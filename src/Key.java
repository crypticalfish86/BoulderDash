import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Key extends Wall {
    private Image img; // Image representing the key
    private String keyColour; // Key's colour or unique identifier

    public Key(GameSession gameSession, int x, int y, long operationInterval, String keyColour) {
        super(gameSession, x, y, TileType.KEY, operationInterval);
        this.keyColour = keyColour;
        this.img = selectImageBasedOnColour(keyColour); // Set the image based on the key colour
        // Keys cannot spread amoebas
    }


    // Interaction logic for when the player interacts with the key
    @Override
    public void interact(Tile inputTileObject) {
        if (inputTileObject.getTileType() == TileType.PLAYER) {
            // Add the key to GameSessionData
            gameSession.getCurrentSessionData().giveKey(returnStringTileRepresentation());

            PathWall pathWall = new PathWall(gameSession, inputTileObject.getXPosition(), inputTileObject.getYPosition(), getOperationInterval());
            gameSession.updateTilePositions(pathWall, inputTileObject, this);
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

    /**
     * Selects an image corresponding to a specific key colour.
     * <p>
     * This method determines which image to load based on the
     * colour key string. (e.g., red, blue, yellow, green).
     * If the provided colour key is not recognised,
     * an exception is thrown.
     *
     * @param colour The colour code of the key as a String.
     * @return The Image corresponding to the specified key colour.
     * @throws IllegalArgumentException If the specified key colour is unsupported.
     */
    private Image selectImageBasedOnColour(String colour) {
        switch (colour) {
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
        throw new IllegalArgumentException("Unsupported key colour: " + colour);
    }

}