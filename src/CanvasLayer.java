import javafx.scene.canvas.GraphicsContext;

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
         * @return has the click been consumed
         */
        public boolean onMouseDown(double x, double y);

        /**
         * function being called when it has previously returned true on onMouseDown, when mouse has been released
         * @param x x-position of the mouseUp
         * @param y y-position of the mouseUp
         */
        public void onMouseUp(double x, double y);

        /**
         * fires every time the layer should be rerendered
         * @param gc graphics context to draw on
         * @param elapsed time in milliseconds passed
         */
        public void draw(GraphicsContext gc, long elapsed);
    }
}

