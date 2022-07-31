package odt.context;

import java.io.File;
import java.io.IOException;

import javax.swing.event.DocumentListener;

import odt.client.IComponent;
import odt.client.IDialog;
import odt.runner.IRootContainer;
import odt.runner.ODTRunnerHelper;

import java.awt.event.ActionListener;

public class Context {

    public static boolean TEST_MODE = true;
    public IRootContainer ROOT;
    public static boolean NOTEST = false;

    public Context() {
        oDTRunnerHelper = new ODTRunnerHelper(this);
    }

    private final ODTRunnerHelper oDTRunnerHelper;

    public void runTest() throws IOException {
        oDTRunnerHelper.runTest();
    }

    public void runTest(File fileToSave) throws IOException {
        oDTRunnerHelper.runTest(fileToSave);
    }

    public void runActions(String actions) throws IOException {
        oDTRunnerHelper.runActions(actions);
    }

    public ActionListener getActionListener(IComponent c, ActionListener l) {
        return oDTRunnerHelper.getActionListener(c, l);
    }

    public DocumentListener getDocumentListener(IComponent c, DocumentListener l) {
        return oDTRunnerHelper.getDocumentListener(c, l);
    }

    public void startTest() {
        oDTRunnerHelper.startTest();
    }

    public void runAction(String line) {
        oDTRunnerHelper.runAction(line);
    }

    public void dialogStart(IDialog dialog) {
        oDTRunnerHelper.dialogStart(dialog);
    }

    public void dialogEnd(IDialog dialog, int closeAction) {
        oDTRunnerHelper.dialogEnd(dialog, closeAction);
    }

    public String getState() {
        return oDTRunnerHelper.getState();
    }
    
}