import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class ProfileSelector {
    CanvasCompositor cc;
    CanvasLayer cl;

    Game game;

    public ProfileSelector(Game game, CanvasCompositor cc, CanvasLayer cl) {
        this.game = game;
        this.cc = cc;
        this.cl = cl;

        boolean[] mouseDownOnBackButton = {false};
        boolean[] mouseDownOnProfileSelector1 = {false};
        boolean[] mouseDownOnProfileSelector2 = {false};
        boolean[] mouseDownOnProfileSelector3 = {false};



    }
}
