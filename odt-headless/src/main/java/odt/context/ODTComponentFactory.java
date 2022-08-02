package odt.context;

import java.util.List;

import odt.client.IButton;
import odt.client.IComboBox;
import odt.client.IComponent;
import odt.client.IForm;
import odt.client.IFrame;
import odt.client.ITextField;
import odt.client.headless.*;
import odt.client.swing.*;
import odt.runner.HeadlessRunner;
import odt.runner.IRunner;
import odt.runner.SwingRunner;

public class ODTComponentFactory {

    public static final boolean SWING_MODE = true;

    public static IButton createButton(String s, IComponent wrapper) {
        if(SWING_MODE) {
            return new SButton(s, wrapper);
        }else {
            return new HButton(s, wrapper);
        }
    }

    public static IForm createForm(IComponent wrapper) {
        if(SWING_MODE) {
            return new SForm(wrapper);
        }else {
            return new HForm(wrapper);
        }
    }

    public static IFrame createFrame(IComponent wrapper) {
        if(SWING_MODE) {
            return new SFrame(wrapper);
        }else {
            return new HFrame(wrapper);
        }
    }

    public static ITextField createTextField(IComponent wrapper) {
        if(SWING_MODE) {
            return new STextField(wrapper);
        }else {
            return new HTextField(wrapper);
        }
    }

    public static IComboBox createComboBox(String[] list, IComponent wrapper) {
        if(SWING_MODE) {
            if(list == null) {
                return new SComboBox(wrapper);
            }else {
                return new SComboBox(list, wrapper);
            }
        }else {
            if(list == null) {
                return new HComboBox(wrapper);
            }else {
                return new HComboBox(list, wrapper);
            }
        }
    }

    public static IRunner createRunner(List<String> lines) {
        if(SWING_MODE) {
            //return new SwingRunner(lines);
            return new HeadlessRunner(lines);
        }else {
            return new HeadlessRunner(lines);
        }
    }

    public static void getState() {
        getContext().getState();
    }



    private static Context context;
    private static ThreadLocal<Context> hcontext;

    public static Context getContext() {
        return getContext(null);
    }

    public static Context getContext(Context csaved) {
        if(SWING_MODE) {
            if(context == null) {
                context = new Context();
                //context.init();
            }
            return context;
        }else {
            if(hcontext == null) {
                hcontext = new ThreadLocal<>();
            }
            if(csaved != null) {
                hcontext.set(csaved);
            }
            Context c = hcontext.get();
            if(c == null) {
                c = new Context();
                hcontext.set(c);
                //c.init();
            }
            return hcontext.get();
        }
    }
    
}
