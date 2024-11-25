import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class CanvasCompositor {
    
    private Scene scene;

    private Canvas canvas;
    private GraphicsContext gc;
    private ArrayList<CanvasLayer> canvasLayerArray = new ArrayList<>();



    public CanvasCompositor(Pane pane, Scene scene) {

        this.scene = scene;
        this.canvas = new Canvas(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);



        this.gc = canvas.getGraphicsContext2D();



        scene.setOnMousePressed(E -> {
            //checks from the top to the bottom to see which layer it interacts with
            boolean hasConsumed = false;
            for (int i = canvasLayerArray.size() - 1; i >= 0; --i) {
                CanvasLayer cl = canvasLayerArray.get(i);

                hasConsumed |= cl.cI.onMouseDown(E.getSceneX(), E.getSceneY(), hasConsumed);
            }
        });

        scene.setOnMouseReleased(E -> {
            //checks from the top to the bottom to see which layer it interacts with
            boolean hasConsumed = false;
            for (int i = canvasLayerArray.size() - 1; i >= 0; --i) {
                CanvasLayer cl = canvasLayerArray.get(i);

                hasConsumed |= cl.cI.onMouseUp(E.getSceneX(), E.getSceneY(), hasConsumed);
            }
        });


        scene.setOnKeyPressed(E -> {
            for (CanvasLayer cl : canvasLayerArray) {
                cl.cI.onKeyDown(E.getCode());
            }
        });

        scene.setOnKeyReleased(E -> {
            for (CanvasLayer cl : canvasLayerArray) {
                cl.cI.onKeyUp(E.getCode());
            }
        });



        pane.getChildren().addAll(canvas);
    }


    /**
     * Adds the specified render layer for the canvas.
     * This layer can be removed by calling the remove function
    */
    public void addLayer(CanvasLayer canvasLayer) {
        for (int i = 0; i < canvasLayerArray.size(); ++i) {
            if (canvasLayer.ZIndex < canvasLayerArray.get(i).ZIndex) {
                
                canvasLayerArray.add(i, canvasLayer);

                return;
            }
        }

        canvasLayerArray.add(canvasLayer);
    }

    /**
     * Removes a specified layer from the compositor
     * @param canvasLayer layer to be removed
     * @return if the remove action was successful (if this item was previously existing)
     */
    public boolean removeLayer(CanvasLayer canvasLayer) {
        return canvasLayerArray.remove(canvasLayer);
    }

    /**
     * Removes all context layers from this canvas
    */
    public void removeAllLayers() {
        canvasLayerArray.clear();
    }

    /**
     * 
    */
    public void draw(long elapsed) {
        gc.clearRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

        for (CanvasLayer cl : canvasLayerArray) {
            cl.cI.draw(gc, elapsed);
        }



        scene.setOnMouseReleased(E -> {
            //checks from the top to the bottom to see which layer it interacts with
            boolean hasConsumed = false;
            for (int i = canvasLayerArray.size() - 1; i >= 0; --i) {
                CanvasLayer cl = canvasLayerArray.get(i);

                hasConsumed |= cl.cI.onMouseMove(E.getSceneX(), E.getSceneY(), hasConsumed);
            }
        });

    }
}
