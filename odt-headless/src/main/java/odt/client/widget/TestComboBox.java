package odt.client.widget;

import java.util.EventListener;
import java.util.List;
import java.util.Map;

import odt.client.IComboBox;
import odt.client.ODTComponentFactory;
import odt.client.model.ODTField;

public class TestComboBox extends AComponent implements IComboBox {

    private IComboBox impl;

    public TestComboBox() {
        impl = ODTComponentFactory.createComboBox(null,this);
        super.impl = impl;
    }

    public TestComboBox(String[] list) {
        impl = ODTComponentFactory.createComboBox(list, this);
        super.impl = impl;
    }

    /*@Override
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
    }*/

    @Override
    public ODTField persist() {
        ODTField field  = new ODTField();
        field.formId(formId).actionId(getName()).value(""+impl.getSelectedIndex());
        return field;
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
    public String getPValue() {
        return Integer.toString(impl.getSelectedIndex());
    }

    @Override
    public void runAction(EventListener l, String[] line) {
        if(line.length > 2) {
            impl.setSelectedIndex(Integer.parseInt(line[2]));
        }
    }

}

