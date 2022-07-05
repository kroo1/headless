package odt.client.headless;

import odt.client.IComboBox;
import odt.client.IComponent;

import java.util.EventListener;
import java.util.Vector;

public class HComboBox extends AComponent implements IComboBox {

    private Vector<Object> items = new Vector<>();

    public HComboBox(IComponent wrapper) {
        super(wrapper);
    }

    public HComboBox(String[] list, IComponent wrapper) {
        super(wrapper);
        if(list != null) addItems(list);
    }

    private int selectedIndex = 0;

    @Override
    public int getSelectedIndex() {
        return selectedIndex;
    }

    @Override
    public Object getSelectedItem() {
        if(selectedIndex == -1) {
            return null;
        }
        return items.elementAt(selectedIndex);
    }

    @Override
    public void setSelectedIndex(int index) {
        if(-1 < index && index < items.size()) {
            selectedIndex = index;
        }else {
            selectedIndex = -1;
        }
        fireActionPerformed(null);
    }

    @Override
    public void addItem(Object item) {
        items.add(item);
    }

    @Override
    public void removeAllItems() {
        items.removeAllElements();
    }

    @Override
    public void addItems(Object[] list) {
        int l = list.length;
        for (int i = 0; i < l; i++) {
            items.add(list[i]);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Object getElementAt(int i) {
        return items.elementAt(i);
    }

    @Override
    public void runAction(EventListener l, String[] line) {
        fireActionPerformed(null);
    }

}
