import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PathWall extends Wall {

    // Image representing the PathWall (replace with the actual image path)
    public static final Image img = new Image("file:Assets/Images/PathWall.png"); // Path to the PathWall image

    // Constructor
    public PathWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.PATH, operationInterval);
        this.amoebaCanSpreadToThisTile = true; // Amoeba can spread to this tile
    }

    // Handle interactions with other tiles
    @Override
    public void interact(Tile tile) {
        // if (tile.getTileType() == TileType.MOVING_ENEMY) {
        PathWall pathWall = new PathWall(
            gameSession,
            tile.getXPosition(),
            tile.getYPosition(),
            operationInterval
        );

        gameSession.updateTilePositions(pathWall, tile, this);
        // }

        // Example interaction logic
        // if (tile instanceof Player) {
        //     System.out.println("Player stepped on a PathWall.");
        //     // You can add more specific behaviors here, such as triggering effects
        // } else {
        //     System.out.println("Another tile interacted with the PathWall.");
        // }
        

    }

    // Update the tile (called periodically)
    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        
    }

    // Draw the PathWall tile
    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0); // Draw the PathWall image at its current position
    }
}
