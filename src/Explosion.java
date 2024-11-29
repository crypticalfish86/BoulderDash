import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Explosion extends Tile {

    private Boolean replaceWithDiamond;
    
    public static final Image img = new Image("file:Assets/Images/Explosion.png"); // Placeholder for the image




    public Explosion(GameSession gameSession, int x, int y, long operationInterval, Boolean replaceWithDiamond) {
        super(gameSession, x, y, TileType.EXPLOSION, operationInterval);
        this.replaceWithDiamond = replaceWithDiamond;

    }
    public void interact(Tile Tile) {

    }
    public void updateTile(long currentTimeInMilliseconds) {
        //TODO convert explosion to path/diamond after certain length of time
    }



    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }
}
