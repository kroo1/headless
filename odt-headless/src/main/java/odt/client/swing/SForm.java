package odt.client.swing;

import odt.client.IComponent;
import odt.client.IDialog;
import odt.client.IForm;
import odt.client.model.ODTField;
import odt.client.widget.ADialog;
import odt.context.Context;
import odt.context.ODTComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

public class SForm extends JPanel implements IForm {

    @Override
    public IComponent getIComponentImpl() {
        return this;
    }

    private Context context = ODTComponentFactory.getContext();

    private final IComponent wrapper;

    public SForm(IComponent wrapper) {
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
    public String getPValue() {
        return wrapper.getPValue();
    }

    @Override
    public void runAction(EventListener l, String[] line) {

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
    public void add(IComponent b) {
        Object orig = b.getOrig();
        if(orig != null && orig instanceof Component) {
            super.add((Component)orig);
        }
    }

    @Override
    public IComponent[] getFields() {
        Component[] cmps = super.getComponents();
        List<IComponent> fields = new ArrayList<>();
        for (Component cmp: cmps) {
            if(cmp instanceof IComponent) {
                IComponent wrapper = ((IComponent)cmp).getWrapper();
                fields.add(wrapper);
            }
        }
        return fields.toArray(new IComponent[fields.size()]);
    }

    @Override
    public String getODTState() {
        return wrapper.getODTState();
    }

    @Override
    public void setSate(Map state) {
        wrapper.setSate(state);
    }


    private IDialog dialog;
    private int dialogResp;
    @Override
    public int showConfirmDialog(String title, int optionType, int messageType) {
        JOptionPane opane = new JOptionPane(this, optionType, messageType);
        dialog = new ADialog() {
            IComponent form = wrapper;
            JOptionPane orig = opane;
            @Override
            public void setValue(Object obj) {
                orig.setValue(obj);
            }

            @Override
            public Object getValue() {
                return orig.getValue();
            }

            @Override
            public IForm getForm() {return (IForm)form;}

            public void closeConfirmDialog() {
                dialogResp = Integer.parseInt(opane.getValue().toString());
                context.dialogEnd(dialog, dialogResp);
                dialog = null;
                dialogResp = -1;
            }
        };
        context.dialogStart(dialog);
        JDialog jDialog = opane.createDialog(title);
        jDialog.setModalityType(ModalityType.MODELESS);
        jDialog.setVisible(true);
        //dialog.closeConfirmDialog();
        //int resp = JOptionPane.showConfirmDialog(dialog, this, title, optionType, messageType);
        return dialogResp;
    }


    @Override
    public void setFormId(String name) {
        wrapper.setFormId(name);
    }

}
