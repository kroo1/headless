package odt.client;

import java.util.Map;

public interface IForm {
    String getName();
    void setSate(Map<String, Object> state);
    int showConfirmDialog(String dialog, int okCancelOption, int plainMessage);
    Object getOrig();
    void add(IComponent b);
    IComponent[] getFields();
    void setName(String testForm);
    void setBounds(int x, int y, int w, int h);
    String getODTState();
}
