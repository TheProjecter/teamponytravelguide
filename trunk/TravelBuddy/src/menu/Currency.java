package menu;

import java.io.IOException;
import main.travelBuddy;
import util.Color;
/**
 *
 * @author nialldeasy
 */
public class Currency extends Menu{


    public Currency(travelBuddy m,String backround) throws IOException{
        super("Currency Convertor",m,backround);
        setTransitionType(1);
        addButton("Menu",0, "map",color.Black);
    }
   

}
