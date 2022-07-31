package odt.client.swing;

import odt.client.IButton;
import odt.client.IComponent;
import odt.client.model.ODTField;

import javax.swing.*;
import java.util.EventListener;
import java.util.Map;

public class SButton extends JButton implements IButton {

    @Override
    public IComponent getIComponentImpl() {
        return this;
    }

    private IComponent wrapper;

    public SButton(String s, IComponent wrapper) {
        super(s);
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
    public void setSate(Map<String, Object> iState) {
        wrapper.setSate(iState);
    }

    @Override
    public String getODTState() {
        return "";
    }

    @Override
    public String getPValue() {
        return wrapper.getPValue();
    }

    @Override
    public void runAction(EventListener l, String[] line) {
        doClick();
    }

    @Override
    public Object getOrig() {
        return this;
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

}
