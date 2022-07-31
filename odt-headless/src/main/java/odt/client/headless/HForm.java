package odt.client.headless;

import odt.client.IComponent;
import odt.client.IDialog;
import odt.client.IForm;
import odt.client.widget.ADialog;

import java.util.Vector;

public class HForm extends AComponent implements IForm {
    public HForm(IComponent wrapper) {
        super(wrapper);
    }

    private IDialog dialog;
    private int dialogResp;

    private IComponent getFormWrapper() {
        return getWrapper();
    }

    @Override
    public int showConfirmDialog(String title, int optionType, int messageType) {
        dialog = new ADialog() {
            IComponent form = getFormWrapper();
            Object value;
            @Override
            public void setValue(Object obj) {
                value = obj;
            }

            @Override
            public Object getValue() {
                return value;
            }

            @Override
            public IForm getForm() {return (IForm)form;}

            public void closeConfirmDialog() {
                dialogResp = Integer.parseInt(dialog.getValue().toString());
                context.dialogEnd(dialog, dialogResp);
                dialog = null;
                dialogResp = -1;
            }
        };

        context.dialogStart(dialog);
        return dialogResp;
    }

    private Vector<IComponent> comps = new Vector<>();
    @Override
    public void add(IComponent b) {
        comps.add(b);
    }

    @Override
    public IComponent[] getFields() {
        return comps.toArray(new IComponent[comps.size()]);
    }
}
