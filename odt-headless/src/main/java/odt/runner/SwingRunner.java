package odt.runner;

import java.util.List;

import javax.swing.SwingWorker;

import odt.context.Context;
import odt.context.ODTComponentFactory;

public class SwingRunner extends SwingWorker<List<String>, String> implements IRunner {

    private Context context = ODTComponentFactory.getContext();
    
    private List<String> lines;
    public SwingRunner(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public List<String> doInBackground() {
        for (int i = 0; i < lines.size(); i++) {
            publish(lines.get(i));
        }
        return null;
    }


    @Override
    protected void process(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            context.runAction(lines.get(i));
        }
    }

    public void exec() {
        execute();
    }
    
}