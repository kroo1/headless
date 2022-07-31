package odt.client.headless;

import odt.client.IComponent;
import odt.client.IContainer;
import odt.client.IForm;
import odt.client.IFrame;
import odt.runner.IRootContainer;

import java.util.HashMap;

public class HFrame extends AComponent implements IFrame {
    public HFrame(IComponent wrapper) {
        super(wrapper);
    }

    private IContainer contentPane;
    @Override
    public void setContentPane(IContainer form) {
        contentPane = form;
    }

    @Override
    public IComponent[] getFields() {
        return new IComponent[]{(IComponent)contentPane};
    }

    public IRootContainer getWrapperFrame() {
        return (IRootContainer) getWrapper();
    }

    public HashMap<String, IForm> getForms() {
        return getWrapperFrame().getForms();
    }

    public void addForm(IForm f) {
        getWrapperFrame().addForm(f);
    }

    public void removeForm(IForm f) {
        getWrapperFrame().removeForm(f);
    }

    public String newFormName() {
        return getWrapperFrame().newFormName();
    }
}
