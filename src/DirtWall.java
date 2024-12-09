import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents a dirt wall in the game which become paths when
 * the player walks over them.
 * @author Cameron McDonald
 * @author Alex (Tsz Tung Yee)
 * @version 1.1
 */
public class DirtWall extends Wall {

    public static final Image img = new Image("file:Assets/Images/Dirt.png"); // Placeholder for the dirt wall image

    /**
     * Constructs a dirt wall tile.
     * @param gameSession The current game session.
     * @param x the x position of the dirt wall.
     * @param y the y position of the dirt wall.
     * @param operationInterval The time in ms between operations.
     */
    public DirtWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.DIRT_WALL, operationInterval);
    }

    /**
     * Handles moving the player to this tile if the player tries to interact
     * with this tile.
     * @param tile The tile that is interacting with this tile.
     */
    @Override
    public void interact(Tile tile) {

        if (tile.tileType == TileType.PLAYER) {
            PathWall pathWall = new PathWall(
                gameSession,
                tile.getXPosition(),
                tile.getYPosition(),
                operationInterval
            );
            
            gameSession.updateTilePositions(pathWall, tile, this);
        }
    }


    @Override
    public void updateTile(long currentTimeInMilliseconds) {

    }

    // Draw the dirt wall image
    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0); // Render the dirt wall
    }

    public String returnStringTileRepresentation(){
        return "D";
    }
}
