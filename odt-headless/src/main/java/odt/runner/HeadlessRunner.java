package odt.runner;

import java.util.List;

import odt.context.Context;
import odt.context.ODTComponentFactory;

public class HeadlessRunner implements IRunner {

    private Context context = ODTComponentFactory.getContext();

    private List<String> lines;
    public HeadlessRunner(List<String> lines) {
        this.lines = lines;
    }

    protected void process(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            context.runAction(lines.get(i));
        }
    }

    public void exec() {
        process(lines);
    }
}
