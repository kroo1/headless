package odt.client;

public interface IForm extends IContainer {

    default int showConfirmDialog(String dialog, int okCancelOption, int plainMessage){
        return ((IForm)getIComponentImpl()).showConfirmDialog(dialog, okCancelOption, plainMessage);
    }

    default void add(IComponent comp){
        ((IForm)getIComponentImpl()).add(comp);
    }

    
}
