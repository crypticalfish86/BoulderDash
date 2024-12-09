import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents the player in the game, inheriting from the Tile class.
 * The player can move, interact with tiles, and respond to input.
 * @author Alex (Tsz Tung Yee)
 * @author Cameron McDonald (cmcoff)
 *  * @version 2.1
 */
public class Player extends Tile {

    /**
     * The image used to represent the player.
     */
    public static final Image img = new Image("file:Assets/Images/PlayerForward.png");

    // Directional keys tracking
    private boolean keyUp = false;
    private boolean keyDown = false;
    private boolean keyLeft = false;
    private boolean keyRight = false;

    // Current and last direction of movement
    private char currentDirection = ' ';
    private char lastDirection = ' ';

    // Frame count for movement speed adjustment
    private int recurringFrameCount = 0;

    // Constants for movement speed
    public static final int RECURRING_DIRECTION_SPEED_UP = 4;
    public static final int RECURRING_DIRECTION_INITIAL_SPEED = 20;
    public static final int RECURRING_DIRECTION_FAST_SPEED = 7;

    /**
     * Constructs a Player object with the position and operation interval listed.
     *
     * @param gameSession       The game session to which the player is in.
     * @param x                 The initial x-coordinate of the player.
     * @param y                 The initial y-coordinate of the player.
     * @param operationInterval The interval between tile updates.
     */
    public Player(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.PLAYER, operationInterval);
    }

    /**
     * Handles input and player movement
     *
     * @param key The key pressed by the player ('W', 'A', 'S', 'D').
     */
    public void onKeyPressed(char key) {
        switch (key) {
            case 'W': keyUp = true; currentDirection = 'W'; break;
            case 'S': keyDown = true; currentDirection = 'S'; break;
            case 'A': keyLeft = true; currentDirection = 'A'; break;
            case 'D': keyRight = true; currentDirection = 'D'; break;
            default: System.out.printf("Warning: Unsupported key pressed: %c\n", key);
        }
    }

    /**
     * adjusts players direction based on key release
     *
     * @param key The key released by the player.
     */
    public void onKeyReleased(char key) {
        switch (key) {
            case 'W': keyUp = false; break;
            case 'S': keyDown = false; break;
            case 'A': keyLeft = false; break;
            case 'D': keyRight = false; break;
            default: System.out.printf("Warning: Unsupported key released: %c\n", key);
        }

        // Determine the new direction
        if (keyUp) currentDirection = 'W';
        else if (keyDown) currentDirection = 'S';
        else if (keyLeft) currentDirection = 'A';
        else if (keyRight) currentDirection = 'D';
        else currentDirection = ' ';
    }

    @Override
    public void interact(Tile tile) {
        if (tile.getTileType() == TileType.BOULDER || tile.getTileType() == TileType.DIAMOND) {
            gameSession.updateTilePositions(new PathWall(gameSession, this.x, this.y + 1, operationInterval), tile, this);
            killPlayer();
        } else if (tile.getTileType() == TileType.MOVING_ENEMY) {
            gameSession.updateTilePositions(new PathWall(gameSession, this.x, this.y + 1, operationInterval), tile, this);
            killPlayer();
        }
    }

    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        if (currentDirection != lastDirection) recurringFrameCount = 0;
        else recurringFrameCount++;

        lastDirection = currentDirection;

        boolean shouldMove = recurringFrameCount < RECURRING_DIRECTION_INITIAL_SPEED * RECURRING_DIRECTION_SPEED_UP
                ? recurringFrameCount % RECURRING_DIRECTION_INITIAL_SPEED == 0
                : (recurringFrameCount - RECURRING_DIRECTION_INITIAL_SPEED * RECURRING_DIRECTION_SPEED_UP) % RECURRING_DIRECTION_FAST_SPEED == 0;

        if (!shouldMove) return;

        // Interact with the tile in the current direction
        switch (currentDirection) {
            case 'W': gameSession.getTileFromGrid(this.getXPosition(), this.getYPosition() - 1).interact(this); break;
            case 'S': gameSession.getTileFromGrid(this.getXPosition(), this.getYPosition() + 1).interact(this); break;
            case 'A': gameSession.getTileFromGrid(this.getXPosition() - 1, this.getYPosition()).interact(this); break;
            case 'D': gameSession.getTileFromGrid(this.getXPosition() + 1, this.getYPosition()).interact(this); break;
        }

        // Kill player if an enemy is nearby
        if (isNextToEnemy()) killPlayer();
    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }

    /**
     * Ends the game when the player is killed.
     */
    public void killPlayer() {
        gameSession.onGameOver(false);
    }

    /**
     * Checks if there is an enemy next to the player.
     *
     * @return true if an enemy is nearby; false otherwise.
     */
    private boolean isNextToEnemy() {
        return gameSession.getTileFromGrid(this.x - 1, this.y).getTileType() == TileType.MOVING_ENEMY ||
                gameSession.getTileFromGrid(this.x + 1, this.y).getTileType() == TileType.MOVING_ENEMY ||
                gameSession.getTileFromGrid(this.x, this.y - 1).getTileType() == TileType.MOVING_ENEMY ||
                gameSession.getTileFromGrid(this.x, this.y + 1).getTileType() == TileType.MOVING_ENEMY;
    }

    /**
     * Returns the player's string representation for save states.
     *
     * @return "P" as the player's string representation.
     */
    public String returnStringTileRepresentation() {
        return "P";
    }
}
