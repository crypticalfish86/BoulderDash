
import javafx.util.Duration;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;





public class CanvasTest extends Application {

    @Override
    public void start(Stage primaryStage) {

        Pane pane = new Pane();
        Scene scene = new Scene(pane, 500, 500);

        CanvasCompositor cc = new CanvasCompositor(pane, scene);





        //I'm sorry java does not have pointers, so I have to do this
        long[] lastUpdated = {System.currentTimeMillis()};

        
        Timeline updateLoop = new Timeline(new KeyFrame(Duration.seconds((double) 1 /60), E -> {
            long now = System.currentTimeMillis();
            long timeDiff = now - lastUpdated[0];
            lastUpdated[0] = now;
            cc.draw(timeDiff);
        }));
        
        updateLoop.play();

        primaryStage.show();


        //creates a test layer
        CanvasLayer cl = new CanvasLayer(new CanvasLayer.CanvasLayerI() {
            @Override
            public boolean onMouseDown(double x, double y) {
                return false;
            }

            @Override
            public void onMouseUp(double x, double y) {
                
            }

            @Override
            public void draw(GraphicsContext gc, long elapsed) {

            }
            
        }, 0);
        
        cc.addLayer(cl);
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}