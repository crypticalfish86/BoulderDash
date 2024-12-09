import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents a path in the game.
 * @author Alex (Tsz Tung Yee)
 * @version 1.1
 */
public class PathWall extends Wall {

    // Image representing the PathWall (replace with the actual image path)
    private static final Image img = new Image("file:Assets/Images/BlackPath.png"); // Path to the PathWall image

    /**
     * Constructs a path tile.
     * @param gameSession The current game session.
     * @param x the x position of the diamond.
     * @param y the y position of the diamond.
     * @param operationInterval The time in ms between operations.
     */
    public PathWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.PATH, operationInterval);
    }

    /**
     * Handles moving other tiles in the game to this tile if they try to
     * interact with this tile.
     * @param tile The tile that is interacting with this tile.
     */
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


    /**
     * {@inheritDoc}
     * @param currentTimeInMilliseconds
     * The current time in milliseconds since the unix epoch (01/01/1970).
     */
    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        
    }

    /**
     * {@inheritDoc}
     * @param gc
     * The graphics context you're drawing to.
     */
    // Draw the PathWall tile
    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0); // Draw the PathWall image at its current position
    }

    /**
     * {@inheritDoc}
     * @return "-"
     */
    public String returnStringTileRepresentation(){
        return "-";
    }
}
