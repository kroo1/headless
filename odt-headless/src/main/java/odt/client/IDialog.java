package odt.client;

public interface IDialog extends IContainer{

    default void setValue(Object obj){
        ((IDialog)getIComponentImpl()).setValue(obj);
    }
    default Object getValue(){
        return ((IDialog)getIComponentImpl()).getValue();
    }
    default IForm getForm(){
        return ((IDialog)getIComponentImpl()).getForm();
    }
    default void closeConfirmDialog(){
        ((IDialog)getIComponentImpl()).closeConfirmDialog();
    }
    
}
