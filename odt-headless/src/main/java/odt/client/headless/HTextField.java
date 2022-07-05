package odt.client.headless;

import odt.client.IComponent;
import odt.client.ITextField;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;

public class HTextField extends AComponent implements ITextField {

    private Document doc = new Document() {
        @Override
        public int getLength() {
            return 0;
        }

        @Override
        public void addDocumentListener(DocumentListener listener) {

        }

        @Override
        public void removeDocumentListener(DocumentListener listener) {

        }

        @Override
        public void addUndoableEditListener(UndoableEditListener listener) {

        }

        @Override
        public void removeUndoableEditListener(UndoableEditListener listener) {

        }

        @Override
        public Object getProperty(Object key) {
            return null;
        }

        @Override
        public void putProperty(Object key, Object value) {

        }

        @Override
        public void remove(int offs, int len) throws BadLocationException {

        }

        @Override
        public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {

        }

        @Override
        public String getText(int offset, int length) throws BadLocationException {
            return null;
        }

        @Override
        public void getText(int offset, int length, Segment txt) throws BadLocationException {

        }

        @Override
        public Position getStartPosition() {
            return null;
        }

        @Override
        public Position getEndPosition() {
            return null;
        }

        @Override
        public Position createPosition(int offs) throws BadLocationException {
            return null;
        }

        @Override
        public Element[] getRootElements() {
            return new Element[0];
        }

        @Override
        public Element getDefaultRootElement() {
            return null;
        }

        @Override
        public void render(Runnable r) {

        }
    };

    public HTextField(IComponent wrapper) {
        super(wrapper);

    }

    private DocumentListener dl;

    @Override
    public void addDocumentListener(DocumentListener documentListener) {
        dl = documentListener;
    }

    private String text;

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String value) {
        text = value;
        if(dl != null) dl.insertUpdate(new DocumentEvent() {
            @Override
            public int getOffset() {
                return 0;
            }

            @Override
            public int getLength() {
                return 0;
            }

            @Override
            public Document getDocument() {
                return doc;
            }

            @Override
            public EventType getType() {
                return null;
            }

            @Override
            public ElementChange getChange(Element elem) {
                return null;
            }
        });
    }
    @Override
    public Object getDocument() {
        return doc;
    }
}
