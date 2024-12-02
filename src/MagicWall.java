import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MagicWall extends Wall {

    public static final Image img = new Image("file:Assets/Images/MagicWall.png"); // Path to the Magic Wall image
    private boolean isActive; // Indicates whether the magic wall is active
    private long activationStartTime; // Timestamp for when the wall was activated

    public MagicWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.MAGIC_WALL, operationInterval);
        this.amoebaCanSpreadToThisTile = false;
        this.isActive = false; // Initially inactive
        this.activationStartTime = 0;
    }

    // Interaction logic for the Magic Wall
    @Override
    public void interact(Tile inputTileObject) {
        System.out.println("Magic wall activated: Interaction started!");
        if (!isActive) {
            activateWall(); // Activate the wall
        } else {
            System.out.println("Magic wall is already active.");
        }
    }

    // Activate the magic wall
    private void activateWall() {
        this.isActive = true;
        this.activationStartTime = System.currentTimeMillis();
        System.out.println("Magic wall is now active!");
    }

    // Deactivate the magic wall
    private void deactivateWall() {
        this.isActive = false;
        System.out.println("Magic wall has been deactivated.");
    }

    // Update logic for the magic wall
    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        if (isActive) {
            long elapsedTime = currentTimeInMilliseconds - activationStartTime;

            // Example behavior: Deactivate after 10 seconds
            if (elapsedTime > 10_000) {
                deactivateWall();
            }

            // Add additional magic wall effects here, such as transforming surrounding tiles
        }
    }

    // Draw the magic wall
    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0); // Draw the magic wall image
    }

    // Return a string representation of the magic wall
    @Override
    public String returnStringTileRepresentation() {
        return "M"; // 'M' for Magic Wall
    }
}
