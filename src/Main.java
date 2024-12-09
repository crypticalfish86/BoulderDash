import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Entry point to the application.
 * @author Alex (Tsz Tung Yee)
 * @author Omar Zarugh
 * @author Armaan Ghadiali
 * @author Jace Weerawardena
 * @author Isaac Atkinson
 * @author Anthony Davies
 * @author Cameron McDonald
 * @version 1.1
 */

public class Main extends Application {

    public static final int WINDOW_HEIGHT = 960;
    public static final int WINDOW_WIDTH = 1280;

    @Override
    public void start(Stage stage) {
        // String javaVersion = System.getProperty("java.version");
        // String javafxVersion = System.getProperty("javafx.version");


        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        stage.setTitle("OMG IT'S Boulder Dash!!!!!!!!");

        CanvasCompositor cc = new CanvasCompositor(root, scene, WINDOW_WIDTH, WINDOW_HEIGHT);



        Game game = new Game(cc);

        


        //I'm sorry java does not have pointers, so I have to do this
        long[] lastUpdated = {System.currentTimeMillis()};

        //javafx library, loops
        Timeline updateLoop = new Timeline(new KeyFrame(Duration.seconds((double) 1 /60), E -> {

            long now = System.currentTimeMillis();
            long timeDiff = now - lastUpdated[0];
            if (timeDiff < 1000 / 75) { return; }


            lastUpdated[0] = now;
            cc.draw(timeDiff);
        }));
        updateLoop.setCycleCount(Animation.INDEFINITE);
        updateLoop.play();


        stage.setScene(scene);
        stage.show();


    }
    

    public static void main(String[] args) {
        launch();
    }

    
}