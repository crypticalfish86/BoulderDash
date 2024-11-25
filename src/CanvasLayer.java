import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class CanvasLayer {

    
    public CanvasLayerI cI;
    public int ZIndex;


    public CanvasLayer(CanvasLayerI cI, int ZIndex) {
        this.cI = cI;
        this.ZIndex = ZIndex;
    }



    
    public interface CanvasLayerI {



        /**
         * function being called when the mouse is down on this specific layer. mouseUp is promised to be fired
         * @param x x-position of the mouseDown
         * @param y y-position of the mouseDown
         * @param hasConsumed if this mouseDown is obstructed
         * @return has the click been consumed
         */
        public boolean onMouseDown(double x, double y, boolean hasConsumed);



        /**
         * function being called when it has previously returned true on onMouseDown, when mouse has been released
         * @param x x-position of the mouseUp
         * @param y y-position of the mouseUp
         * @param hasConsumed if this mouseDown is obstructed
         */
        public boolean onMouseUp(double x, double y, boolean hasConsumed);


        
        /**
         * function that returns true or false depending on is this input consumed
         * @param x x-position of the mouse
         * @param y y-position of the mouse
         * @param hasConsumed if this mouse has been obstructed
         * @return
         */
        public boolean onMouseMove(double x, double y, boolean hasConsumed);



        /**
         * fires every time the layer should be rerendered
         * @param gc graphics context to draw on
         * @param elapsed time in milliseconds passed
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

