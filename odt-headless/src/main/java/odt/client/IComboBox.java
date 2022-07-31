package odt.client;

public interface IComboBox extends IComponent {

    default int getSelectedIndex() {
        return ((IComboBox)getIComponentImpl()).getSelectedIndex();
    }

    default Object getSelectedItem() {
        return ((IComboBox)getIComponentImpl()).getSelectedItem();
    }

    default void setSelectedIndex(int index) {
        ((IComboBox)getIComponentImpl()).setSelectedIndex(index);
    }

    default void addItem(Object item) {
        ((IComboBox)getIComponentImpl()).addItem(item);
    }

    default void removeAllItems() {
        ((IComboBox)getIComponentImpl()).removeAllItems();
    }

    default void addItems(Object[] items) {
        ((IComboBox)getIComponentImpl()).addItems(items);
    }

    default int getItemCount() {
        return ((IComboBox)getIComponentImpl()).getItemCount();
    }

    default Object getElementAt(int i) {
        return ((IComboBox)getIComponentImpl()).getElementAt(i);
    }

}
