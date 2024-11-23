import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static final int WINDOW_WIDTH = 640;
    public static final int WINDOW_HEIGHT = 480;


    @Override
    public void start(Stage stage) {
        // String javaVersion = System.getProperty("java.version");
        // String javafxVersion = System.getProperty("javafx.version");


        Pane root = new Pane();

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        Game game = new Game(scene, root);

        

        stage.setScene(scene);
        stage.show();
    }
    

    public static void main(String[] args) {
        launch();
    }

}