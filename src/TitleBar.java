import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.File;
import java.util.PriorityQueue;

/**
 * TitleBar.java
 *
 * Will create a draggable titlebar
 */
public class TitleBar extends HBox{

    private final int maxHeight = 32;
    private IntegerProperty windowNumber = new SimpleIntegerProperty();
    /**
     * constructor
     * can possible pass list of icons, etc in the future
     */
    public TitleBar(String color){
        // set the window number
        windowNumber.set(WindowManager.numWindows + 1);
        WindowManager.numWindows += 1;

        makeDraggable(this); // make it draggable since undecorated can't be dragged
        setHeights();  // height of the title-bar

        // TODO: Move these styles to the css file as well
        styleBox(color); // styling the box

        //-------------------------
        // add css file
        //-------------------------
        File f = new File("CSS/titlebar.css");
        getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

        //--------------------------
        // add listener to window count
        //--------------------------
        windowNumber.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updatePos();
            }
        });

        //--------------------------
        // adding buttons
        //--------------------------
        TitleButton xButton = createXButton();
        ToggleButton stickyButton = createStickyButton();
        TitleButton addButton = createAddButton();

        Region r1 = new Region();
        HBox.setHgrow(r1, Priority.ALWAYS);

        getChildren().addAll(addButton, stickyButton, r1, xButton);
    }

    private void updatePos(){

    }

    public void setWindowNumber(int number){
        windowNumber.set(number);
    }

    public int getWindowNumber(){
        return windowNumber.get();
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
                ((Stage)(((ToggleButton)event.getSource()).getScene().getWindow())).setAlwaysOnTop(true);
            }

            else{
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


    /**
     * styleBox
     * @param color
     *
     * adds the styles for the title-bar (background color, shadow)
     * takes a color parameter in case we want to change it later
     */
    private void styleBox(String color){
        setStyle("-fx-background-color:" + color +
                "-fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");
    }


    /**
     * setHeights
     *
     * simple method to set the height of the HBox
     * its put into its own method to make resizing easier
     */
    private void setHeights(){
        setHeight(maxHeight);
        setMaxHeight(maxHeight);
        setPrefHeight(maxHeight);
    }


    /**
     * makeDraggable
     * @param node
     *
     * takes node paramater and attaches listeners to it
     */
    private double startX = 0;
    private double startY = 0;
    private void makeDraggable(Node node){

        node.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY){
                    startX = event.getSceneX();
                    startY = event.getSceneY();
                }
            }
        });

        node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY){
                    node.getScene().getWindow().setX(event.getScreenX()-startX);
                    node.getScene().getWindow().setY(event.getScreenY()-startY);
                }
            }
        });
    }
}
