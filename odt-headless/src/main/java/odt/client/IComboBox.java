package odt.client;

import java.awt.event.ActionListener;

public interface IComboBox {

    String getName();

    boolean isEnabled();

    //boolean isSelected();

    boolean isVisible();

    void setBounds(int x, int y, int w, int h);

    Object getOrig();

    int getSelectedIndex();

    Object getSelectedItem();

    void setSelectedIndex(int index);

    void setEnabled(boolean enable);

    //void setSelected(boolean selected);

    void setVisible(boolean visible);

    void addActionListener(ActionListener actionListener);

    void setName(String name);

    ActionListener[] getActionListeners();

    void removeActionListener(ActionListener l);

    void addItem(Object item);

    void removeAllItems();

    void addItems(Object[] items);

    int getItemCount();

    Object getElementAt(int i);
}
