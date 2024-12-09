import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;


/**
 * This Class is used to create a layer to be used with {@link CanvasCompositor}, to allow any layers
 * have the same previlege of rendering and listening inputs
 * 
 * The interface {@link CanvasCompositor} is required to define the custom behaviour of this layer
 * so that this layer can respond to drawing and key inputs, as well as mouse inputs
 * @author Jace Weerawardena
 * @author Armaan Ghadiali
 * @author Omar Zarugh
 * @author Alex (Tsz Tung Yee)
 * @version 1.5
 */
public class CanvasLayer {

    
    
    public CanvasLayerI cI;
    public int ZIndex;


    /**
     * Creates a Canvas Layer, which is used for rendering and receiving inputs for a ui layer
     * @param cI the interface, containing the essential events to be called. Use this to customise this layer's behaviour
     * @param ZIndex used for layering. higher z-index appears on top, and also receives inputs on higher priority
     */
    public CanvasLayer(CanvasLayerI cI, int ZIndex) {
        this.cI = cI;
        this.ZIndex = ZIndex;
    }


    /**
     * Interface used for determining the behaviour of a graphics layer.
     */
    public interface CanvasLayerI {
        /**
         * function being called when the mouse is down on this specific layer. mouseUp is promised to be fired
         * @param x x-position of the mouse
         * @param y y-position of the mouse
         * @param hasConsumed if this mouseDown is obstructed
         * @return has the click been consumed on this layer, or if the input has been blocked
         */
        public boolean onMouseDown(double x, double y, boolean hasConsumed);



        /**
         * function being called when it has previously returned true on onMouseDown, when mouse has been released
         * @param x x-position of the mouse
         * @param y y-position of the mouse
         * @param hasConsumed if this mouseDown is obstructed
         * @return has the click been consumed on this layer, or if the input has been blocked
         */
        public boolean onMouseUp(double x, double y, boolean hasConsumed);


        
        /**
         * function that returns true or false depending on is this input consumed
         * @param x x-position of the mouse
         * @param y y-position of the mouse
         * @param hasConsumed if this mouse has been obstructed
         * @return has the click been consumed on this layer, or if the input has been blocked
         */
        public boolean onMouseMove(double x, double y, boolean hasConsumed);



        /**
         * fires every time the layer should be rerendered
         * @param gc graphics context to draw on
         * @param elapsed time in milliseconds passed. passed for animation purposes
         */
        public void draw(GraphicsContext gc, long elapsed);


        
        /**
         * fires when a key is down
         * redirected from scene inputs to simplify
         * @param key input key
         */
        public void onKeyDown(KeyCode key);



        /**
         * fires when a key is up
         * redirected from scene inputs to simplify
         * @param key input key
         */
        public void onKeyUp(KeyCode key);
    }
}

