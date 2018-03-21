import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
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

    public static void gridWindows(){
        int stageW = Constants.stageW;
        int stageH = Constants.stageH;

        // find out how many windows can fit in a column.
        // get height of window/stageH
        // then find out how many columns you can have
        // get width of window/stageW


        int numInCol = (int)Constants.primaryScreenBounds.getHeight()/stageH;
        int numCols = (int)Constants.primaryScreenBounds.getWidth()/stageW;

        int iC = 0;

        for(int i = 0; i < allWindows.size(); i++){
            Notepad p = allWindows.get(i);

            if (p.getIsPinned()){
                p.setStageLocation(
                        Constants.primaryScreenBounds.getWidth() - (((iC/numInCol)+1)*stageW + (iC/numInCol)*1),
                        (iC%numInCol)*stageH
                );
                iC++;
            }
        }
    }
}
