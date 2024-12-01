import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Key extends Wall {
    private char keyColour; // Key's color or unique identifier

    public static final Image img = new Image("file:Assets/Images/Key.png"); // Replace with the actual image path

    public Key(GameSession gameSession, int x, int y, TileType tileType, long operationInterval, char keyColour) {
        super(gameSession, x, y, tileType, operationInterval);
        this.keyColour = keyColour;
        this.amoebaCanSpreadToThisTile = false; // Keys cannot spread amoebas
    }

    public char getKeyColour() {
        return this.keyColour;
    }

    @Override
    public void interact(Tile inputTileObject) {
        if (inputTileObject instanceof Player) {
            Player player = (Player) inputTileObject;
            System.out.println("Player picked up the key of color: " + keyColour);
            player.addKeyToInventory(this);

            // Remove the key from the game grid by setting it to null
            gameSession.setTile(getYPosition(), getXPosition(), null);
        }
    }

    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        // No specific update logic for keys
    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0); // Draw the key image
    }
}
