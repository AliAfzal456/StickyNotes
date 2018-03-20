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
        int removed = s.myBar.getWindowNumber();

        for (Notepad stage: allWindows) {
            // for each stage, decrease its number counter
            System.out.println(stage.myBar.getWindowNumber());
            if (stage.myBar.getWindowNumber() > removed){
                stage.myBar.setWindowNumber(stage.myBar.getWindowNumber() - 1);
            }
        }
    }
}
