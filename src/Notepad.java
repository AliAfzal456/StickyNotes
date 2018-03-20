import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Notepad {

    public TitleBar myBar;
    public Stage myStage;

    public Notepad(){
        Stage stage = new Stage();

        WindowManager.allWindows.add(this);
        BorderPane root = new BorderPane(); // borderpane, only need top and center
        TitleBar bar = new TitleBar(" #336699;");    // title-bar
        myBar = bar;
        myStage = stage;
        root.setTop(bar);

        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("closing");
                WindowManager.destroyWindow(Notepad.this);
            }
        });
        stage.setScene(new Scene(root, 300, 250));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}
