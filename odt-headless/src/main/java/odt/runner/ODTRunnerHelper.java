package odt.runner;

import com.fasterxml.jackson.databind.ObjectMapper;

import odt.client.IComponent;
import odt.client.IDialog;
import odt.client.IForm;
import odt.context.Context;
import odt.context.ODTComponentFactory;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

public class ODTRunnerHelper {

    private final Context context;

    public ODTRunnerHelper(Context context) {
        this.context = context;
    }

    private IRunnerMode stateHelper = null;

    public void setRunnerMode(IRunnerMode stateHelper) {
        this.stateHelper = stateHelper;
    }

    private Object event = null;
    private StringBuffer actions = new StringBuffer();
    private int action_counter = 0;
    private boolean loading = false;

    public void reset() {
        event = null;
        actions = new StringBuffer();
        action_counter = 0;
        stateHelper.reset();
    }

    public void runTest() throws IOException {
        reset();
        loading = true;
        setState(stateHelper.loadState0());
        loading = false;
        saveFormsState();
        runActions(stateHelper.loadActions());
    }

    public void startTest() {
        reset();
        saveFormsState();
    }

    private void setState(String pState) {
        if(pState != null && context.ROOT != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map map = mapper.readValue(pState, Map.class);
                IComponent[] forms = context.ROOT.getFields();
                for (int i = 0; i < forms.length; i++) {
                    IForm form = (IForm) forms[i];
                    Map iState = (Map)map.get(form.getName());
                    if(iState != null) {
                        form.setSate(iState);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFormsState() {
        stateHelper.saveState(getState(), action_counter);
    }

    private void saveAction() {
        action_counter++;
        saveFormsState();
        stateHelper.saveActions(actions.toString());
    }

    public boolean testActionStart(Object e, IComponent c) {
        if(loading) return false;
        if(event != null) return false;
        event = e;
        if(e instanceof AWTEvent) {
            AWTEvent awtEvent = (AWTEvent) e;
            Object source = awtEvent.getSource();
            if(source != null && source instanceof IComponent) {
                //String name = ((Component) source).getName();
                IComponent s = ((IComponent) source).getWrapper();
                if(s.testAction())
                addAction(s.getFormId(), s.getName(), s.getPValue());
            }
        }else if(e instanceof DocumentEvent ) {
            Object source = c;
            if(source != null && source instanceof IComponent) {
                if(c.testAction())
                    addAction(c.getFormId(), c.getName(), c.getPValue());
            }
        }
        return true;
    }

    public void testActionEnd(Object e) {
        if(event == e) {
            event = null;
        }
    }

    private void addAction(String formId, String action, String value) {
        if(formId != null) {
            actions.append(formId + ",");
        }
        if(value != null) {
            actions.append(action + "," + value + "\n");
        }else {
            actions.append(action + "\n");
        }
        saveAction();
    }

    public String getState() {
        if(context.ROOT != null) {
            StringBuffer json = new StringBuffer();
            Collection<IForm> forms = context.ROOT.getForms().values();
            boolean append = false;
            json.append("{");
            Iterator<IForm> it = forms.iterator();
            while (it.hasNext()) {
                IForm form = it.next();
                if (append) {
                    json.append(",");
                }
                json.append("\"");json.append(form.getName());json.append("\":");
                json.append(form.getODTState());
                append = true;
            }
            json.append("}");
            return json.toString();
        }
        return null;
    }

    private void runActions(BufferedReader br) throws IOException {
        if (br != null) {
            List<String> lines = new ArrayList<>();
            String line;
            while((line = br.readLine())!=null) {
                lines.add(line);
            }
            runActions((String[])lines.toArray(new String[lines.size()]));
        }
    }

    public void runActions(String actions) throws IOException {
        if (actions != null) {
            StringReader reader = new StringReader(actions);
            BufferedReader br = new BufferedReader(reader);
            try {
                runActions(br);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                br.close();
            }
        }
    }

    public void runActions(String[] actions) throws IOException {
        List<String> lines = new ArrayList<>();
        if (actions != null) {
            IRunner first = null;
            for (String line : actions) {
                if(DIALOG_OPEN.equals(line)) {
                    IRunner task = ODTComponentFactory.createRunner(lines);
                    if(first == null) {
                        first = task;
                    }else {
                        tasks.add(task);
                    }
                    lines = new ArrayList<>();
                }else if(line.startsWith(DIALOG_CLOSE)) {
                    lines.add(line);
                    IRunner task = ODTComponentFactory.createRunner(lines);
                    tasks.add(task);
                    lines = new ArrayList<>();
                }else {
                    lines.add(line);
                }
            }
            if(lines.size() > 0) {
                IRunner task = ODTComponentFactory.createRunner(lines);
                if(first == null) {
                    first = task;
                }else {
                    tasks.add(task);
                }
                lines = new ArrayList<>();
            }
            if (first != null) {
                first.exec();
            }
        }else {
            System.out.println("No actions!");
        }
    }

    public void runAction(String line) {
        String[] res = line.split(",");
        if(DIALOG_CLOSE.equals(res[0])) {
            IDialog opane = dialogs.pop();
            opane.setValue(res[1]);
            opane.closeConfirmDialog();
            opane.setValue(res[1]);
        }else {
            ActionItems actionItems = actionMap.get(res[0]+"."+res[1]);
            if (actionItems != null) actionItems.c.runAction(actionItems.l, res);
        }
    }

    //Dialog

    private Stack<IDialog> dialogs = new Stack<>();

    private List<IRunner> tasks = new CopyOnWriteArrayList<>();

    private final String DIALOG_OPEN = "DIALOG_OPEN";
    private final String DIALOG_CLOSE = "DIALOG_CLOSE";

    private void firePopupOpenClose() {
        if(tasks == null || tasks.size() == 0) return;
        IRunner task = tasks.remove(0);
        if(task != null) {
            task.exec();
        }
    }

    public void dialogStart(IDialog dialog) {
        context.ROOT.addForm(dialog.getForm());
        dialogs.push(dialog);
        event = null;
        addAction(null, DIALOG_OPEN, null);
        firePopupOpenClose();
    }

    public void dialogEnd(IDialog dialog, int closeAction) {
        context.ROOT.removeForm(dialog.getForm());
        firePopupOpenClose();
        addAction(null, DIALOG_CLOSE, Integer.toString(closeAction));
    }

    //Action

    private class ActionItems {
        private final IComponent c;
        private final EventListener l;
        private ActionItems(IComponent c, EventListener l) {
            this.c = c;
            this.l = l;
        }
    }

    private HashMap<String, ActionItems> actionMap = new HashMap<>();

    public ActionListener getActionListener(IComponent c, ActionListener l) {
        if(stateHelper == null || l instanceof ListenerAdapter) return l;
        if(c.getFormId() != null && c.getName() != null) {
            ListenerAdapter la = new ListenerAdapter(l, null, null);
            ActionItems newV = new ActionItems(c, la);
            ActionItems oldV = actionMap.replace(c.getFormId()+"."+c.getName(), newV);
            if(oldV == null) {
                actionMap.put(c.getFormId()+"."+c.getName(), newV);
            }
            return la;
        }
        return l;
    }

    public DocumentListener getDocumentListener(IComponent c, DocumentListener l) {
        if(stateHelper == null || l instanceof ListenerAdapter) return l;
        if(c.getFormId() != null && c.getName() != null) {
            ListenerAdapter la = new ListenerAdapter(null, l, c);
            ActionItems newV =new ActionItems(c, la);
            ActionItems oldV = actionMap.replace(c.getFormId()+"."+c.getName(), newV);
            if(oldV == null) {
                actionMap.put(c.getFormId()+"."+c.getName(), newV);
            }
            return la;
        }
        return l;
    }

    private class ListenerAdapter implements ActionListener, DocumentListener {

        private final ActionListener al;
        private final DocumentListener dl;
        private final IComponent c;

        private ListenerAdapter(ActionListener al, DocumentListener dl, IComponent c) {
            this.al = al;
            this.dl = dl;
            this.c = c;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(al == null) return;
            if(!testActionStart(e,null)) return;
            al.actionPerformed(e);
            testActionEnd(e);
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            if(dl == null) return;
            if(!testActionStart(e,c)) return;
            dl.insertUpdate(e);
            testActionEnd(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if(dl == null) return;
            if(!testActionStart(e, c)) return;
            dl.removeUpdate(e);
            testActionEnd(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if(dl == null) return;
            if(!testActionStart(e, c)) return;
            dl.changedUpdate(e);
            testActionEnd(e);
        }
    }

}

