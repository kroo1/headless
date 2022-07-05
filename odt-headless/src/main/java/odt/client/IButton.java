package odt.client;

import java.awt.event.ActionListener;

public interface IButton {

    void setEnabled(boolean enable);

    void setSelected(boolean selected);

    void setVisible(boolean visible);

    void doClick();

    void addActionListener(ActionListener actionListener);

    void setName(String name);

    ActionListener[] getActionListeners();

    void removeActionListener(ActionListener l);

    String getName();

    boolean isEnabled();

    boolean isSelected();

    boolean isVisible();

    void setBounds(int x, int y, int w, int h);

    Object getOrig();

}
