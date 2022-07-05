package odt.client;

import java.util.EventListener;
import java.util.Map;

public interface IComponent {
    String persist();
    String getName();
    void setSate(Map<String, Object> iState);
    String getODTState();
    String getPValue();

    void runAction(EventListener l, String[] line);

    Object getOrig();
    IComponent getWrapper();

    void setBounds(int x, int y, int w, int h);

    void setFormId(String name);

    default boolean testAction() {
        return true;
    }
}
