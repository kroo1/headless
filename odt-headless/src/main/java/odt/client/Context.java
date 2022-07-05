package odt.client;

import java.io.File;
import java.io.IOException;

import javax.swing.event.DocumentListener;

import odt.client.tester.IRootContainer;
import odt.client.tester.ODTesterMain;

import java.awt.event.ActionListener;

public class Context {

    public static boolean TEST_MODE = true;
    public IRootContainer ROOT;
    public static boolean NOTEST = false;

    public void init() {
        oDTesterMain = new ODTesterMain();
    }


    private ODTesterMain oDTesterMain;

    public void runTest() throws IOException {
        oDTesterMain.runTest();
    }

    public void runTest(File fileToSave) throws IOException {
        oDTesterMain.runTest(fileToSave);
    }

    public void runActions(String actions) throws IOException {
        oDTesterMain.runActions(actions);
    }

    public ActionListener getActionListener(IComponent c, ActionListener l) {
        return oDTesterMain.getActionListener(c, l);
    }

    public DocumentListener getDocumentListener(IComponent c, DocumentListener l) {
        return oDTesterMain.getDocumentListener(c, l);
    }

    public void startTest() {
        oDTesterMain.startTest();
    }

    public void runAction(String line) {
        oDTesterMain.runAction(line);
    }

    public void dialogStart(IDialog dialog) {
        oDTesterMain.dialogStart(dialog);
    }

    public void dialogEnd(IDialog dialog, int closeAction) {
        oDTesterMain.dialogEnd(dialog, closeAction);
    }

    public String getState() {
        return oDTesterMain.getState();
    }
    
}