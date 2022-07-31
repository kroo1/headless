package odt.client.widget;

import java.util.EventListener;
import java.util.List;
import java.util.Map;

import odt.client.IComboBox;
import odt.client.model.ODTField;
import odt.context.ODTComponentFactory;

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

    @Override
    public ODTField persist() {
        ODTField field  = new ODTField();
        field.actionId(getName()).value(""+impl.getSelectedIndex());
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

