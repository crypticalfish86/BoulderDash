import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DirtWall extends Wall {

    public static final Image img = new Image("file:Assets/Images/Dirt.png"); // Placeholder for the dirt wall image

    public DirtWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.DIRT_WALL, operationInterval);
        this.amoebaCanSpreadToThisTile = true; // Amoebas can spread to dirt walls
    }

    // Interaction logic for the dirt wall
    @Override
    public void interact(Tile tile) {
        
        PathWall pathWall = new PathWall(
            gameSession,
            tile.getXPosition(),
            tile.getYPosition(),
            operationInterval
        );
        
        gameSession.updateTilePositions(pathWall, tile, this);
    }

    // Update logic for dirt walls
    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        // Dirt walls typically don't have dynamic behavior, but this can be extended
    }

    // Draw the dirt wall image
    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0); // Render the dirt wall
    }
}
