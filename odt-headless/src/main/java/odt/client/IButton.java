package odt.client;

public interface IButton extends IComponent {

    default void doClick() {
        ((IButton)getIComponentImpl()).doClick();
    }

}
