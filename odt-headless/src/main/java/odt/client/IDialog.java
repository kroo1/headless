package odt.client;

public interface IDialog {
    void setValue(Object obj);
    Object getValue();
    IForm getForm();
    void closeConfirmDialog();
}
