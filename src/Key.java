import javafx.scene.image.Image;

public class Key extends Wall{
    private char keyColour;

    public static final Image img = new Image("./"); // TODO: add the image path here

    public Key(GameSession gameSession, int x, int y, TileType TileType, long operationInterval) {
        super(gameSession, x, y, TileType, operationInterval);
        this.amoebaCanSpreadToThisTile = false;
    }

    public char getKeyColour() {
        return this.keyColour;
    }

    public void interact(Tile inputTileObject) {
        // Example interaction logic
        if (inputTileObject instanceof Player) {
            System.out.println("Player picked up the key!");
            // Remove the key from the map or add it to the player's inventory
        }
    }
    public void onUpdate(long timePassedInMilliseconds) {
        draw(img, 0, 0); // Draw the key image at the specified location
    }

    public void draw(Image img, int x, int y) {
    }

    @Override
    public void updateTile(long currentTimeInMilliseconds) {

    }
}






