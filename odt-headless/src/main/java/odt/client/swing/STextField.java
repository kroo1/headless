package odt.client.swing;

import odt.client.IComponent;
import odt.client.ITextField;
import odt.client.model.ODTField;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.util.EventListener;
import java.util.Map;

public class STextField extends JTextField implements ITextField {

    @Override
    public IComponent getIComponentImpl() {
        return this;
    }

    private IComponent wrapper;

    public STextField(IComponent wrapper) {
        this.wrapper = wrapper;
    }

    /*@Override
    public String persist() {
        return wrapper.persist();
    }*/

    @Override
    public ODTField persist() {
        return wrapper.persist();
    }

    @Override
    public void setSate(Map iState) {
        wrapper.setSate(iState);
    }

    @Override
    public String getODTState() {
        return null;
    }

    @Override
    public String getPValue() {
        return wrapper.getPValue();
    }

    @Override
    public void runAction(EventListener l, String[] line) {
        wrapper.runAction(l,line);
    }

    @Override
    public IComponent getWrapper() {
        return wrapper;
    }

    @Override
    public void setFormId(String name) {
        wrapper.setFormId(name);
    }

    @Override
    public String getFormId(){
        return wrapper.getFormId();
    }

    @Override
    public void addDocumentListener(DocumentListener l) {
        getDocument().addDocumentListener(l);
    }

    @Override
    public Object getOrig() {
        return this;
    }
}
