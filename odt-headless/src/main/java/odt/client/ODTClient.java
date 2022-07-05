package odt.client;

import odt.client.widget.ActionPanel;
import odt.client.widget.TestFrame;

public class ODTClient {

    public ODTClient() {
        TestFrame f = new TestFrame();
        Context context = ODTComponentFactory.getContext();
        context.ROOT = f;
        ActionPanel p = new ActionPanel();
        f.setContentPane((IContainer) p.getForm());
        f.setSize(800,600);
        f.setLayout(null);
        f.setVisible(true);
    }
    
}
