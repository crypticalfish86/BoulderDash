import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Door extends Wall {

    public static final Image img = new Image("file:Assets/Images/Door.png"); // Placeholder for the image
    private char doorColour;

    public Door(GameSession gameSession, int x, int y, long operationInterval, char doorColour) {
        super(gameSession, x, y, TileType.STATIC_TILE, operationInterval);
        this.doorColour = doorColour;
        this.amoebaCanSpreadToThisTile = false;
    }

    // Getter for door color
    public char getDoorColour() {
        return this.doorColour;
    }

    // Interact method for the door
    @Override
    public void interact(Tile inputTileObject) {
        if (inputTileObject.getTileType() == TileType.PLAYER) {
            // Use GameSessionData to check if the player has the correct key
            if (gameSession.getCurrentSessionData().tryConsumeKey(doorColour)) {
                System.out.println("Door unlocked with the correct key: " + doorColour);
                unlockDoor(); // Unlock the door
            } else {
                System.out.println("Door cannot be opened without the correct key: " + doorColour);
            }
        } else {
            System.out.println("Only the player can interact with the door.");
        }
    }

    // Logic to unlock or remove the door
    private void unlockDoor() {
        // Remove the door from the game grid
        gameSession.setTile(getYPosition(), getXPosition(), null); // Set tile to null or replace with a passable tile
    }

    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        // Optional: Implement time-based behavior if needed
    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0); // Draw the door image
    }

    public String returnStringTileRepresentation() {
        return Character.toString(this.doorColour);
    }
}
