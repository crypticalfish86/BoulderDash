import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DirtWall extends Wall {

    public static final Image img = new Image("file:Assets/Images/Dirt.png"); // Placeholder for the dirt wall image

    public DirtWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.STATIC_TILE, operationInterval);
        this.amoebaCanSpreadToThisTile = true; // Amoebas can spread to dirt walls
    }

    // Interaction logic for the dirt wall
    @Override
    public void interact(Tile inputTileObject) {
        if (inputTileObject instanceof Player) {
            System.out.println("Dirt wall dug out by the player.");

            // Remove the dirt wall from the game grid by setting the tile to null or a passable tile
            gameSession.setTile(getYPosition(), getXPosition(), null);

            // Optionally, you could replace the wall with a "Path" tile or other tile type
            // gameSession.setTile(getYPosition(), getXPosition(), new PathWall(gameSession, getXPosition(), getYPosition(), TileType.PATH, gameSession.OPERATION_INTERVAL));
        }
    }

    // Optional update logic for dirt walls
    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        // Dirt walls typically don't have dynamic behavior, but this is available for animations or other effects
    }

    // Draw the dirt wall image
    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0); // Render the dirt wall
    }
}
