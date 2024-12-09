import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents a titanium wall in the game.
 * @author Jace Weerawardena
 * @author Cameron mcDonald
 */

public class TitaniumWall extends Wall {
    
    private static final Image img =
            new Image("file:Assets/Images/TitaniumWallVers2.png");

    /**
     * Constructs a titanium wall tile.
     * @param gameSession The current game session.
     * @param x the x position of the titanium wall.
     * @param y the y position of the titanium wall.
     * @param operationInterval The time in ms between operations.
     */
    public TitaniumWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.TITANIUM_WALL, operationInterval);
    }

    public void interact(Tile tile) {

    }

    public void updateTile(long currentTimeInMilliseconds) {

    }



    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }

    public String returnStringTileRepresentation(){
        return "T";
    }
}
