package odt.client.swing;

import odt.client.Context;
import odt.client.IComboBox;
import odt.client.IComponent;
import odt.client.ODTComponentFactory;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.Map;

public class SComboBox extends JComboBox implements IComboBox, IComponent {

    private Context context = ODTComponentFactory.getContext();

    private IComponent wrapper;

    public SComboBox(IComponent wrapper) {
        super();
        this.wrapper = wrapper;
    }

    public SComboBox(String[] petStrings, IComponent wrapper) {
        super(petStrings);
        this.wrapper = wrapper;
    }

    @Override
    public String persist() {
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
        wrapper.runAction(l, line);
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
    public void addActionListener(ActionListener l) {
        super.addActionListener(context.getActionListener(this, l));
    }

    public void addItems(Object[] items) {
        ActionListener[] ls = super.getActionListeners();
        for (int i = 0; i < ls.length; i++) {
            super.removeActionListener(ls[i]);
        }
        for (int i = 0; i < items.length; i++) {
            addItem(items[i]);
        }
        for (int i = 0; i < ls.length; i++) {
            super.addActionListener(ls[i]);
        }
    }

    @Override
    public Object getElementAt(int i) {
        return getModel().getElementAt(i);
    }

    @Override
    public void setFormId(String name) {
        wrapper.setFormId(name);
    }
}
