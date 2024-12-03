import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Door extends Wall {

    public static final Image img = new Image("file:Assets/Images/RedDoor.png"); // Placeholder for the image
    private char doorColour; // The color of the door

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
            System.out.println("Player is interacting with a door of color: " + getDoorColour());

            // Check if the player has the correct key in their inventory
            if (gameSession.getCurrentSessionData().tryConsumeKey(getDoorColour())) {
                System.out.println("Door unlocked with the correct key: " + getDoorColour());
                unlockDoor(); // Unlock the door
            } else {
                System.out.println("Door cannot be opened without the correct key: " + getDoorColour());
            }
        } else {
            System.out.println("Only the player can interact with the door.");
        }
    }

    // Logic to unlock or remove the door
    private void unlockDoor() {
        // Replace the door on the grid with a PathWall (or another passable tile)
        PathWall pathWall = new PathWall(gameSession, getXPosition(), getYPosition(), getOperationInterval());
        gameSession.setTile(getYPosition(), getXPosition(), pathWall);

        System.out.println("Door at (" + getXPosition() + ", " + getYPosition() + ") has been unlocked and replaced with a PathWall.");
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
