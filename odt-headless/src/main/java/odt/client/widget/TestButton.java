package odt.client.widget;

import java.util.EventListener;
import java.util.Map;
import java.awt.event.ActionListener;

import odt.client.IButton;
import odt.client.model.ODTField;
import odt.context.ODTComponentFactory;

public class TestButton extends AComponent implements IButton {

    private IButton buttonImpl;

    public TestButton(String s) {
        buttonImpl = ODTComponentFactory.createButton(s, this);
        impl = buttonImpl;
    }

    @Override
    public ODTField persist() {
        ODTField field  = new ODTField();
        field.actionId(getName());
        return field;
    }

    @Override
    public void setSate(Map iState) {
        buttonImpl.setEnabled((boolean)iState.get("enable"));
        buttonImpl.setSelected((boolean)iState.get("selected"));
        buttonImpl.setVisible((boolean)iState.get("visible"));
    }

    @Override
    public void runAction(EventListener l, String[] line) {
        buttonImpl.doClick();
    }

    @Override
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

}

