package odt.client.tester;

import odt.client.IContainer;
import odt.client.IForm;

import java.util.HashMap;

public interface IRootContainer extends IContainer {
    
    HashMap<String, IForm> getForms();
    void addForm(IForm form);
    void removeForm(IForm form);
    String newFormName();
}
