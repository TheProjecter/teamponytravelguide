package util;


/**
 *
 * @author nialldeasy
 */
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;
import java.io.IOException;


public class SImage {

    Image image;
    float scale;

    SImage(String Name, float Scale) {
        scale = Scale;

        try {
            Image temp = Image.createImage("/images/" + Name);
            image = createThumbnail(temp, Scale * temp.getWidth(), Scale * temp.getHeight());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    SImage(String Name) {
        scale = 1;

        try {
            Image temp = Image.createImage("/images/" + Name);
            image = createThumbnail(temp, scale * temp.getWidth(), scale * temp.getHeight());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

   public Image scale(int x, int y) {
        image = createThumbnail(image, x, y);
        return image;
    }
    public Image get() {
        return image;
    }

    public Image makeTransparent() {
        int[] rgb = image.getRGB();
        for (int i = 0; i < rgb.length; ++i) {
            if (rgb[i] == 0xffffffff) {
                rgb[i] &= 0x00ffffff;
            }
        }
        return Image.createImage(rgb, image.getWidth(), image.getHeight());



    }

    private Image createThumbnail(Image image, float X, float Y) {
        float sourceWidth = image.getWidth();
        float sourceHeight = image.getHeight();

        float thumbWidth = X;
        float thumbHeight = Y;

        if (thumbHeight == -1) {
            thumbHeight = thumbWidth * sourceHeight / sourceWidth;
        }

        Image thumb = Image.createImage((int) thumbWidth, (int) thumbHeight);
        Graphics g = thumb.getGraphics();

        for (int y = 0; y < thumbHeight; y++) {
            for (int x = 0; x < thumbWidth; x++) {
                g.setClip(x, y, 1, 1);
                float dx = x * sourceWidth / thumbWidth;
                float dy = y * sourceHeight / thumbHeight;
                g.drawImage(image, (int) (x - dx), (int) (y - dy));
            }
        }

        Image immutableThumb = thumb;

        return immutableThumb;
    }
}
