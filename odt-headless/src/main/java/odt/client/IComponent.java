package odt.client;

import java.util.EventListener;
import java.util.Map;

import odt.client.model.ODTField;

import java.awt.event.ActionListener;

public interface IComponent {

    IComponent getIComponentImpl();

    default ODTField persist(){
        return null;
    }
    default String getName(){
        return "";
    }
    default void setSate(Map<String, Object> iState){
        
    }
    default String getODTState(){
        return null;
    }
    default String getPValue(){
        return null;
    }

    default void runAction(EventListener l, String[] line){
        
    }
    default Object getOrig(){
        return getIComponentImpl();
    }
    default IComponent getWrapper(){
        return null;
    }

    default void setBounds(int x, int y, int w, int h){
        getIComponentImpl().setBounds(x, y, w, h);
    }

    default void setFormId(String name){
        
    }

    default String getFormId(){
        return "Nincs megadva";
    }

    default boolean testAction() {
        return true;
    }

    default void setEnabled(boolean enable) {
        getIComponentImpl().setEnabled(enable);
    }

    default void setSelected(boolean selected) {
        getIComponentImpl().setSelected(selected);
    }

    default void setVisible(boolean visible) {
        getIComponentImpl().setVisible(visible);
    }

    default void addActionListener(ActionListener actionListener) {
        
    }

    default void setName(String name) {
        getIComponentImpl().setName(name);
    }

    default ActionListener[] getActionListeners() {
        return null;
    }

    default void removeActionListener(ActionListener l) {
        
    }

    default boolean isEnabled() {
        return getIComponentImpl().isEnabled();
    }

    default boolean isSelected() {
        return getIComponentImpl().isSelected();
    }

    default boolean isVisible() {
        return getIComponentImpl().isVisible();
    }
}
