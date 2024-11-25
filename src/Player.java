
import java.net.URL;

import javafx.scene.image.Image;

public class Player extends Tile {
    
    String inputPending; // Holds the queued input
    String input;


    
    // Image representing the player
    public static final Image img = new Image("file:Assets/Images/Ameoba.png");//TODO: add the image here

    
    

    


    // Constructor for Player
    public Player(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.PLAYER, operationInterval);
    }

    // Method to queue input
    public void queueInput(String input) {
        this.inputPending = input; // Save the input into inputPending
        System.out.println("Input queued: " + input);
    }

    // Method to handle player initialization logic (constructor-like behavior)
    public void Player() {
        inputPending = ""; // Initialize inputPending as an empty string
        System.out.println("Player initialized.");
    }

    // Method to handle player death logic
    public void killPlayer() {
        System.out.println("Player killed."); // Simulating player death
        // Add logic to remove the player from the game session
    }

    @Override
    public void interact(Tile tile) {
        // Logic for player interaction with another tile
        System.out.println("Interacting with tile: " + tile.getTileType());
    }

    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        // Update tile's visual and/or game state
        draw(img, 0, 0); // Draw the player's image at (0,0)
    }

    public void onKeyPressed(String key) {
        System.out.println("Key " + key + " pressed.");
        queueInput(key); // Queue the key as input
    }

    public void onKeyReleased(String key) {
        System.out.println("Key " + key + " released.");
    }
}
