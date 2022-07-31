package odt.client.widget;

import java.awt.event.ActionListener;

import odt.client.IComponent;
import odt.context.Context;
import odt.context.ODTComponentFactory;

public abstract class AComponent implements IComponent {

    protected Context context = ODTComponentFactory.getContext();

    protected String formId;

    protected IComponent impl;

    public void setFormId(String name) {
        formId = name;
    }

    public String getFormId() {
        return formId;
    }

    @Override
    public String getName() {
        /*if(formId != null) {
            return formId + "_" + getIComponentImpl().getName();
        }else {
            return getIComponentImpl().getName();
        }*/
        return getIComponentImpl().getName();
    }

    @Override
    public IComponent getIComponentImpl() {
        return impl;
    }

    @Override
    public IComponent getWrapper() {
        return this;
    }

    @Override
    public void addActionListener(ActionListener l) {
        impl.addActionListener(context.getActionListener(this, l));
    }

}

