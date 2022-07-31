package odt.client.tester;

import com.fasterxml.jackson.databind.ObjectMapper;

import odt.client.Context;
import odt.client.IComponent;
import odt.client.IDialog;
import odt.client.IForm;
import odt.client.ODTComponentFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ODTesterMain {

    private final Context context = ODTComponentFactory.getContext();

    private String DIR_NAME;
    private String LOAD_DIR_PATH;
    private String DIR_PATH;
    private String ACTION_PATH;
    private Object event = null;
    private StringBuffer actions = new StringBuffer();
    private int action_counter = 0;
    private boolean loading = false;

    private void createDir() {
        if(DIR_PATH == null) {
            DIR_NAME = "ODTest_" + (new SimpleDateFormat("YYYYMMdd_HHMMSS").format(new Date()));
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Válasszon könyvtárat!");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int userSelection = fileChooser.showSaveDialog(new JFrame());

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                File dir = new File(fileToSave.getAbsolutePath() + "/" + DIR_NAME);
                if (!dir.exists()) {
                    dir.mkdirs();
                    DIR_PATH = dir.getAbsolutePath();
                }
            }
        }
    }

    private void loadDir() {
        if(LOAD_DIR_PATH == null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Válasszon könyvtárat!");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int userSelection = fileChooser.showOpenDialog(new JFrame());

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                loadDirH(fileChooser.getSelectedFile());
            }
        }
    }

    private void loadDirH(File fileToSave) {
        String time = (new SimpleDateFormat("YYYYMMdd_HHMMSS").format(new Date()));
        LOAD_DIR_PATH = fileToSave.getAbsolutePath();
        String lDirName = "Result_" + fileToSave.getName() + "_" + time;
        File dir = new File(fileToSave.getParentFile().getAbsolutePath() + "/" + lDirName);
        if (!dir.exists()) {
            dir.mkdirs();
            DIR_PATH = dir.getAbsolutePath();
        }
    }

    private void loadState0() throws IOException {
        StringBuilder res = new StringBuilder();
        File state0 = new File(LOAD_DIR_PATH + "/" + "state_0");
        if (state0.exists()) {
            BufferedReader br = null;
            try {
                FileReader reader = new FileReader(state0);
                br = new BufferedReader(reader);
                String line;
                while((line = br.readLine())!=null) {
                    res.append(line).append("\n");
                }
                setState(res.toString());
            }finally {
                if(br != null) br.close();
            }
        }else {
            System.out.println("No state0: " + state0.getAbsolutePath());
        }
    }

    private void saveFormsState() {
        createDir();
        if(DIR_PATH != null) {
            try {
                File stateFile = new File(DIR_PATH + "/state_"+action_counter);
                if (!stateFile.exists()) {
                    stateFile.createNewFile();
                    FileWriter writer = new FileWriter(stateFile);
                    writer.write(getState());
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    private void setState(String pState) {
        if(context.ROOT != null) {
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

    private void saveAction() {
        if(context.NOTEST || DIR_PATH == null) return;
        if(ACTION_PATH == null) {
            try {
                File actionsFile = new File(DIR_PATH + "/actions");
                if (!actionsFile.exists()) {
                    actionsFile.createNewFile();
                    ACTION_PATH = actionsFile.getAbsolutePath();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        action_counter++;
        saveFormsState();
        try {
            FileWriter writer = new FileWriter(ACTION_PATH);
            writer.write(actions.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void testActionEnd(Object e) {
        if(event == e) {
            event = null;
        }
    }

    public void startTest() {
        reset();
        saveFormsState();
    }

    public void runTest() throws IOException {
        runTest(null);
    }

    public void runTest(File fileToSave) throws IOException {
        reset();
        loading = true;
        if(fileToSave == null) {
            loadDir();
        }else {
            loadDirH(fileToSave);
        }
        if(LOAD_DIR_PATH != null) {
            loadState0();
            loading = false;
            saveFormsState();
            runActions();
        }
    }

    public void runActions(String actions) throws IOException {
        List<String> lines = new ArrayList<>();
        if (actions != null) {
            BufferedReader br = null;
            try {
                StringReader reader = new StringReader(actions);
                br = new BufferedReader(reader);
                String line;
                IRunner first = null;
                while((line = br.readLine())!=null) {
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
            } finally {
                if(br != null) br.close();
            }
        }else {
            System.out.println("No actions!");
        }
    }

    private void runActions() throws IOException {
        File actions = new File(LOAD_DIR_PATH + "/" + "actions");
        List<String> lines = new ArrayList<>();
        if (actions.exists()) {
            BufferedReader br = null;
            try {
                FileReader reader = new FileReader(actions);
                br = new BufferedReader(reader);
                String line;
                IRunner first = null;
                while((line = br.readLine())!=null) {
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
            } finally {
                if(br != null) br.close();
            }
        }else {
            System.out.println("No actions: " + actions.getAbsolutePath());
        }
    }

    public void runAction(String line) {
        String[] res = line.split(",");
        if(DIALOG_CLOSE.equals(res[0])) {
            IDialog opane = dialogs.pop();
            if(!ODTComponentFactory.SWING_MODE) {
                opane.setValue(res[1]);
                opane.closeConfirmDialog();
            }
            //addAction(DIALOG_CLOSE, res[1]);
            opane.setValue(res[1]);
        }else {
            ActionItems actionItems = actionMap.get(res[0]+"."+res[1]);
            if (actionItems != null) actionItems.c.runAction(actionItems.l, res);
        }
    }

    public void reset() {
        DIR_NAME = null;
        LOAD_DIR_PATH = null;
        DIR_PATH = null;
        ACTION_PATH = null;
        event = null;
        actions = new StringBuffer();
        action_counter = 0;
        loading = false;
    }

    public void main(String[] args) {

    }

    private Stack<IDialog> dialogs = new Stack<>();

    private List<IRunner> tasks = new ArrayList<>();

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
        firePopupOpenClose();
        addAction(null, DIALOG_OPEN, null);
    }

    private final String DIALOG_OPEN = "DIALOG_OPEN";
    private final String DIALOG_CLOSE = "DIALOG_CLOSE";

    public void dialogEnd(IDialog dialog, int closeAction) {
        context.ROOT.removeForm(dialog.getForm());
        firePopupOpenClose();
        addAction(null, DIALOG_CLOSE, Integer.toString(closeAction));
    }

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
        if(l instanceof ListenerAdapter) return l;
        if(context.TEST_MODE && c.getFormId() != null && c.getName() != null) {
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
        if(context.TEST_MODE && c.getFormId() != null && c.getName() != null) {
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

