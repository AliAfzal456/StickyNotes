import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;

public class Notepad {

    private int windowNumber;
    static int stageW = 300;
    static int stageH = 250;
    boolean isPinned = false;

    private Stage s;

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
        // initial setup of stage and manager
        Stage stage = new Stage();  // creating the stage
        s = stage;
        managerSetup(); // setup window manager information for this window

        // root creation
        BorderPane root = new BorderPane(); // borderpane, only need top and center

        // title bar creation
        TitleBar bar = createBar();
        root.setTop(bar);

        // css application
        applyCSS(root);


        // body creation
        TextArea body = createTextArea();
        root.setCenter(body);

        // root setting and stage showing
        stage.setScene(new Scene(root, Notepad.stageW, Notepad.stageH));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }


    private TitleButton createAddButton(){
        TitleButton aButton = new TitleButton(TitleBar.maxHeight, TitleBar.maxHeight);

        Image image = new Image(getClass().getResourceAsStream("img/ic_add_button.png"));
        aButton.setGraphic(new ImageView(image));

        aButton.setOnAction((event ->{
            WindowManager.spawnWindow();
        }));

        return aButton;
    }

    public void setStageLocation(double x, double y) {
        s.setX(x);
        s.setY(y);
    }

    public boolean getIsPinned(){
        return isPinned;
    }

    private ToggleButton createStickyButton(){
        ToggleButton sButton = new ToggleButton();
        sButton.setPrefSize(TitleBar.maxHeight, TitleBar.maxHeight);

        Image image = new Image(getClass().getResourceAsStream("img/ic_pin_button.png"));
        sButton.setGraphic(new ImageView(image));

        sButton.setOnAction((event -> {
            if (sButton.isSelected()){
                isPinned = true;
                ((Stage)(((ToggleButton)event.getSource()).getScene().getWindow())).setAlwaysOnTop(true);
            }

            else{
                isPinned = false;

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
        TitleButton xButton = new TitleButton(TitleBar.maxHeight, TitleBar.maxHeight);


        Image image = new Image(getClass().getResourceAsStream("img/ic_x_button.png"));
        xButton.setGraphic(new ImageView(image));

        xButton.setOnAction((event -> {
            WindowManager.destroyWindow(Notepad.this);
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
        String css = this.getClass().getResource("CSS/titlebar.css").toExternalForm();
        root.getStylesheets().add(css);
    }

    private TitleButton createGridButton(){
        TitleButton gButton = new TitleButton(TitleBar.maxHeight, TitleBar.maxHeight);


        Image image = new Image(getClass().getResourceAsStream("img/ic_grid_button.png"));
        gButton.setGraphic(new ImageView(image));


        gButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WindowManager.gridWindows();
            }
        });

        return gButton;
    }

    private TitleBar createBar(){
        TitleBar bar = new TitleBar(" #336699;");

        TitleButton xButton = createXButton();
        ToggleButton stickyButton = createStickyButton();
        TitleButton addButton = createAddButton();

        // experimental button: gridding windows
        TitleButton gridButton = createGridButton();

        Region r1 = new Region();
        HBox.setHgrow(r1, Priority.ALWAYS);

        bar.addNode(addButton);
        bar.addNode(stickyButton);
        bar.addNode(gridButton);
        bar.addNode(r1);
        bar.addNode(xButton);

        return bar;
    }
}
