import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MagicWall extends Wall {

    public static final Image img = new Image("file:Assets/Images/MagicWall.png"); // Path to the Magic Wall image
    private boolean isActive; // Indicates whether the magic wall is active
    private long activationStartTime; // Timestamp for when the wall was activated

    public MagicWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.MAGIC_WALL, operationInterval);
        this.activationStartTime = 0;
    }

    // Interaction logic for the Magic Wall
    @Override
    public void interact(Tile inputTileObject) {

        // Check the type of the interacting tile
        switch (inputTileObject.getTileType()) {
            case BOULDER:
                if (inputTileObject.getYPosition() == this.y - 1) {
                    transformAndEjectTile(inputTileObject, TileType.DIAMOND); // Transform Boulder into Diamond
                }
                break;
            case DIAMOND:
                if (inputTileObject.getYPosition() == this.y - 1) {
                    transformAndEjectTile(inputTileObject, TileType.BOULDER); // Transform Diamond into Boulder
                }
                break;
            default:

        }
    }

    // Transform and eject the tile to the other side of the Magic Wall
    private void transformAndEjectTile(Tile inputTileObject, TileType newTileType) {
        if(this.y == gameSession.getGridHeight() - 1){
            return;

        }
        if(!(gameSession.getTileFromGrid(this.x,this.y + 1).getTileType() == TileType.PATH)){
            return;

        }

        int x = inputTileObject.getXPosition();
        int y = inputTileObject.getYPosition();
        Tile newTile;

        // Create the new tile based on the new tile type
        if (newTileType == TileType.BOULDER) {
            newTile = new Boulder(gameSession, x, y, inputTileObject.getOperationInterval());
        } else if (newTileType == TileType.DIAMOND) {
            newTile = new Diamond(gameSession, x, y, inputTileObject.getOperationInterval());
        } else {
            return;
        }

        // Replace the old tile with the new tile
        gameSession.setTile(y, x, newTile);

        PathWall pathWall = new PathWall(gameSession, this.x, this.y -1, inputTileObject.getOperationInterval());
        gameSession.setTile(this.y -1, this.x, pathWall);

        // Attempt to eject the new tile to the other side of the Magic Wall
        ejectTileToOtherSide(newTile);
    }

    // Eject the tile to the opposite side of the Magic Wall
    private void ejectTileToOtherSide(Tile tile) {
        int wallX = this.getXPosition();
        int wallY = this.getYPosition();
        int tileX = tile.getXPosition();
        int tileY = tile.getYPosition();

        int dx = tileX - wallX; // Determine the direction of entry
        int dy = tileY - wallY;

        int exitX = wallX - dx; // Determine the exit point
        int exitY = wallY - dy;

        // Check if the exit position is within bounds
        if (exitX >= 0 && exitX < gameSession.getGridWidth() &&
                exitY >= 0 && exitY < gameSession.getGridHeight()) {

            Tile exitTile = gameSession.getTileFromGrid(exitX, exitY);

            // Check if the exit tile is passable (PathWall or empty)
            if (exitTile.getTileType() == TileType.PATH) {
                // Move the transformed tile to the exit position
                gameSession.setTile(exitY, exitX, tile);
            }
        }

    }


    // Update logic for the magic wall
    @Override
    public void updateTile(long currentTimeInMilliseconds) {

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
