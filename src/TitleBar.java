import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.List;


/**
 * TitleBar.java
 *
 * Will create a draggable titlebar
 */
public class TitleBar extends HBox{

    private final int maxHeight = 32;


    /**
     * constructor
     * can possible pass list of icons, etc in the future
     */
    public TitleBar(String color){
        makeDraggable(this); // make it draggable since undecorated can't be dragged
        setHeights();  // height of the title-bar

        // TODO: Move these styles to the css file as well
        styleBox(color); // styling the box
    }

    public void addNodes(List<Node> nodes){
        getChildren().addAll(nodes);
    }

    public void addNode(Node node){
        getChildren().add(node);
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
