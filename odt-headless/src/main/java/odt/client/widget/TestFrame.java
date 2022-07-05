package odt.client.widget;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import odt.client.*;
import odt.client.tester.IRootContainer;

public class TestFrame implements IFrame, IComponent, IRootContainer {

    private IFrame frameImpl;

    public TestFrame() {
        frameImpl = ODTComponentFactory.createFrame(this);
    }

    @Override
    public String persist() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setSate(Map<String, Object> iState) {

    }

    @Override
    public String getODTState() {
        return null;
    }

    @Override
    public String getPValue() {
        return null;
    }

    @Override
    public void runAction(EventListener l, String[] line) {

    }

    @Override
    public Object getOrig() {
        return frameImpl;
    }

    @Override
    public void seBounds(int x, int y, int w, int h) {
        frameImpl.seBounds(x, y, w, h);
    }

    @Override
    public IComponent getWrapper() {
        return this;
    }

    @Override
    public void setBounds(int x, int y, int w, int h) {
        frameImpl.seBounds(x, y, w, h);
    }

    @Override
    public void setFormId(String name) {

    }

    @Override
    public void setContentPane(IContainer form) {
        frameImpl.setContentPane(form);
        addForm((IForm)form);
    }

    @Override
    public void setSize(int w, int h) {
        frameImpl.setSize(w,h);
    }

    @Override
    public void setLayout(Object o) {
        frameImpl.setLayout(o);
    }

    @Override
    public void setVisible(boolean b) {
        frameImpl.setVisible(b);
    }

    @Override
    public IComponent[] getFields() {
        return frameImpl.getFields();
    }

    public HashMap<String, IForm> getForms() {
        return res;
    }

    private HashMap<String, IForm> res = new HashMap<String, IForm>();

    public void addForm(IForm f) {
        res.put(f.getName(), f);
    }

    public void removeForm(IForm f) {
        res.remove(f.getName());
    }

    @Override
    public String newFormName() {
        int i = 1;
        while(res.containsKey("TestFrame"+i)) {
            i++;
        }
        return "TestFrame"+i;
    }

}