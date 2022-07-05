package odt.client;

import javax.swing.event.DocumentListener;

public interface ITextField {
    void addDocumentListener(DocumentListener documentListener);

    String getText();

    boolean isEnabled();

    boolean isVisible();

    String getName();

    void setEnabled(boolean enable);

    void setVisible(boolean visible);

    void setText(String value);

    Object getOrig();

    void setName(String name);

    void setBounds(int x, int y, int w, int h);

    Object getDocument();
}
