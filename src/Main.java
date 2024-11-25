import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static final int GRID_HEIGHT = 1280;
    public static final int GRID_WIDTH = 960;

    @Override
    public void start(Stage stage) {
        // String javaVersion = System.getProperty("java.version");
        // String javafxVersion = System.getProperty("javafx.version");


        Pane root = new Pane();

        Scene scene = new Scene(root, GRID_HEIGHT, GRID_WIDTH);
        Game game = new Game(scene, root);

        

        stage.setScene(scene);
        stage.show();
    }
    

    public static void main(String[] args) {
        launch();
    }

}