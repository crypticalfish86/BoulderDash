import javafx.scene.image.Image;

public class Player extends Tile {

    // Image representing the player (replace with actual image path)
    public static final Image img = new Image("file:Assets/Images/Ameoba.png"); // Placeholder for the image

    // Constructor for Player
    public Player(GameSession gameSession, int x, int y, long operationInterval) {
        // Call the parent class (Tile) constructor with the gameSession and other parameters
        super(gameSession, x, y, TileType.PLAYER, operationInterval);
    }

    // Example method to access GameSession
    public void somePlayerLogic() {
        GameSession session = getGameSession(); // This should work now, as Player inherits from Tile
        if (session != null) {
            System.out.println("Game Session is available.");
        }
    }

    // Handle player input (example)
    public void onKeyPressed(String key) {
        System.out.println("Key " + key + " pressed.");
        // You can queue input or implement additional logic for key press here
    }

    public void onKeyReleased(String key) {
        System.out.println("Key " + key + " released.");
        // Handle key release logic here
    }

    @Override
    public void interact(Tile other) {
        // Implement interaction logic with other tiles
        System.out.println("Player interacting with tile: " + other.getTileType());
    }

    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        // Update the player's tile, for example, drawing the image
        draw(img, 0, 0); // Update player's image at the specified location (customize as needed)
    }
}
