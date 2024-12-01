import javafx.scene.canvas.GraphicsContext;
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
        GameSession session = getGameSession();
        if (session != null) {
            System.out.println("Game Session is available.");
        }
    }

    // Handle player input for movement and actions
    public void onKeyPressed(String key) {
        System.out.println("Key " + key + " pressed.");

        // Handle movement based on key input
        switch (key.toLowerCase()) {
            case "w": // Move up
                move(0, -1);
                break;
            case "a": // Move left
                move(-1, 0);
                break;
            case "s": // Move down
                move(0, 1);
                break;
            case "d": // Move right
                move(1, 0);
                break;
            default:
                System.out.println("Unrecognized key: " + key);
                break;
        }
    }

    public void onKeyReleased(String key) {
        System.out.println("Key " + key + " released.");
        // Handle key release logic here, if needed
    }

    // Move the player to a new tile based on the direction
    private void move(int deltaX, int deltaY) {
        int newX = getXPosition() + deltaX;
        int newY = getYPosition() + deltaY;

        // Check bounds or collision logic here, if needed
        if (canMoveTo(newX, newY)) {
            setNewPosition(newX, newY); // Update position using the setter method
            System.out.println("Player moved to position: (" + newX + ", " + newY + ")");
        } else {
            System.out.println("Cannot move to position: (" + newX + ", " + newY + ")");
        }
    }

    // Example method to check if the player can move to a specific tile
    private boolean canMoveTo(int x, int y) {
        // Add collision logic or bounds checking here
        // For example, ensure x and y are within the game grid boundaries
        return x >= 0 && y >= 0 && x < getGameSession().getGridWidth() && y < getGameSession().getGridHeight();
    }

    @Override
    public void interact(Tile other) {
        // Implement interaction logic with other tiles
        System.out.println("Player interacting with tile: " + other.getTileType());
    }

    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        GraphicsContext gc = this.gameSession.getGraphicsContext(); // Access GraphicsContext
        draw(gc, img, getXPosition(), getYPosition());
    }


    @Override
    public void drawTile(GraphicsContext gc) {
        // Draw the player image on the graphics context
        draw(gc, img, 0, 0);
    }
}
