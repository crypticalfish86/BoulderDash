import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TitaniumWall extends Wall {
    
    public static final Image img = new Image("file:Assets/Images/TitaniumWallVers2.png"); // Placeholder for the image
    public TitaniumWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.TITANIUM_WALL, operationInterval);
    }

    public void interact(Tile inputTileObject) {

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
