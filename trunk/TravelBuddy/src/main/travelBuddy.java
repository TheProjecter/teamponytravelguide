package main;


import java.io.IOException;
import javax.microedition.midlet.*;
import com.sun.lwuit.*;
import com.sun.lwuit.events.*;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;
import com.sun.lwuit.plaf.UIManager;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import menu.*;
import util.locationService;

public class travelBuddy extends MIDlet implements ActionListener {

    int x = 0;
    static Vector menus = new Vector();
    public locationService myLocation;
    private Timer myRefreshTimer = new Timer ();
    private TimerTask myRefreshTask = null;
    String api_key = "ABQIAAAAINBylDrmCzP0hjBny0xjShQx1blDeS5eQkHek9a605WmfCbw4xTw-n6QbEfrlpLvuWly_kOemXZ_VQ";
    public void startApp() {
            Display.init(this);
            Hashtable themeProps = new Hashtable();
            themeProps.put("SoftButton.fgColor", "0");
            themeProps.put("Title.fgColor", "ffffff");
            themeProps.put("fgSelectionColor", "ffffff");
            themeProps.put("bgColor", "0");
            themeProps.put("bgSelectionColor", "0");
            themeProps.put("transparency", "0");
            themeProps.put("Button.transparency", "0");
            UIManager.getInstance().setThemeProps(themeProps);
            Style s = UIManager.getInstance().getComponentStyle("menu");
            UIManager.getInstance().setComponentStyle("menu", s);

        try {
            buildMenus();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        try {
//            myLocation = new locationService(false);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//            myLocation.start();
            myRefreshTask = new RefreshTask();
            myRefreshTimer.schedule(myRefreshTask, 0, 50);

    }

    private class RefreshTask extends TimerTask {
        public RefreshTask(){
        }
        public void run () {
          getCurrentMenu().update();
        }
    }
    public void buildMenus() throws IOException{
        menus.addElement(new MainMenu(this));
        menus.addElement(new Currency(this,"paper.jpg"));
        menus.addElement(new Map(this));
    }
    public Menu getMenu(int i){
        return (Menu) menus.elementAt(i);
    }
    public Menu getCurrentMenu(){
        Menu ret = null;
        for(int i=0;i<menus.size();i++){
            Menu curr = (Menu)menus.elementAt(i);
            if(curr.isActive){
                ret = curr;
            }
        }
        return ret;
    }
    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void actionPerformed(ActionEvent ae) {
        System.out.println(ae.getKeyEvent());
    }
    protected void paint(Graphics g, int w, int h) {
    }

}
