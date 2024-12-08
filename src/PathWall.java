import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PathWall extends Wall {

    // Image representing the PathWall (replace with the actual image path)
    public static final Image img = new Image("file:Assets/Images/BlackPath.png"); // Path to the PathWall image

    // Constructor
    public PathWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.PATH, operationInterval);
    }

    // Handle interactions with other tiles
    @Override
    public void interact(Tile tile) {
        
        PathWall pathWall = new PathWall(
            gameSession,
            tile.getXPosition(),
            tile.getYPosition(),
            operationInterval
        );

        gameSession.updateTilePositions(pathWall, tile, this);
    }


    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        
    }

    // Draw the PathWall tile
    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0); // Draw the PathWall image at its current position
    }

    public String returnStringTileRepresentation(){
        return "-";
    }
}
