package odt.client.widget;
import odt.client.*;
import odt.client.tester.ODTesterMain;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

public class ActionPanel implements ActionListener, DocumentListener {

    private Context context = ODTComponentFactory.getContext();

    private abstract class AComponent {
        protected String formId;
        public void setFormId(String name) {
            formId = name;
        }

        public String getName() {
            return formId + "_" + ((IComponent)getOrig()).getName();
        }

        public abstract Object getOrig();
    }

    private class TestButton extends AComponent implements IComponent {

        private IButton buttonImpl;

        public TestButton(String s) {
            buttonImpl = ODTComponentFactory.createButton(s, this);
        }

        @Override
        public String persist() {
            StringBuffer json  = new StringBuffer();

            json.append("\"");json.append(getName());json.append("\":");json.append("{");

            json.append("\"enable\":");json.append(buttonImpl.isEnabled());json.append(",");
            json.append("\"selected\":");json.append(buttonImpl.isSelected());json.append(",");
            json.append("\"visible\":");json.append(buttonImpl.isVisible());

            json.append("}");

            return json.toString();
        }

        @Override
        public void setSate(Map iState) {
            buttonImpl.setEnabled((boolean)iState.get("enable"));
            buttonImpl.setSelected((boolean)iState.get("selected"));
            buttonImpl.setVisible((boolean)iState.get("visible"));
        }

        @Override
        public String getODTState() {
            return null;
        }

        @Override
        public String getPValue() {
            return null;
        }

        @Override
        public void runAction(EventListener l, String[] line) {
            buttonImpl.doClick();
        }

        @Override
        public Object getOrig() {
            return buttonImpl.getOrig();
        }

        @Override
        public IComponent getWrapper() {
            return this;
        }

        public void addActionListener(ActionListener l) {
            buttonImpl.addActionListener(context.getActionListener(this, l));
        }


        public void setName(String name) {
            buttonImpl.setName(name);
            ActionListener[] ls = buttonImpl.getActionListeners();
            if(ls != null && ls.length > 1) {
                System.out.println("több listener");
            }
            if(ls != null && ls.length > 0) {
                ActionListener nl = context.getActionListener(this, ls[0]);
                if (nl != ls[0]) {
                    buttonImpl.removeActionListener(ls[0]);
                    buttonImpl.addActionListener(nl);
                }
            }
        }

        public void setBounds(int x, int y, int w, int h) {
            buttonImpl.setBounds(x, y, w, h);
        }

    }

    private class TestTextField extends AComponent implements IComponent {

        private ITextField impl;

        public TestTextField() {
            impl = ODTComponentFactory.createTextField(this);
        }

        @Override
        public String persist() {
            StringBuffer json  = new StringBuffer();

            json.append("\"");json.append(getName());json.append("\":");json.append("{");

            json.append("\"enable\":");json.append(impl.isEnabled());json.append(",");
            json.append("\"visible\":");json.append(impl.isVisible());json.append(",");
            json.append("\"value\":\"");json.append(impl.getText());

            json.append("\"}");

            return json.toString();
        }

        @Override
        public void setSate(Map iState) {
            impl.setEnabled((boolean)iState.get("enable"));
            impl.setVisible((boolean)iState.get("visible"));
            impl.setText(iState.get("value").toString());
        }

        @Override
        public String getODTState() {
            return null;
        }

        @Override
        public String getPValue() {
            return impl.getText();
        }

        @Override
        public void runAction(EventListener l, String[] line) {
            if(line.length > 1) {
                impl.setText(line[1]);
            }else {
                impl.setText("");
            }
        }

        @Override
        public Object getOrig() {
            return impl.getOrig();
        }

        @Override
        public IComponent getWrapper() {
            return this;
        }

        @Override
        public void setBounds(int x, int y, int w, int h) {
            impl.setBounds(x, y, w, h);
        }

        public void addDocumentListener(DocumentListener l) {
            impl.addDocumentListener(context.getDocumentListener(this, l));
        }

        public void setText(String s) {
            impl.setText(s);
        }

        public void setName(String name) {
            impl.setName(name);
        }

        public String getText() {
            return impl.getText();
        }

        public Object getDocument() {
            return impl.getDocument();
        }
    }

    private class TestComboBox extends AComponent implements IComponent {

        private IComboBox impl;

        public TestComboBox() {
            impl = ODTComponentFactory.createComboBox(null,this);
        }

        public TestComboBox(String[] list) {
            impl = ODTComponentFactory.createComboBox(list, this);
        }

        @Override
        public String persist() {
            StringBuffer json  = new StringBuffer();

            json.append("\"");json.append(getName());json.append("\":");json.append("{");

            json.append("\"enable\":");json.append(impl.isEnabled());json.append(",");
            //json.append("\"focused\":");json.append(hasFocus());json.append(",");
            json.append("\"visible\":");json.append(impl.isVisible());json.append(",");
            json.append("\"selectedIdx\":");json.append(impl.getSelectedIndex());json.append(",");
            json.append("\"selectedItem\":");json.append("\"");json.append(impl.getSelectedItem());json.append("\"");json.append(",");
            json.append("\"selectableItems\":");json.append("[");
            int itemCount = impl.getItemCount();
            for (int i = 0; i < itemCount; i++) {
                if(i > 0) {
                    json.append(",");
                }
                json.append("\"");json.append(impl.getElementAt(i));json.append("\"");
            }
            json.append("]");

            json.append("}");

            return json.toString();
        }

