import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Key extends Wall {
    private Image img; // Image representing the key
    private String keyColour; // Key's color or unique identifier

    public Key(GameSession gameSession, int x, int y, long operationInterval, String keyColour) {
        super(gameSession, x, y, TileType.KEY, operationInterval);
        this.keyColour = keyColour;
        this.img = selectImageBasedOnColor(keyColour); // Set the image based on the key color
         // Keys cannot spread amoebas
    }



    // Interaction logic for when the player interacts with the key
    @Override
    public void interact(Tile inputTileObject) {
        if (inputTileObject.getTileType() == TileType.PLAYER) {
            System.out.println("Player picked up the key of color: " + returnStringTileRepresentation());

            // Add the key to GameSessionData
            gameSession.getCurrentSessionData().giveKey(returnStringTileRepresentation());

            PathWall pathWall = new PathWall(gameSession, inputTileObject.getXPosition(), inputTileObject.getYPosition(), getOperationInterval());
            gameSession.updateTilePositions(pathWall, inputTileObject, this);

            System.out.println("Key of color " + returnStringTileRepresentation() + " added to GameSessionData and removed from the grid.");
        } else {
            System.out.println("Only the player can pick up keys.");
        }
    }

    @Override
    public void updateTile(long currentTimeInMilliseconds) {
    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0); // Draw the key image
    }

    @Override
    public String returnStringTileRepresentation() {
        return this.keyColour;
    }

    // Select the image based on the key color
    private Image selectImageBasedOnColor(String color) {
        switch (color) {
            case "RK": // Red key
                return new Image("file:Assets/Images/RedKey.png");
            case "BK": // Blue key
                return new Image("file:Assets/Images/BlueKey.png");
            case "YK": // Yellow key
                return new Image("file:Assets/Images/YellowKey.png");
            case "GK": // Green key
                return new Image("file:Assets/Images/GreenKey.png");
        }

        // If no case matches, throw an exception
        throw new IllegalArgumentException("Unsupported key color: " + color);
    }
}
