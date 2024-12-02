import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TitaniumWall extends Wall {
    
    public static final Image img = new Image("file:Assets/Images/TitaniumWallVers2.png"); // Placeholder for the image
    public TitaniumWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.STATIC_TILE, operationInterval);
        this.amoebaCanSpreadToThisTile = false;
    }

    public void interact(Tile inputTileObject) {
        System.out.println("Titanium walls cannot be destroyed or interacted with.");
    }

    public void updateTile(long currentTimeInMilliseconds) {
        // System.out.println("TitaniumWall update logic here."); // Update logic if needed
    }



    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }

    public String returnStringTileRepresentation(){
        return "T";
    }
}
