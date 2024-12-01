import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class Player extends Tile {

    public static final Image img = new Image("file:Assets/Images/Player.png"); // Placeholder for the image
    private List<Key> inventory; // List to store collected keys

    public Player(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.PLAYER, operationInterval);
        this.inventory = new ArrayList<>(); // Initialize the inventory
    }

    public void somePlayerLogic() {
        GameSession session = getGameSession();
        if (session != null) {
            System.out.println("Game Session is available.");
        }
    }

    public void onKeyPressed(String key) {
        System.out.println("Key " + key + " pressed.");
        switch (key.toLowerCase()) {
            case "w": move(0, -1); break;
            case "a": move(-1, 0); break;
            case "s": move(0, 1); break;
            case "d": move(1, 0); break;
            default: System.out.println("Unrecognized key: " + key); break;
        }
    }

    public void onKeyReleased(String key) {
        System.out.println("Key " + key + " released.");
    }

    private void move(int deltaX, int deltaY) {
        int newX = getXPosition() + deltaX;
        int newY = getYPosition() + deltaY;
        if (canMoveTo(newX, newY)) {
            setNewPosition(newX, newY);
            interact(gameSession.getTileFromGrid(newX, newY)); // Interact with the tile at the new position
            System.out.println("Player moved to position: (" + newX + ", " + newY + ")");
        } else {
            System.out.println("Cannot move to position: (" + newX + ", " + newY + ")");
        }
    }

    private boolean canMoveTo(int x, int y) {
        return x >= 0 && y >= 0 && x < getGameSession().getGridWidth() && y < getGameSession().getGridHeight();
    }

    @Override
    public void interact(Tile other) {
        System.out.println("Player interacting with tile: " + other.getTileType());

        if (other instanceof Key) {
            // Handle interaction with a Key
            Key key = (Key) other;
            addKeyToInventory(key);

            // Remove the key from the grid by setting the tile to null
            gameSession.setTile(other.getYPosition(), other.getXPosition(), null);
            System.out.println("Player picked up a key of color: " + key.getKeyColour());
        } else if (other instanceof Door) {
            // Handle interaction with a Door
            Door door = (Door) other;
            door.interact(this); // Pass the player object to the door's interact method
        } else {
            System.out.println("Player cannot interact with this tile.");
        }
    }

    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        draw(this.gameSession.getGraphicsContext(), img, getXPosition(), getYPosition());
    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }



    // Add the killPlayer method
    public void killPlayer() {
        System.out.println("Player has been killed.");
        gameSession.endGame(); // Assuming endGame is implemented in GameSession
    }

    // Add key to the player's inventory
    public void addKeyToInventory(Key key) {
        inventory.add(key);
        System.out.println("Key of color " + key.getKeyColour() + " added to inventory.");
    }

    // Get the player's inventory
    public List<Key> getInventory() {
        return inventory;
    }
}
