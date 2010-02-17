package util;


import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;
import java.io.IOException;

/**
 *
 * @author nialldeasy
 */
public class PosImage extends SImage{

    int x;
    int y;

    PosImage(String Name, int X, int Y, float Scale) {
        super(Name,Scale);
        x = X;
        y = Y;
        scale = Scale;
    }
   PosImage(String Name, int X, int Y) {
        super(Name,1);
        x = X;
        y = Y;
    }


    public void draw(Graphics g) {
        g.drawImage(image, x, y);

    }



}
