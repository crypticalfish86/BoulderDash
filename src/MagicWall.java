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
        System.out.println("MagicWall interacted with tile: " + inputTileObject.getTileType());

        // Check the type of the interacting tile
        switch (inputTileObject.getTileType()) {
            case FALLING_OBJECT:
                if (inputTileObject instanceof Boulder) {
                    transformTile(inputTileObject, TileType.DIAMOND); // Transform Boulder into Diamond
                }
                break;
            case DIAMOND:
                transformTile(inputTileObject, TileType.FALLING_OBJECT); // Transform Diamond into Boulder
                break;
            default:
                System.out.println("No transformation applied by MagicWall.");
        }
    }

    // Transform the tile to the new type
    private void transformTile(Tile inputTileObject, TileType newTileType) {
        int x = inputTileObject.getXPosition();
        int y = inputTileObject.getYPosition();
        Tile newTile;

        // Create the new tile based on the new tile type
        if (newTileType == TileType.FALLING_OBJECT) {
            newTile = new Boulder(gameSession, x, y, inputTileObject.getOperationInterval());
            System.out.println("Transformed Diamond into Boulder at (" + x + ", " + y + ").");
        } else if (newTileType == TileType.DIAMOND) {
            newTile = new Diamond(gameSession, x, y, inputTileObject.getOperationInterval());
            System.out.println("Transformed Boulder into Diamond at (" + x + ", " + y + ").");
        } else {
            System.out.println("Unknown transformation type.");
            return;
        }

        // Replace the old tile with the new tile
        gameSession.setTile(y, x, newTile);
    }

    // Activate the magic wall
    private void activateWall() {
        this.isActive = true;
        this.activationStartTime = System.currentTimeMillis();
        System.out.println("MagicWall activated!");
    }

    // Deactivate the magic wall
    private void deactivateWall() {
        this.isActive = false;
        System.out.println("MagicWall deactivated.");
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
