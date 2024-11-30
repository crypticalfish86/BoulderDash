import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DirtWall extends Wall {
    
    public static final Image img = new Image("file:Assets/Images/Dirt.png"); // Placeholder for the image

    public DirtWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.STATIC_TILE, operationInterval);
        this.amoebaCanSpreadToThisTile = true;
    }

    public void interact(Tile inputTileObject) {
        if (inputTileObject instanceof Player) {
            System.out.println("Dirt wall dug out by the player.");
            // Remove the dirt wall from the game grid
        }
    }

    public void updateTile(long currentTimeInMilliseconds) {
        // System.out.println("DirtWall update logic here."); // Optional update behavior
    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }
}
