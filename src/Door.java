import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents a door in the game which can be unlocked with
 * a key that matches the colour of the door.
 * @author Cameron mcDonald
 * @version 1.1
 */

public class Door extends Wall {

    private Image img; // The image representing the door
    private String doorColour; // The colour of the door

    /**
     * Constructs a door tile and sets the correct image for the colour of the door.
     * @param gameSession The current game session.
     * @param x the x position of the diamond.
     * @param y the y position of the diamond.
     * @param operationInterval The time in ms between operations.
     * @param doorColour The colour of the door.
     */
    public Door(GameSession gameSession, int x, int y, long operationInterval, String doorColour) {
        super(gameSession, x, y, TileType.DOOR, operationInterval);
        this.doorColour = doorColour;
        this.img = selectImageBasedOnColour(doorColour); // Set the image based on the door colour
        
    }


    /**
     * Handles unlocking the door if the player has the correct key and tries
     * to interact with this tile.
     * @param tile The tile that is interacting with this tile.
     */
    @Override
    public void interact(Tile tile) {
        if (tile.getTileType() == TileType.PLAYER) {
            // Check if the player has the correct key in their inventory
            if (gameSession.getCurrentSessionData().tryConsumeKey(getDoorColour())) {
                unlockDoor(tile); // Unlock the door
            }
        }
    }


    /**
     * Unlocks the door and moves the player into the current tile.
     * @param tile The player that is interacting with the door.
     */
    private void unlockDoor(Tile tile) {
        PathWall pathWall = new PathWall(
                gameSession,
                tile.getXPosition(),
                tile.getYPosition(),
                getOperationInterval()
        );

        gameSession.updateTilePositions(pathWall, tile, this);
    }

    @Override
    public void updateTile(long currentTimeInMilliseconds) {
    }

    /**
     * Select the correct image for the door based on its colour.
     * @param colour The colour of the door.
     * @return The door image to be used.
     */
    private Image selectImageBasedOnColour(String colour) {
        switch (colour) {
            case "RD": // Red door
                return new Image("file:Assets/Images/RedDoor.png");
            case "BD": // Blue door
                return new Image("file:Assets/Images/BlueDoor.png");
            case "YD": // Yellow door
                return new Image("file:Assets/Images/YellowDoor.png");
            case "GD": // Green door
                return new Image("file:Assets/Images/GreenDoor.png");
        }

        // If no case matches, throw an exception
        throw new IllegalArgumentException("Unsupported door colour: " + colour);
    }

    // Getter for door colour
    public String getDoorColour() {return this.doorColour;}

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0); // Draw the door image
    }

    public String returnStringTileRepresentation() {
        return this.doorColour;
    }


}