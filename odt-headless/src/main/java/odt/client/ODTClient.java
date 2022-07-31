package odt.client;

import odt.client.widget.ActionPanel;
import odt.client.widget.TestFrame;
import odt.context.Context;
import odt.context.ODTComponentFactory;

public class ODTClient {

    private Context context;

    public Context init() {
        context = ODTComponentFactory.getContext(context);
        return context;
    }

    public ODTClient() {
        TestFrame f = new TestFrame();
        context = ODTComponentFactory.getContext(context);
        context.ROOT = f;
        ActionPanel p = new ActionPanel();
        f.setContentPane((IContainer) p.getForm());
        f.setSize(800,600);
        f.setLayout(null);
        f.setVisible(true);
    }
    
}
