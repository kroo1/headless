package odt.client.headless;

import odt.client.IComponent;
import odt.client.model.ODTField;
import odt.context.Context;
import odt.context.ODTComponentFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public abstract class AComponent implements IComponent {

    @Override
    public IComponent getIComponentImpl() {
        return this;
    }

    protected Context context = ODTComponentFactory.getContext();

    private IComponent wrapper;

    public AComponent(IComponent wrapper) {
        this.wrapper = wrapper;
    }

    public AComponent(String s, IComponent wrapper) {
        this.wrapper = wrapper;
        setName(s);
    }

    /*public String persist() {
        return wrapper.persist();
    }*/

    public ODTField persist() {
        return wrapper.persist();
    }

    public void setSate(Map iState) {
        wrapper.setSate(iState);
    }

    public String getPValue() {
        return wrapper.getPValue();
    }

    public void runAction(EventListener l, String[] line) {

    }

    //implement shared parts

    private boolean enable = true;
    public boolean isEnabled() {
        return enable;
    }
    public void setEnabled(boolean enable) {
        this.enable = enable;
    }
    private boolean selected;
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    private boolean visible = true;
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setBounds(int x, int y, int w, int h) {

    }

    private String name;
    @Override
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    private List<ActionListener> listeners = new ArrayList<>();
    public ActionListener[] getActionListeners() {
        return listeners.toArray(new ActionListener[listeners.size()]);
    }
    public void addActionListener(ActionListener actionListener) {
        listeners.add(actionListener);
    }
    public void removeActionListener(ActionListener l) {
        Iterator<ActionListener> it = listeners.iterator();
        while(it.hasNext()) {
            ActionListener i = it.next();
            if(i.equals(l)) {
                it.remove();
                break;
            }
        }
    }

    protected void fireActionPerformed(ActionEvent event) {
        ActionListener[] listeners = getActionListeners();
        ActionEvent e = new ActionEvent(this,
                ActionEvent.ACTION_PERFORMED,
                null);
        for (int i = listeners.length-1; i>=0; i-=1) {
            listeners[i].actionPerformed(e);
        }
    }

    public Object getOrig() {
        return this;
    }

    @Override
    public IComponent getWrapper() {
        return wrapper;
    }

    public String getODTState() {
        return "";
    }

    public void setFormId(String name) {
        wrapper.setFormId(name);
    }

    public String getFormId(){
        return wrapper.getFormId();
    }

    public void setSize(int w, int h) {}

    public void setLayout(Object o) {}
    public void seBounds(int x, int y, int w, int h) {}

}
