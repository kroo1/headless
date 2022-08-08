package odt.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ClientRunnerMode implements IRunnerMode {

    @Override
    public void saveState(String state, int action_counter) {
        createDir();
        if(DIR_PATH != null) {
            try {
                File stateFile = new File(DIR_PATH + "/state_"+action_counter);
                if (!stateFile.exists()) {
                    //stateFile.createNewFile();
                    FileWriter writer = new FileWriter(stateFile);
                    writer.write(state);
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String DIR_NAME;
    private String LOAD_DIR_PATH;
    private String DIR_PATH;

    @Override
    public void reset() {
        DIR_NAME = null;
        LOAD_DIR_PATH = null;
        DIR_PATH = null;
    }

    private void createDir() {
        if(DIR_PATH == null) {
            DIR_NAME = "ODTest_" + (new SimpleDateFormat("YYYYMMdd_HHMMSS").format(new Date()));
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Válasszon könyvtárat!");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int userSelection = fileChooser.showSaveDialog(new JFrame());

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                File dir = new File(fileToSave.getAbsolutePath() + "/" + DIR_NAME);
                if (!dir.exists()) {
                    dir.mkdirs();
                    DIR_PATH = dir.getAbsolutePath();
                }
            }
        }
    }

    private void loadDir() {
        if(LOAD_DIR_PATH == null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Válasszon könyvtárat!");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int userSelection = fileChooser.showOpenDialog(new JFrame());

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                loadDirH(fileChooser.getSelectedFile());
            }
        }
    }

    private void loadDirH(File fileToSave) {
        String time = (new SimpleDateFormat("YYYYMMdd_HHMMSS").format(new Date()));
        LOAD_DIR_PATH = fileToSave.getAbsolutePath();
        String lDirName = "Result_" + fileToSave.getName() + "_" + time;
        File dir = new File(fileToSave.getParentFile().getAbsolutePath() + "/" + lDirName);
        if (!dir.exists()) {
            dir.mkdirs();
            DIR_PATH = dir.getAbsolutePath();
        }
    }

    public String loadFileContent(File file) {
        StringBuilder res = new StringBuilder();
        BufferedReader br = null;
        try {
            FileReader reader = new FileReader(file);
            br = new BufferedReader(reader);
            String line;
            while((line = br.readLine())!=null) {
                res.append(line).append("\n");
            }
            return res.toString();
        }catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }finally {
            if(br != null)
                try {
                    br.close();
                } catch (IOException ex2) {
                    ex2.printStackTrace();
                }
        }
    }

    @Override
    public String loadState0() {
        loadDir();
        File state0 = new File(LOAD_DIR_PATH + "/" + "state_0");
        return loadFileContent(state0);
    }

    @Override
    public String loadActions() {
        File actions = new File(LOAD_DIR_PATH + "/" + "actions");
        return loadFileContent(actions);
    }

    @Override
    public void saveActions(String actions) {
        if(DIR_PATH != null) {
            try {
                File actionFile = new File(DIR_PATH + "/actions");
                FileWriter writer = new FileWriter(actionFile);
                writer.write(actions);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
