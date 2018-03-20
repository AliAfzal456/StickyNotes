import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Notepad {

    public TitleBar myBar;
    public Stage myStage;

    public Notepad(){
        Stage stage = new Stage();

        WindowManager.allWindows.add(this);
        BorderPane root = new BorderPane(); // borderpane, only need top and center
        TitleBar bar = new TitleBar(" #336699;");    // title-bar
        root.setTop(bar);

        stage.setScene(new Scene(root, 300, 250));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}
