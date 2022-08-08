package odt.client.widget;
import odt.client.*;
import odt.context.Context;
import odt.context.ODTComponentFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ActionPanel implements ActionListener, DocumentListener {

    private Context context = ODTComponentFactory.getContext();

    TestForm form;
    TestButton b;
    TestTextField t;
    TestComboBox s;

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
                context.runTest();
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
