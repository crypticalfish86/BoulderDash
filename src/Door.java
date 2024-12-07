import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Door extends Wall {

    private Image img; // The image representing the door
    private String doorColour; // The color of the door

    public Door(GameSession gameSession, int x, int y, long operationInterval, String doorColour) {
        super(gameSession, x, y, TileType.DOOR, operationInterval);
        this.doorColour = doorColour;
        this.img = selectImageBasedOnColor(doorColour); // Set the image based on the door color
        
    }

    // Getter for door color
    public String getDoorColour() {
        return this.doorColour;
    }

    // Interact method for the door
    @Override
    public void interact(Tile inputTileObject) {
        if (inputTileObject.getTileType() == TileType.PLAYER) {
            System.out.println("Player is interacting with a door of color: " + getDoorColour());

            // Check if the player has the correct key in their inventory
            if (gameSession.getCurrentSessionData().tryConsumeKey(getDoorColour())) {
                System.out.println("Door unlocked with the correct key: " + getDoorColour());
                unlockDoor(); // Unlock the door
            } else {
                System.out.println("Door cannot be opened without the correct key: " + getDoorColour());
            }
        } else {
            System.out.println("Only the player can interact with the door.");
        }
    }


    // Logic to unlock or remove the door
    private void unlockDoor() {
        // Replace the door on the grid with a PathWall (or another passable tile)
        PathWall pathWall = new PathWall(gameSession, getXPosition(), getYPosition(), getOperationInterval());
        gameSession.setTile(getYPosition(), getXPosition(), pathWall);

        System.out.println("Door at (" + getXPosition() + ", " + getYPosition() + ") has been unlocked and replaced with a PathWall.");
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
    private Image selectImageBasedOnColor(String color) {
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