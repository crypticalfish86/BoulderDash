import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class NormalWall extends Wall {

    public static final Image img = new Image("file:Assets/Images/NormalWallVers3.png");

    public NormalWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.NORMAL_WALL, operationInterval);
        
    }

    @Override
    public void interact(Tile tile) {
        
    }

    public void updateTile(long currentTimeInMilliseconds) {
        // draw(img, 0, 0);
    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }

    public String returnStringTileRepresentation(){
        return "W";
    }
}
