package odt.runner;

import java.io.IOException;

public class RestRunnerMode implements IRunnerMode {

    @Override
    public void reset() {
    }

    @Override
    public String loadState0() throws IOException {
        return null;
    }

    @Override
    public String loadActions() {
        return null;
    }

    @Override
    public void saveState(String state, int action_counter) {
        
    }

    @Override
    public void saveActions(String string) {
        
    }
    
}
