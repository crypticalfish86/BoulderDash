import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ExitWall extends Wall {

    private static final Image activeImg = new Image("file:Assets/Images/NetherPortal.png"); // Active portal image
    private static final Image inactiveImg = new Image("file:Assets/Images/UnactivatedPortal.png"); // Inactive portal image

    private boolean isActive; // Indicates whether the exit is active

    public ExitWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.EXIT_WALL, operationInterval);
        this.isActive = false; // Exit is initially inactive
        this.amoebaCanSpreadToThisTile = false;
    }

    // Check if the exit is active
    public boolean isActive() {
        return this.isActive;
    }

    // Activate the exit (if all diamonds are collected)
    public void activateIfDiamondsCollected() {
        if (!isActive) {
            GameSessionData sessionData = gameSession.getCurrentSessionData();
            if (sessionData.getDiamondCount() >= sessionData.getDiamondsRequired()) {
                this.isActive = true;
                System.out.println("Exit wall activated! All diamonds have been collected.");
            }
        }
    }

    // Handle interaction with the exit wall
    @Override
    public void interact(Tile inputTileObject) {
        // Ensure the wall is active before allowing the player to exit
        if (this.isActive && inputTileObject.getTileType() == TileType.PLAYER) {
            System.out.println("Player exits through the exit wall!");
            changeLevel(); // Trigger level change
        } else if (!this.isActive) {
            System.out.println("Exit wall is not active. Collect all diamonds to activate it.");
        } else {
            System.out.println("Only the player can interact with the exit wall.");
        }
    }

    // Update tile (periodically check if the wall should be activated)
    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        // Check activation condition
        activateIfDiamondsCollected();
    }

    // Handle level transition
    public void changeLevel() {
        System.out.println("Level changed!");
        gameSession.onGameOver(true); // Assuming this transitions to the next level
    }

    // Draw the exit wall
    @Override
    public void drawTile(GraphicsContext gc) {
        // Select the image based on the active state
        Image currentImg = isActive ? activeImg : inactiveImg;
        draw(gc, currentImg, 0, 0); // Draw the selected image
    }

    public String returnStringTileRepresentation() {
        return "E";
    }
}
