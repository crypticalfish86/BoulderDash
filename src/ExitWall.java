import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ExitWall extends Wall {

    public static final Image img = new Image("file:Assets/Images/ExitWall.png"); // Placeholder for the image

    private boolean isActive; // Indicates whether the exit is active

    public ExitWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.STATIC_TILE, operationInterval);
        this.isActive = false; // Exit is initially inactive
        this.amoebaCanSpreadToThisTile = false;
    }

    // Check if the exit is active
    public boolean isActive() {
        return this.isActive;
    }

    // Activate the exit
    public void activate() {
        this.isActive = true;
        System.out.println("Exit wall activated!");
    }

    // Handle interaction with the exit wall
    @Override
    public void interact(Tile inputTileObject) {
        // if (this.isActive && inputTileObject instanceof Player) {
        //     System.out.println("Player exits through the exit wall!");
        //     changeLevel(); // Trigger level change
        // } else if (!this.isActive) {
        //     System.out.println("Exit wall is not active.");
        // } else {
        //     System.out.println("Only the player can interact with the exit wall.");
        // }
    }

    // Update tile (optional, for animations or effects)
    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        // Add optional update logic, such as animations when active
        if (isActive) {
            System.out.println("ExitWall is active and ready.");
        }
    }

    // Handle level transition
    public void changeLevel() {
        System.out.println("Level changed!");
        // Implement logic to load the next level
        // Example: Notify the game session to handle level transition
        gameSession.endGame(); // Assuming this transitions to the next level
    }

    // Draw the exit wall
    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0); // Draw the exit wall image
    }

    public String returnStringTileRepresentation(){
        return "E";
    }
}
