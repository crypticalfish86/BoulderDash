import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Key extends Wall {
    private char keyColour; // Key's color or unique identifier

    // Image representing the key (update with appropriate file paths for other key colors if needed)
    public static final Image img = new Image("file:Assets/Images/RedKey.png");

    public Key(GameSession gameSession, int x, int y, long operationInterval, char keyColour) {
        super(gameSession, x, y, TileType.KEY, operationInterval);
        this.keyColour = keyColour;
        this.amoebaCanSpreadToThisTile = false; // Keys cannot spread amoebas
    }

    // Getter for the key's color
    public char getKeyColour() {
        return this.keyColour;
    }

    // Interaction logic for when the player interacts with the key
    @Override
    public void interact(Tile inputTileObject) {
        if (inputTileObject.getTileType() == TileType.PLAYER) {
            System.out.println("Player picked up the key of color: " + getKeyColour());

            // Add the key to GameSessionData
            gameSession.getCurrentSessionData().giveKey(getKeyColour());

            // Replace the key on the grid with a PathWall (or another appropriate tile)
            PathWall pathWall = new PathWall(gameSession, getXPosition(), getYPosition(), getOperationInterval());
            gameSession.setTile(getYPosition(), getXPosition(), pathWall);

            System.out.println("Key of color " + getKeyColour() + " added to GameSessionData and removed from the grid.");
        } else {
            System.out.println("Only the player can pick up keys.");
        }
    }

    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        // Keys remain static
    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0); // Draw the key image
    }

    @Override
    public String returnStringTileRepresentation() {
        return Character.toString(this.keyColour);
    }
}
