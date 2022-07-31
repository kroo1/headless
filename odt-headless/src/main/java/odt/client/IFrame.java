package odt.client;

import odt.runner.IRootContainer;

public interface IFrame extends IRootContainer {

    void setContentPane(IContainer form);


    default void setSize(int x, int y){
        ((IFrame)getIComponentImpl()).setSize(x, y);
    }


    default void setLayout(Object obj){
        ((IFrame)getIComponentImpl()).setLayout(obj);
    }


    default void seBounds(int x, int y, int w, int h){
        ((IFrame)getIComponentImpl()).seBounds(x, y, w, h);
    }


}
