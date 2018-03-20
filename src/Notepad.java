import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.File;

public class Notepad {

    private final int maxHeight = 32;
    private int windowNumber;

    public void setWindowNumber(int windowNumber){
        this.windowNumber = windowNumber;
    }

    public int getWindowNumber(){
        return windowNumber;
    }

    public void managerSetup(){
        WindowManager.allWindows.add(this);
        WindowManager.numWindows += 1;
        setWindowNumber(WindowManager.numWindows);
    }

    public Notepad(){
        Stage stage = new Stage();  // creating the stage
        managerSetup(); // setup window manager information for this window

        BorderPane root = new BorderPane(); // borderpane, only need top and center
        TitleBar bar = createBar();
        root.setTop(bar);
        applyCSS(root);


        //----
        TextArea body = createTextArea();
        root.setCenter(body);

        stage.setScene(new Scene(root, 300, 250));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }


    private TitleButton createAddButton(){
        TitleButton aButton = new TitleButton(maxHeight, maxHeight);
        aButton.setBgText("+");

        aButton.setOnAction((event ->{
            WindowManager.spawnWindow();
        }));

        return aButton;
    }

    private ToggleButton createStickyButton(){
        ToggleButton sButton = new ToggleButton("P");
        sButton.setPrefSize(maxHeight, maxHeight);

        sButton.setOnAction((event -> {
            if (sButton.isSelected()){
                //isPinned = true;
                ((Stage)(((ToggleButton)event.getSource()).getScene().getWindow())).setAlwaysOnTop(true);
            }

            else{
                //isPinned = false;
                ((Stage)(((ToggleButton)event.getSource()).getScene().getWindow())).setAlwaysOnTop(false);
            }
        }));

        return sButton;
    }

    /**
     * addXButton
     *
     * adds the x button to the top right
     */
    private TitleButton createXButton(){
        TitleButton xButton = new TitleButton(maxHeight, maxHeight);
        xButton.setBgText("X");

        xButton.setOnAction((event -> {
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        }));

        // return button
        return xButton;
    }

    private TextArea createTextArea(){
        // credit to https://stackoverflow.com/questions/23728517/blurred-text-in-javafx-textarea for fixing blurry text
        TextArea body = new TextArea();
        body.setCache(false);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                body.requestFocus();
                ScrollPane sp = (ScrollPane)body.getChildrenUnmodifiable().get(0);
                sp.setCache(false);
                for (Node n : sp.getChildrenUnmodifiable()) {
                    n.setCache(false);
                }
            }
        });
        body.setWrapText(true);

        return body;
    }


    private void applyCSS(Pane root){
        //-------------------------
        // add css file
        //-------------------------
        File f = new File("CSS/titlebar.css");
        root.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
    }

    private TitleBar createBar(){
        TitleBar bar = new TitleBar(" #336699;");

        TitleButton xButton = createXButton();
        ToggleButton stickyButton = createStickyButton();
        TitleButton addButton = createAddButton();

        Region r1 = new Region();
        HBox.setHgrow(r1, Priority.ALWAYS);

        bar.addNode(addButton);
        bar.addNode(stickyButton);
        bar.addNode(r1);
        bar.addNode(xButton);

        return bar;
    }
}
