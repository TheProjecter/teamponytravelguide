package menu;

import java.io.IOException;
import main.travelBuddy;

/**
 *
 *
 * 
 * @author nialldeasy
 */
public class MainMenu extends Menu {

    public MainMenu(travelBuddy m) throws IOException {
        super("Main Menu", m, "backround.png");
        setTransitionType(2);
        setLayoutType(1);
        addButtons();
      //  f.show();
        show();

    }

    void addButtons() throws IOException {
        addButton("Map", 2, "map");
        addButton("Camera", 1, "camera");
        addButton("Itinerary", 0, "itinerary");
        addButton("Xe", 0, "money");
       // addButton("Translator", 0, "translate");
    }
    public void update(){

    }
}
