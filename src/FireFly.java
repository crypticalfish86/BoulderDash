import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class FireFly extends FlyingEnemy{

    
    public static final Image img = new Image("file:Assets/Images/FireFly.png"); // Placeholder for the image



    public FireFly(GameSession gameSession, int x, int y, long operationInterval, boolean prioritiseRight){
        super(gameSession, x, y, TileType.MOVING_ENEMY, operationInterval, prioritiseRight);
        this.amoebaCanSpreadToThisTile = true;
    }
    public void interact(Tile Tile){
        //TODO implement an interact function
    }
    public void updateTile(long currentTimeInMilliseconds){
        //TODO implement an updateTile every certain number of ms
    }


    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }
}
