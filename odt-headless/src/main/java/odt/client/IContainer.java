package odt.client;

public interface IContainer extends IComponent {
    
    default IComponent[] getFields() {
        return ((IContainer)getIComponentImpl()).getFields();
    }

}
