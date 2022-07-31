package odt.client.swing;

import odt.client.IComponent;
import odt.client.IContainer;
import odt.client.IForm;
import odt.client.IFrame;
import odt.client.model.ODTField;
import odt.client.tester.IRootContainer;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class SFrame extends JFrame implements IFrame {

    @Override
    public IComponent getIComponentImpl() {
        return this;
    }

    private final IRootContainer wrapper;

    public SFrame(IComponent wrapper) {
        this.wrapper = (IRootContainer)wrapper;
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

    }

    @Override
    public String getODTState() {
        return "";
    }

    @Override
    public String getPValue() {
        return null;
    }

    @Override
    public void runAction(EventListener l, String[] line) {

    }

    @Override
    public Component getOrig() {
        return this;
    }

    @Override
    public void seBounds(int x, int y, int w, int h) {

    }

    @Override
    public IComponent getWrapper() {
        return null;
    }

    @Override
    public void setContentPane(IContainer form) {
        Object orig = form.getOrig();
        if(orig != null && orig instanceof Container) {
            setContentPane((Container) orig);
        }
    }

    @Override
    public void setLayout(Object o) {
        if(o instanceof LayoutManager) {
            setLayout(o);
        }
    }

    @Override
    public IComponent[] getFields() {
        return new IComponent[]{(IComponent)getContentPane()};
    }

    public HashMap<String, IForm> getForms() {
        return wrapper.getForms();
    }

    public void addForm(IForm f) {
        wrapper.addForm(f);
    }

    public void removeForm(IForm f) {
        wrapper.removeForm(f);
    }

    @Override
    public String newFormName() {
        return wrapper.newFormName();
    }

    @Override
    public void setFormId(String name) {

    }

}
