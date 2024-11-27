import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

public class PathWall extends Wall {



    public static final Image img = new Image("file:Assets/Images/PathWall.png");//TODO: add the image here



    public PathWall(GameSession gameSession, int x, int y, TileType TileType, long operationInterval) {
        super(gameSession, x, y, TileType, operationInterval);
        this.amoebaCanSpreadToThisTile = true;
    }

    public void interact(Tile Tile){
        //TODO implement an interact function
    }
    




    public void updateTile(long currentTimeInMilliseconds) {
        // draw(img, 0, 0);
    }

    protected void draw(GraphicsContext gc, Image img, int xOffset, int yOffset) {
        gc.drawImage(img,
                this.x * GameSession.GRID_SIZE + xOffset,
                this.y * GameSession.GRID_SIZE + yOffset
        );
    }
}
