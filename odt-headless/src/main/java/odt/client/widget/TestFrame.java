package odt.client.widget;

import java.util.HashMap;

import odt.client.*;
import odt.context.ODTComponentFactory;

public class TestFrame extends AComponent implements IFrame {

    private IFrame frameImpl;

    public TestFrame() {
        frameImpl = ODTComponentFactory.createFrame(this);
        impl = frameImpl;
    }

    @Override
    public void setContentPane(IContainer form) {
        frameImpl.setContentPane(form);
        addForm((IForm)form);
    }

    @Override
    public HashMap<String, IForm> getForms() {
        return res;
    }

    private HashMap<String, IForm> res = new HashMap<String, IForm>();

    @Override
    public void addForm(IForm f) {
        res.put(f.getName(), f);
    }

    @Override
    public void removeForm(IForm f) {
        res.remove(f.getName());
    }

    @Override
    public String newFormName() {
        int i = 1;
        while(res.containsKey("TestForm"+i)) {
            i++;
        }
        return "TestForm"+i;
    }

}