import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Key extends Wall {
    private Image img; // Image representing the key
    private char keyColour; // Key's color or unique identifier

    public Key(GameSession gameSession, int x, int y, long operationInterval, char keyColour) {
        super(gameSession, x, y, TileType.KEY, operationInterval);
        this.keyColour = keyColour;
        this.img = selectImageBasedOnColor(keyColour); // Set the image based on the key color
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
    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0); // Draw the key image
    }

    @Override
    public String returnStringTileRepresentation() {
        return Character.toString(this.keyColour);
    }

    // Select the image based on the key color
    private Image selectImageBasedOnColor(char color) {
        switch (color) {
            case 'r': // Red key
                return new Image("file:Assets/Images/RedKey.png");
            case 'b': // Blue key
                return new Image("file:Assets/Images/BlueKey.png");
            case 'y': // Yellow key
                return new Image("file:Assets/Images/YellowKey.png");
            case 'g': // Green key
                return new Image("file:Assets/Images/GreenKey.png");
        }

        // If no case matches, throw an exception
        throw new IllegalArgumentException("Unsupported key color: " + color);
    }
}
