import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainMenu {
    



    CanvasCompositor cc;
    CanvasLayer cl;


    Game game;


    public MainMenu(Game game, CanvasCompositor cc) {
        this.cc = cc;
        this.game = game;











        this.cl = new CanvasLayer(new CanvasLayer.CanvasLayerI() {
            @Override
            public boolean onMouseDown(double x, double y, boolean hasConsumed) {
                return false;
            }

            @Override
            public boolean onMouseUp(double x, double y, boolean hasConsumed) {
                return false;
            }

            @Override
            public boolean onMouseMove(double x, double y, boolean hasConsumed) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onKeyDown(KeyCode key) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void onKeyUp(KeyCode key) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void draw(GraphicsContext gc, long elapsed) {
                gc.setFill(new Color(.05, .05, .05, 1));
                gc.fillRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
            }
        }, 1);




        cc.addLayer(cl);

        // Create function to check what button you are on
        // This will use the three functions above (mouseUp, mouseDown, mouseMove)

    }


    public void hide() {
        cc.removeLayer(cl);
    }

    public void show() {
        cc.addLayer(cl);
    }
}
