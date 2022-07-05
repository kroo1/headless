package odt.client;

public interface IFrame {
    void setContentPane(IContainer form);

    void setSize(int i, int i1);

    void setLayout(Object o);

    void setVisible(boolean b);

    IComponent[] getFields();

    void seBounds(int x, int y, int w, int h);

}
