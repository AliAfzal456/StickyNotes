import javafx.scene.control.Button;

import java.io.File;

/**
 * TitleButton
 *
 * custom class for title button, mostly because it's going to have modified styling
 * Could've been done through CSS, but i might add extra functionality later, and i'll need it then
 */
public class TitleButton extends Button {

    public TitleButton(int width, int height){
        setFocusTraversable(false);
        setPrefSize(width, height);
    }

    public void setBgText(String text){
        setText(text);
    }

}