        @Override
        public void setSate(Map iState) {
            impl.setEnabled((boolean)iState.get("enable"));
            impl.setVisible((boolean)iState.get("visible"));
            List<Object> items = (List)iState.get("selectableItems");
            impl.removeAllItems();
            for (int i = 0; i < items.size(); i++) {
                impl.addItem(items.get(i));
            }
            impl.setSelectedIndex((int)iState.get("selectedIdx"));
        }

        @Override
        public String getODTState() {
            return null;
        }

        @Override
        public String getPValue() {
            return Integer.toString(impl.getSelectedIndex());
        }

        @Override
        public void runAction(EventListener l, String[] line) {
            impl.setSelectedIndex(Integer.parseInt(line[1]));
        }

        @Override
        public Object getOrig() {
            return impl.getOrig();
        }

        @Override
        public IComponent getWrapper() {
            return this;
        }

        @Override
        public void setBounds(int x, int y, int w, int h) {
            impl.setBounds(x, y, w, h);
        }

        public void addActionListener(ActionListener l) {
            impl.addActionListener(context.getActionListener(this, l));
        }

        public void addItems(Object[] items) {
            impl.addItems(items);
        }

        public void setName(String name) {
            impl.setName(name);
        }
    }

    TestForm form;
    TestButton b;
    TestTextField t;
    TestComboBox s;

    private class TestForm  extends AComponent implements IForm, IComponent, IContainer {

        private IForm formImpl;

        public TestForm() {
            formImpl = ODTComponentFactory.createForm(this);
        }


        @Override
        public String getODTState() {
            StringBuilder json = new StringBuilder();
            try {
                json.append("{");
                boolean append = false;
                IComponent[] cmps = formImpl.getFields();
                int cmpc = cmps != null ? cmps.length : 0;
                for (int idx = 0; idx < cmpc; idx++) {
                    IComponent cmp = cmps[idx];
                    if (append) {
                        json.append(",");
                    }
                    json.append(cmp.persist());
                    append = true;
                }

                json.append("}");

                //System.out.println("ODT_JSON:" + json.toString());
                return json.toString();
            }catch(Exception ex) {
                ex.printStackTrace();
                return "{}";
            }
        }

        @Override
        public String getName() {
            return formImpl.getName();
        }

        @Override
        public void setSate(Map state) {
            IComponent[] cmps = formImpl.getFields();
            int cmpc = cmps != null ? cmps.length : 0;
            for (int idx = 0; idx < cmpc; idx++) {
                Object item = cmps[idx];
                IComponent cmp = cmps[idx];
                Map iState = (Map)state.get(cmp.getName());
                if(iState != null) {
                    cmp.setSate(iState);
                }
            }
        }

        @Override
        public String getPValue() {
            return null;
        }

        @Override
        public void runAction(EventListener l, String[] line) {

        }

        @Override
        public Object getOrig() {
            return formImpl.getOrig();
        }

        @Override
        public IComponent getWrapper() {
            return this;
        }

        @Override
        public void setBounds(int x, int y, int w, int h) {
            formImpl.setBounds(x, y, w, h);
        }


        @Override
        public String persist() {
            return null;
        }

        @Override
        public IComponent[] getFields() {
            return formImpl.getFields();
        }

        @Override
        public void setName(String s) {
            formImpl.setName(s);
        }

        @Override
        public int showConfirmDialog(String title, int optionType, int messageType) {
            return formImpl.showConfirmDialog(title, optionType, messageType);
        }

        public void add(IComponent b) {
            formImpl.add(b);
            b.setFormId(this.getName());
        }
    }

    public IForm getForm() {
        return form;
    }
    private IDialog j;
    public ActionPanel() {
        this.j = j;
        form  = new TestForm();
        form.setName(context.ROOT.newFormName());

        b=new TestButton("click");
        b.setBounds(130,100,100, 40);
        form.add(b);
        b.setName("btnClick");
        b.addActionListener(this);


        t=new TestTextField();
        t.setBounds(130,140,100, 40);
        t.setText("1");
        form.add(t);
        t.setName("counter");
        t.addDocumentListener(this);

        TestButton st=new TestButton("start test") {
            @Override
            public boolean testAction() {
                return false;
            }
        };
        st.setBounds(130,180,100, 40);
        st.setName("start test");
        st.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.startTest();
            }
        });

        form.add(st);

        TestButton r=new TestButton("run test"){
            @Override
            public boolean testAction() {
                return false;
            }
        };
        r.setBounds(130,220,100, 40);
        r.setName("run test");
        r.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    context.runTest();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        form.add(r);

        TestButton db=new TestButton("dialog");
        db.setBounds(130,260,100, 40);
        form.add(db);
        db.setName("dialog");
        db.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IForm f =new ActionPanel().getForm();

                f.showConfirmDialog("Dialog",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };
        s=new TestComboBox();
        s.setBounds(130,300,100, 40);
        form.add(s);
        s.setName("select");
        s.addActionListener(this);
        s.addItems(petStrings);


    }

    private StringBuffer log;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(b.getOrig())) {
            t.setText(Integer.toString(Integer.parseInt(t.getText()) + 1));
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if(e.getDocument().equals(t.getDocument())) {
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if(e.getDocument().equals(t.getDocument())) {
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if(e.getDocument().equals(t.getDocument())) {
        }
    }
}
