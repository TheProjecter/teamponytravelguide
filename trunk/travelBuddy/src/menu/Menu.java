package menu;

import com.sun.lwuit.Button;
import com.sun.lwuit.Component;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.GridLayout;
import java.io.IOException;

import com.sun.lwuit.animations.*;
import main.travelBuddy;

/**
 *
 * @author nialldeasy
 */
public class Menu {

    travelBuddy m;
    Form f;
    public boolean isActive;

    public Menu(String Name, travelBuddy M, String backround) throws IOException {
        m = M;
        f = new Form(Name);
//        f.setLayout(new GridLayout(10, 2));
//        f.setScrollable(true);
//        f.setCyclicFocus(true);
//       // f.setVisible(true);
        //  f.setFocusable(true);
        f.setBgImage(Image.createImage("/images/" + backround));
        f.setTransitionOutAnimator(
                CommonTransitions.createSlide(
                CommonTransitions.SLIDE_HORIZONTAL, true, 200));
                
        isActive=false;

    }
      public Menu(String Name, travelBuddy M) throws IOException {
        m = M;
        f = new Form(Name);
//        f.setLayout(new GridLayout(10, 2));
//        f.setScrollable(true);
//        f.setCyclicFocus(true);
//       // f.setVisible(true);
        //  f.setFocusable(true);
        f.setTransitionOutAnimator(
                CommonTransitions.createSlide(
                CommonTransitions.SLIDE_HORIZONTAL, true, 200));

        isActive=false;

    }

    public void update() {
        
    }

    public Component getFocused() {
        return f.getFocused();
    }

    public void addButton(String name, int next, String picture) throws IOException {
        Button a = new Button(Image.createImage("/images/" + picture + "_unsel.png"));
        a.setUIID(name);
        a.setText(name);
        a.setRolloverIcon(Image.createImage("/images/" + picture + "_sel.png"));
        a.setAlignment(Label.CENTER);
        a.setTextPosition(Label.BOTTOM);
        a.setFocusable(true);
        a.addActionListener(new ButtonActionListener(m, next));
        f.addComponent(a);


    }

    public void show() {
        f.show();
        isActive=true;
    }

    /**
     * 0=None
     * 1=2*10 grid
     * 2=1*10 grid
     */
    public void setLayoutType(int i) {
        if (i == 0) {
            f.setLayout(null);
        }
        if (i == 1) {
            f.setLayout(new GridLayout(10, 2));
        }
        if (i == 2) {
            f.setLayout(new GridLayout(10, 1));
        }
        if (i == 3) {
        }
        if (i == 4) {
        }


    }

    /**
     * 0=None
     * 1=slide right
     * 2=slide left
     * 3=fade quickly
     * 4=fade slowly
     */
    public void setTransitionType(int i) {
        if (i == 0) {
            f.setTransitionOutAnimator(null);
        }
        if (i == 1) {
            f.setTransitionOutAnimator(
                    CommonTransitions.createSlide(
                    CommonTransitions.SLIDE_HORIZONTAL, true, 200));
        }
        if (i == 2) {
            f.setTransitionOutAnimator(
                    CommonTransitions.createSlide(
                    CommonTransitions.SLIDE_HORIZONTAL, false, 200));
        }
        if (i == 3) {
        }
        if (i == 4) {
        }
    }

    private class ButtonActionListener implements ActionListener {

        int Next;

        ButtonActionListener(travelBuddy m, int next) {
            Next = next;
        }

        public void actionPerformed(ActionEvent evt) {
            m.getMenu(Next).show();
            isActive=false;
        }
    }
}
