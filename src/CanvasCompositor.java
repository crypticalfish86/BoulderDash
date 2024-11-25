import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class CanvasCompositor {
    


    private Canvas canvas;
    private GraphicsContext gc;
    private ArrayList<CanvasLayer> canvasLayerArray = new ArrayList<>();

    private CanvasLayer lastClickOn;

    public CanvasCompositor(Pane pane, Scene scene) {
        this.canvas = new Canvas(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);



        this.gc = canvas.getGraphicsContext2D();



        scene.setOnMousePressed(E -> {
            //checks from the top to the bottom to see which layer it interacts with
            for (int i = canvasLayerArray.size() - 1; i >= 0; --i) {
                CanvasLayer cl = canvasLayerArray.get(i);

                boolean isConsumed = cl.cI.onMouseDown(E.getSceneX(), E.getSceneY());

                if (isConsumed) {
                    lastClickOn = cl;
                    break;
                }
            }

        });

        scene.setOnMouseReleased(E -> {
            if (lastClickOn != null) {
                lastClickOn.cI.onMouseUp(E.getSceneX(), E.getSceneY());
                lastClickOn = null;
            }
        });
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
    }
}
