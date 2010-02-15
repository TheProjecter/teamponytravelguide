package util;


import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author nialldeasy
 */
public class Text {

    String text;
    int x;
    int y;
    int anchor;
    int color;
    int size;
    boolean bold=false;


    public static final int WHITE = 0xFFFFFF;
    public static final int BLACK = 0x000000;
    public static final int BLUE = 0x0000FF;
    public static final int LIGHT_GRAY = 0xAAAAAA;
    public static final int DARK_GRAY = 0x555555;

    public Text(String Text, int X,int Y,int Anchor){
        text=Text;
        x=(int)(X-Text.length()*3.5);
        y=Y;
        anchor=Anchor;
        color=BLACK;
        size=0;

    }
    public Text setText(String T){
        text=T;
        return this;
    }
    public Text setPos(int X,int Y){
        x=X;
        y=Y;
        return this;
    }
    public void setFontStyle(int i){
        if(i==0){
            
        }
        if(i==1){
            
        }
        if(i==2){
            
            
        }
    }
    public void setFontSize(int i){
        if(i<=2){
            size=i;
        }
    }
    public void bold(boolean BoldOn){
        bold=BoldOn;
    }
    public void setColor(int Color){
        color=Color;
    }
    public void draw(Graphics g){
        Font font = null;
        if(bold){
            if(size==0){
            font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD,Font.SIZE_SMALL );
            }
            if(size==1){
            font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD,Font.SIZE_MEDIUM );
            }
            if(size==2){
            font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD,Font.SIZE_LARGE );
            }
        }else{
             if(size==0){
            font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN,Font.SIZE_SMALL );
            }
            if(size==1){
            font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN,Font.SIZE_MEDIUM );
            }
            if(size==2){
            font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN,Font.SIZE_LARGE );
            }
        }


            g.setFont(font);
            g.setColor(color);
            g.drawString(text, x, y, anchor);
            font = Font.getDefaultFont();
            g.setFont(font);
            g.setColor(BLACK);
    }
}







