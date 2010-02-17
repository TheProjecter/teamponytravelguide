package menu;

import com.sun.lwuit.Image;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import main.travelBuddy;

/**
 *
 * @author nialldeasy
 */
public class Map extends Menu {

    double lat = 10, lon = 10;
    travelBuddy M;

    public Map(travelBuddy m) throws IOException {
        super("Map", m, "backround.png");
        setTransitionType(1);
        M = m;
//        lat = M.myLocation.latitude;
 //       lon = M.myLocation.longitude;
        f.setBgImage(null);
    }

    public void update() {
       // lat = M.myLocation.latitude;
       // lon = M.myLocation.longitude;
        try {
            f.setBgImage(Image.createImage("/images/backround.png"));
            f.setBgImage(getMap());
            System.out.println("hello");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Image getMap() throws IOException {
        String Url = "http://maps.google.com/staticmap?center=" +
                lat + "," + lon +
                "&zoom=16&size=200x200&maptype=mobile&markers=" + lat + "," + lon +
                ",ref/&key=MAPS_API_KEY";
        HttpConnection httpConnection = null;
        InputStream inputStream = null;
        byte[] buffer = null;

        try {
            httpConnection = (HttpConnection) Connector.open(Url);
            httpConnection.setRequestMethod(HttpConnection.GET);
            inputStream = httpConnection.openInputStream();
            int length = (int) httpConnection.getLength();
            buffer = new byte[length];
            inputStream.read(buffer);

            int c;
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            while ((c = inputStream.read()) != -1) {
                byteArray.write(c);
            }
            buffer = byteArray.toByteArray();
            byteArray.close();
            inputStream.close();
            httpConnection.close();

        } catch (Exception e) {
            new Alert("Error", "Connection Failed", null, AlertType.ERROR);
        }

        return Image.createImage(buffer, 0, buffer.length);
    }
}
