import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents a normal wall in the game.
 * This wall is static and cannot be interacted with or changed.
 * Author: Isaac Atkinson (IsaacA27)
 * Version: 1.0
 */
public class NormalWall extends Wall {

    /**
     * The image for the normal wall.
     */
    private static final Image img = new Image("file:Assets/Images/NormalWallVers3.png");

    /**
     * Creates a normal wall tile.
     *
     * @param gameSession       the game session this wall go in.
     * @param x                 the x-coordinate of the wall.
     * @param y                 the y-coordinate of the wall.
     * @param operationInterval the update interval for the wall.
     */
    public NormalWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.NORMAL_WALL, operationInterval);
    }

    /**
     * Handles interactions with the wall.
     * Normal walls do nothing when interacted with.
     *
     * @param tile the tile interacting with the wall.
     */
    @Override
    public void interact(Tile tile) {
        // No action.
    }

    /**
     * @param currentTimeInMilliseconds the current time.
     */
    public void updateTile(long currentTimeInMilliseconds) {
        // No updates needed.
    }

    /**
     * Draws the wall on the screen.
     * @param gc the graphics context to draw on.
     */
    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }

    /**
     * Returns a representation of the wall.
     *
     * @return "W".
     */
    public String returnStringTileRepresentation() {
        return "W";
    }
}
