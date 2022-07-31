package odt.client.widget;

import java.util.EventListener;
import java.util.Map;
import java.awt.event.ActionListener;

import odt.client.IButton;
import odt.client.ODTComponentFactory;
import odt.client.model.ODTField;

public class TestButton extends AComponent implements IButton {

    private IButton buttonImpl;

    public TestButton(String s) {
        buttonImpl = ODTComponentFactory.createButton(s, this);
        impl = buttonImpl;
    }

    /*@Override
    public String persist() {
        StringBuffer json  = new StringBuffer();

        json.append("\"");json.append(getName());json.append("\":");json.append("{");

        json.append("\"enable\":");json.append(buttonImpl.isEnabled());json.append(",");
        json.append("\"selected\":");json.append(buttonImpl.isSelected());json.append(",");
        json.append("\"visible\":");json.append(buttonImpl.isVisible());

        json.append("}");

        return json.toString();
    }*/

    @Override
    public ODTField persist() {
        ODTField field  = new ODTField();
        field.formId(formId).actionId(getName());
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

