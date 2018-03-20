import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage){
        WindowManager manager = new WindowManager();
        manager.spawnWindow();
    }
    public static void main(String[] args){
        launch(args);
    }
}
