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
        // if (inputTileObject instanceof Player) {
        //     Player player = (Player) inputTileObject;

        //     // Check if the player has the correct key
        //     if (playerHasCorrectKey(player)) { 
        //         System.out.println("Door unlocked with the correct key!");
        //         unlockDoor();
        //     } else {
        //         System.out.println("Door cannot be opened without the correct key.");
        //     }
        // }


        //TODO:
        // use if (gsd.tryConsumeKey(char doorColour)) {
        //
        //}
    }

    // Check if the player has the correct key
    private boolean playerHasCorrectKey(Player player) {
        // return gameSession.getCurrentSessionData().getInventoryItem(String.format("%c%c", doorColour, 'k'));
        return false;
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
}
