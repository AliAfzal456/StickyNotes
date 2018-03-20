import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage){
        BorderPane root = new BorderPane(); // borderpane, only need top and center
        TitleBar bar = new TitleBar(" #336699;");    // title-bar

        root.setTop(bar);

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
