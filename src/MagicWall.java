import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MagicWall extends Wall {


    public static final Image img = new Image("file:Assets/Images/MagicWall.png");//TODO: add the image here

    public MagicWall(GameSession gameSession, int x, int y, TileType TileType, long operationInterval) {
        super(gameSession, x, y, TileType, operationInterval);
        this.amoebaCanSpreadToThisTile = false;
    }

    public void interact(Tile inputTileObject) {
        System.out.println("Magic wall activated: Interaction started!");
        // Add behavior for converting tiles or creating special effects
    }

    public void updateTile(long currentTimeInMilliseconds) {
        // draw(img, 0, 0);
    }
}
