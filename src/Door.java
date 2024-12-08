import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Door extends Wall {

    private Image img; // The image representing the door
    private String doorColour; // The color of the door

    public Door(GameSession gameSession, int x, int y, long operationInterval, String doorColour) {
        super(gameSession, x, y, TileType.DOOR, operationInterval);
        this.doorColour = doorColour;
        this.img = selectImageBasedOnColour(doorColour); // Set the image based on the door color
        
    }

    // Getter for door color
    public String getDoorColour() {
        return this.doorColour;
    }

    // Interact method for the door
    @Override
    public void interact(Tile inputTileObject) {
        if (inputTileObject.getTileType() == TileType.PLAYER) {
            // Check if the player has the correct key in their inventory
            if (gameSession.getCurrentSessionData().tryConsumeKey(getDoorColour())) {
                unlockDoor(inputTileObject); // Unlock the door
            }
        }
    }


    // Logic to unlock or remove the door
    private void unlockDoor(Tile inputTileObject) {
        // Replace the door on the grid with a PathWall (or another passable tile)
        PathWall pathWall = new PathWall(gameSession, inputTileObject.getXPosition(), inputTileObject.getYPosition(), getOperationInterval());
        gameSession.updateTilePositions(pathWall, inputTileObject, this);
    }

    @Override
    public void updateTile(long currentTimeInMilliseconds) {
    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0); // Draw the door image
    }

    public String returnStringTileRepresentation() {
        return this.doorColour;
    }

    // Select the image based on the door color
    private Image selectImageBasedOnColour(String color) {
        switch (color) {
            case "RD": // Red door
                return new Image("file:Assets/Images/RedDoor.png");
            case "BD": // Blue door
                return new Image("file:Assets/Images/BlueDoor.png");
            case "YD": // Yellow door
                return new Image("file:Assets/Images/YellowDoor.png");
            case "GD": // Green door
                return new Image("file:Assets/Images/GreenDoor.png");
        }

        // If no case matches, throw an exception
        throw new IllegalArgumentException("Unsupported door color: " + color);
    }
}