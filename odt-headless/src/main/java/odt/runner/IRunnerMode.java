package odt.runner;

import java.io.IOException;

public interface IRunnerMode {

    default void saveState(String state, int action_counter){

    }
    default int getStateMode() {
        return -1;
    }
    void reset();
    String loadState0() throws IOException;
    String loadActions();
    void saveActions(String string);
    
}
