import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

public class PathWall extends Wall {



    public static final Image img = new Image("file:Assets/Images/PathWall.png");//TODO: add the image here



    public PathWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.STATIC_TILE, operationInterval);
        this.amoebaCanSpreadToThisTile = true;
    }

    public void interact(Tile Tile){
        //TODO implement an interact function
    }
    




    public void updateTile(long currentTimeInMilliseconds) {
        // draw(img, 0, 0);
    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }
}
