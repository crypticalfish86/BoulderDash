import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents an exit wall in the game, which allows the player
 * to complete the current level and progress to the next level if they have
 * collected the required number of diamonds.
 * @author Cameron mcDonald
 * @version 1.1
 */

public class ExitWall extends Wall {

    private static final Image activeImg = new Image("file:Assets/Images/NetherPortal.png"); // Active portal image
    private static final Image inactiveImg = new Image("file:Assets/Images/UnactivatedPortal.png"); // Inactive portal image

    private boolean isActive; // Indicates whether the exit is active

    /**
     * Constructs an exit wall tile and initially sets it to be inactive.
     * @param gameSession The current game session.
     * @param x the x position of the exit wall.
     * @param y the y position of the exit wall.
     * @param operationInterval The time in ms between operations.
     */
    public ExitWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.EXIT_WALL, operationInterval);
        this.isActive = false; // Exit is initially inactive
    }


    /**
     * Handles moving to the next level if the player tries to interact with
     * this tile and the exit wall is active.
     * @param tile The tile that is interacting with this tile.
     */
    @Override
    public void interact(Tile tile) {
        // Ensure the wall is active before allowing the player to exit
        if (this.isActive && tile.getTileType() == TileType.PLAYER) {
            changeLevel(); // Trigger level change
        }
    }

    /**
     * Checks if the exit wall should be activated.
     * @param currentTimeInMilliseconds
     * The current time in milliseconds since the unix epoch (01/01/1970).
     */
    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        activateIfDiamondsCollected();
    }


    /**
     * Activates the exit wall if the player has collected the required
     * number of diamonds.
     */
    public void activateIfDiamondsCollected() {
        if (!isActive) {
            GameSessionData sessionData = gameSession.getCurrentSessionData();
            if (sessionData.getDiamondCount() >= sessionData.getDiamondsRequired()) {
                this.isActive = true;
            }
        }
    }


    /**
     * Change the current level
     */
    public void changeLevel() {
        gameSession.onGameOver(true);
    }


    public boolean isActive() {return this.isActive;}


    // Draw the exit wall
    @Override
    public void drawTile(GraphicsContext gc) {
        // Select the image based on the active state
        Image currentImg = isActive ? activeImg : inactiveImg;
        draw(gc, currentImg, 0, 0); // Draw the selected image
    }

    public String returnStringTileRepresentation() {
        return "E";
    }
}
