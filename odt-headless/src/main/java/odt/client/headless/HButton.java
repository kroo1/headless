package odt.client.headless;

import odt.client.IButton;
import odt.client.IComponent;

import java.util.EventListener;

public class HButton extends AComponent implements IButton {

    public HButton(String s, IComponent wrapper) {
        super(s, wrapper);
    }

    @Override
    public void doClick() {
        fireActionPerformed(null);
    }

    @Override
    public void runAction(EventListener l, String[] line) {
        doClick();
    }
}
