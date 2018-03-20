import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * window manager
 * responsible for spawning new windows and making sure the sticky function works properly
 */
public class WindowManager {
    static int numWindows = 0;
    static ArrayList<Notepad> allWindows = new ArrayList<>();

    public static void spawnWindow(){
        Notepad n  = new Notepad();
        System.out.println(numWindows);
        System.out.println(allWindows.toString());
    }


    public static void destroyWindow(Notepad s){
        allWindows.remove(s);
        numWindows -= 1;
    }
}
