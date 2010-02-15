package main;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.location.*;


public class loctest extends MIDlet implements CommandListener
{
   private Display display;
   private Form form;
   private Command cmdExit,cmdOK;
   private StringItem si;

   public loctest()
   {
      display = Display.getDisplay(this);
      form = new Form("Location Api test");
      cmdExit = new Command("Exit",Command.EXIT,5);
      cmdOK = new Command("OK",Command.OK,1);
      si = new StringItem("Geo Location", "Click OK");
      form.append(si);
      form.addCommand(cmdOK);
      form.addCommand(cmdExit);
      form.setCommandListener(this);
   }

   public void startApp()
   {
      display.setCurrent(form);
   }

   public void pauseApp()
   {
   }

   public void destroyApp(boolean flag)
   {
      notifyDestroyed();
   }

   public void commandAction(Command c, Displayable d)
   {
      if (c == cmdOK){
         Retriever ret = new Retriever(this);
         ret.start();
         } else if (c == cmdExit) {
            destroyApp(false);
      }
   }

   public void displayString(String string)
   {
      si.setText(string);
   }
}


class Retriever extends Thread
{
   private loctest midlet;
   public Retriever(loctest midlet)
   {
      this.midlet = midlet;
   }

   public void run()
   {
      try
      {
         checkLocation();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         midlet.displayString(ex.toString());
      }
   }

   public void checkLocation() throws Exception
   {
      String string;
      Location l;
      LocationProvider lp;
      Coordinates c;

      Criteria cr= new Criteria();
      cr.setHorizontalAccuracy(500);

      lp= LocationProvider.getInstance(cr);
      l = lp.getLocation(60);

      c = l.getQualifiedCoordinates();
      if(c != null ) {
         // Use coordinate information
         double lat = c.getLatitude();
         double lon = c.getLongitude();
         string = "\nLatitude : " + lat + "\nLongitude : " + lon;
       } else {
         string ="Location API failed";
       }
       midlet.displayString(string);
   }
}