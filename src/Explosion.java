import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Explosion extends Tile {

    
    public static final Image img = new Image("file:Assets/Images/Explosion.png"); // Placeholder for the image




    public Explosion(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.EXPLOSION, operationInterval);
    }
    public void interact(Tile Tile) {

    }
    public void updateTile(long currentTimeInMilliseconds) {

    }



    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }
}
