import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents a key in the game which can be collected by the player
 * and used to unlock doors.
 * @author Cameron mcDonald
 * @version 1.1
 */

public class Key extends Wall {
    private Image img; // Image representing the key
    private String keyColour; // Key's colour or unique identifier

    /**
     * Constructs a key tile and sets the correct image for the key
     * based on its colour.
     * @param gameSession The current game session.
     * @param x the x position of the diamond.
     * @param y the y position of the diamond.
     * @param operationInterval The time in ms between operations.
     * @param keyColour The colour of the key.
     */
    public Key(GameSession gameSession, int x, int y, long operationInterval, String keyColour) {
        super(gameSession, x, y, TileType.KEY, operationInterval);
        this.keyColour = keyColour;
        this.img = selectImageBasedOnColour(keyColour); // Set the image based on the key colour

    }


    /**
     * Handles the player picking up the key and moving the player to this tile.
     * Also updates the number of keys the player has in their inventory.
     * @param tile The tile that is interacting with this tile.
     */
    @Override
    public void interact(Tile tile) {
        if (tile.getTileType() == TileType.PLAYER) {
            // Add the key to GameSessionData
            gameSession.getCurrentSessionData().giveKey(returnStringTileRepresentation());

            PathWall pathWall = new PathWall(gameSession, tile.getXPosition(), tile.getYPosition(), getOperationInterval());
            gameSession.updateTilePositions(pathWall, tile, this);
        }
    }

    @Override
    public void updateTile(long currentTimeInMilliseconds) {
    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0); // Draw the key image
    }

    @Override
    public String returnStringTileRepresentation() {
        return this.keyColour;
    }

    /**
     * Select the correct image for the key based on its colour.
     * @param colour The colour of the key.
     * @return The key image to be drawn.
     */
    private Image selectImageBasedOnColour(String colour) {
        switch (colour) {
            case "RK": // Red key
                return new Image("file:Assets/Images/RedKey.png");
            case "BK": // Blue key
                return new Image("file:Assets/Images/BlueKey.png");
            case "YK": // Yellow key
                return new Image("file:Assets/Images/YellowKey.png");
            case "GK": // Green key
                return new Image("file:Assets/Images/GreenKey.png");
        }

        // If no case matches, throw an exception
        throw new IllegalArgumentException("Unsupported key color: " + colour);
    }
}
