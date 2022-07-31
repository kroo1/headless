package odt.client.widget;

import java.util.EventListener;
import java.util.Map;

import javax.swing.event.DocumentListener;

import odt.client.ITextField;
import odt.client.ODTComponentFactory;
import odt.client.model.ODTField;

public class TestTextField extends AComponent implements ITextField {

    private ITextField impl;

    public TestTextField() {
        impl = ODTComponentFactory.createTextField(this);
        super.impl = impl;
    }

    /*@Override
    public String persist() {
        StringBuffer json  = new StringBuffer();

        json.append("\"");json.append(getName());json.append("\":");json.append("{");

        json.append("\"enable\":");json.append(impl.isEnabled());json.append(",");
        json.append("\"visible\":");json.append(impl.isVisible());json.append(",");
        json.append("\"value\":\"");json.append(impl.getText());

        json.append("\"}");

        return json.toString();
    }*/

    @Override
    public ODTField persist() {
        ODTField field  = new ODTField();
        field.formId(formId).actionId(getName()).value(impl.getText());
        return field;
    }

    @Override
    public void setSate(Map iState) {
        impl.setEnabled((boolean)iState.get("enable"));
        impl.setVisible((boolean)iState.get("visible"));
        impl.setText(iState.get("value").toString());
    }

    @Override
    public String getPValue() {
        return impl.getText();
    }

    @Override
    public void runAction(EventListener l, String[] line) {
        if(line.length > 2) {
            impl.setText(line[2]);
        }else {
            impl.setText("");
        }
    }

    public void addDocumentListener(DocumentListener l) {
        impl.addDocumentListener(context.getDocumentListener(this, l));
    }
}

