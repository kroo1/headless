package odt.client;

import javax.swing.event.DocumentListener;

public interface ITextField extends IComponent {
    
    default void addDocumentListener(DocumentListener documentListener) {
        ((ITextField)getIComponentImpl()).addDocumentListener(documentListener);
    }

    default String getText() {
        return ((ITextField)getIComponentImpl()).getText();
    }

    default void setText(String value) {
        ((ITextField)getIComponentImpl()).setText(value);
    }

    default Object getDocument() {
        return ((ITextField)getIComponentImpl()).getDocument();
    }

}
