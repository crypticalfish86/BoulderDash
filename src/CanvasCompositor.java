import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.sun.javafx.font.directwrite.RECT;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;


public class CanvasCompositor {
    
    private Canvas canvas;
    private GraphicsContext gc;
    private ArrayList<CanvasLayer> canvasLayerArray = new ArrayList<>();
    private final ReentrantReadWriteLock modifyLock = new ReentrantReadWriteLock();


    public CanvasCompositor(Pane pane, Scene scene) {

        
        this.canvas = new Canvas(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        this.gc = canvas.getGraphicsContext2D();


        //redirects the mouse press event. only process primary mouse clicks due to backwards compability
        scene.setOnMousePressed(E -> {
            if (E.getButton() != MouseButton.PRIMARY) { return; }
            //checks from the top to the bottom to see which layer it interacts with
            boolean hasConsumed = false;
            for (int i = canvasLayerArray.size() - 1; i >= 0; --i) {
                CanvasLayer cl = canvasLayerArray.get(i);

                hasConsumed |= cl.cI.onMouseDown(E.getSceneX(), E.getSceneY(), hasConsumed);
            }
        });


        
        //redirects the mouse release event. only process primary mouse clicks due to backwards compability
        scene.setOnMouseReleased(E -> {
            if (E.getButton() != MouseButton.PRIMARY) { return; }
            //checks from the top to the bottom to see which layer it interacts with
            boolean hasConsumed = false;
            for (int i = canvasLayerArray.size() - 1; i >= 0; --i) {
                CanvasLayer cl = canvasLayerArray.get(i);

                hasConsumed |= cl.cI.onMouseUp(E.getSceneX(), E.getSceneY(), hasConsumed);
            }
        });

        
        //redirects the mouse move event. only process primary mouse clicks due to backwards compability
        scene.setOnMouseMoved(E -> {
            //checks from the top to the bottom to see which layer it interacts with
            boolean hasConsumed = false;
            for (int i = canvasLayerArray.size() - 1; i >= 0; --i) {
                CanvasLayer cl = canvasLayerArray.get(i);

                hasConsumed |= cl.cI.onMouseMove(E.getSceneX(), E.getSceneY(), hasConsumed);
            }
        });


        //redirects key inputs
        scene.setOnKeyPressed(E -> {
            if (canvasLayerArray.isEmpty()) { return; }
            for (CanvasLayer cl : canvasLayerArray) {
                cl.cI.onKeyDown(E.getCode());
            }
        });

        //redirects key inputs
        scene.setOnKeyReleased(E -> {
            if (canvasLayerArray.isEmpty()) { return; }
            for (CanvasLayer cl : canvasLayerArray) {
                cl.cI.onKeyUp(E.getCode());
            }
        });


        //adds the current drawing layer to the scene's pane
        pane.getChildren().addAll(canvas);
    }



    /**
     * Adds the specified render layer for the canvas.
     * This layer can be removed by calling the remove function
     * @param canvasLayer the layer to be added
    */
    public boolean addLayer(CanvasLayer canvasLayer) {

        boolean hasAdded;
        //tries to acquire the read lock to modify the layer array
        try {
            modifyLock.writeLock().lock();

            //inserts the layer into the descending sorted array
            for (int i = 0; i < canvasLayerArray.size(); ++i) {
                if (canvasLayer.ZIndex < canvasLayerArray.get(i).ZIndex) {

                    canvasLayerArray.add(i, canvasLayer);

                    break;
                }
            }
            canvasLayerArray.add(canvasLayer);
            hasAdded = true;

        } finally {
            modifyLock.writeLock().unlock();
        }
        return hasAdded;
    }



    /**
     * Removes a specified layer from the compositor
     * @param canvasLayer layer to be removed
     * @return if the remove action was successful (if this item was previously existing)
     */
    public boolean removeLayer(CanvasLayer canvasLayer) {


        boolean hasRemoved; // indicates if this process has been successful

        //tries to acquire write lock to modify the layer array
        try {
            modifyLock.writeLock().lock();

            hasRemoved = canvasLayerArray.remove(canvasLayer);
        } finally {
            modifyLock.writeLock().unlock();
        }
        return hasRemoved;
    }



    /**
     * Removes all context layers from this canvas
    */
    public void removeAllLayers() {
        canvasLayerArray.clear();
    }



    /**
     * signals the drawing of this canvas
     * @param elapsed time elapsed between last draw
    */
    public void draw(long elapsed) {

        //tries to acquire read lock for rendering
        try {
            modifyLock.readLock().lock();

            //clears the window
            gc.clearRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);


            //attempts to draw all the layers
            if (!canvasLayerArray.isEmpty()) {
                for (CanvasLayer cl : canvasLayerArray) {
                    cl.cI.draw(gc, elapsed);
                }
            }


        } finally {
            modifyLock.readLock().unlock();
        }
    }
}
