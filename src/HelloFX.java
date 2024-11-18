import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HelloFX extends Application {

    @Override
    public void start(Stage stage) {
        // String javaVersion = System.getProperty("java.version");
        // String javafxVersion = System.getProperty("javafx.version");


        Pane root = new Pane();

        Scene scene = new Scene(root, 640, 480);
        Game game = new Game(scene, root);

        

        stage.setScene(scene);
        stage.show();
    }
    

    public static void main(String[] args) {
        launch();
    }

}