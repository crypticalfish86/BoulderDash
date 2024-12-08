import java.util.ArrayList;
import java.util.Objects;


import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;


/**
 * A canvas that is used for allowing multiple classes to add their custom layer ({@link CanvasLayer}) to the drawing pipeline
 * Each layer is required to define its behaviour to certain events
 * 
 * By using this object, a timeline is required to operate its drawing loop, and the elapsed time is also required to pass into the object
 * @see javafx.animation.Timeline
 */
public class CanvasCompositor {
    
    private final Canvas canvas; // internal canvas used for drawing
    private final GraphicsContext gc; // internal graphics context derived from canvas
    private final ArrayList<CanvasLayer> canvasLayerArray = new ArrayList<>(); // array for storing the layers. ordered by ascending z.


    private int sizeX; // canvas width
    private int sizeY; // canvas height

     


    /**
     * Creates a canvas under the specified pane, and redirects the input events from the given screen to all layers under this object
     * @param pane Any pane that this canvas will create on
     * @param scene A scene to read inputs from
     * @param sizeX width of the canvas in pixels
     * @param sizeY height of the canvas in pixels
     */
    public CanvasCompositor(Pane pane, Scene scene, int sizeX, int sizeY) {
        Objects.requireNonNull(pane);
        Objects.requireNonNull(scene);

        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.canvas = new Canvas(sizeX, sizeY);
        this.gc = canvas.getGraphicsContext2D();

        

        //redirects the mouse press event. only process primary mouse clicks due to backwards compability
        scene.setOnMousePressed(E -> {
            if (E.getButton() != MouseButton.PRIMARY) { return; }
            //checks from the top to the bottom to see which layer it interacts with
            
            synchronized (canvasLayerArray) {
                boolean hasConsumed = false;
                for (int i = canvasLayerArray.size() - 1; i >= 0; --i) {
                    CanvasLayer cl = canvasLayerArray.get(i);

                    hasConsumed |= cl.cI.onMouseDown(E.getSceneX(), E.getSceneY(), hasConsumed);
                }
            }
        });


        
        //redirects the mouse release event. only process primary mouse clicks due to backwards compability
        scene.setOnMouseReleased(E -> {
            if (E.getButton() != MouseButton.PRIMARY) { return; }


            //checks from the top to the bottom to see which layer it interacts with
            synchronized (canvasLayerArray) {
                boolean hasConsumed = false;
                for (int i = canvasLayerArray.size() - 1; i >= 0; --i) {
                    CanvasLayer cl = canvasLayerArray.get(i);

                    hasConsumed |= cl.cI.onMouseUp(E.getSceneX(), E.getSceneY(), hasConsumed);
                }
            }
        });

        
        //redirects the mouse move event. only process primary mouse clicks due to backwards compability
        scene.setOnMouseMoved(E -> {

            
            
            //checks from the top to the bottom to see which layer it interacts with
            synchronized (canvasLayerArray) {
                boolean hasConsumed = false;
                for (int i = canvasLayerArray.size() - 1; i >= 0; --i) {
                    CanvasLayer cl = canvasLayerArray.get(i);

                    hasConsumed |= cl.cI.onMouseMove(E.getSceneX(), E.getSceneY(), hasConsumed);
                }
            }
        });


        //redirects key inputs
        scene.setOnKeyPressed(E -> {
            synchronized (canvasLayerArray) {
                for (int i = canvasLayerArray.size() - 1; i >= 0; --i) {

                    CanvasLayer cl = canvasLayerArray.get(i);
                    cl.cI.onKeyDown(E.getCode());
                }
            }
        });

        //redirects key inputs
        scene.setOnKeyReleased(E -> {
            synchronized (canvasLayerArray) {
                for (int i = canvasLayerArray.size() - 1; i >= 0; --i) {

                    CanvasLayer cl = canvasLayerArray.get(i);
                    cl.cI.onKeyUp(E.getCode());
                }
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
        synchronized (canvasLayerArray) {



            //inserts the layer into the ascending sorted array
            for (int i = 0; i < canvasLayerArray.size(); ++i) {
                if (canvasLayer.ZIndex < canvasLayerArray.get(i).ZIndex) {

                    canvasLayerArray.add(i, canvasLayer);

                    break;
                }
            }
            canvasLayerArray.add(canvasLayer);
            hasAdded = true;

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
        synchronized (canvasLayerArray) {
            

            hasRemoved = canvasLayerArray.remove(canvasLayer);
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
     * draws all the layers of this canvas
     * @param elapsed time elapsed between last draw
    */
    public void draw(long elapsed) {

        //tries to acquire read lock for rendering
        synchronized (canvasLayerArray) {

            //clears the window
            gc.clearRect(0, 0, sizeX, sizeY);


            //attempts to draw all the layers
            if (!canvasLayerArray.isEmpty()) {
                for (int i = 0; i < canvasLayerArray.size(); ++i) {
                    canvasLayerArray.get(i).cI.draw(gc, elapsed);
                }
            }
        }
    }


    /**
     * Resizes the internal canvas
     * @param sizeX width of the canvas in pixels
     * @param sizeY height of the canvas in pixels
     */
    public void resize(int sizeX, int sizeY) {
        this.canvas.resize(sizeX, sizeX);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    
    /**
     * Returns the width of this canvas
     * @return width of canvas in pixels
     */
    public int getSizeX() {
        return this.sizeX;
    }

    
    /**
     * Returns the width of this canvas
     * @return height of canvas in pixels
     */
    public int getSizeY() {
        return this.sizeY;
    }
}
