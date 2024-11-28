import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Frog extends Enemy{

    
    public static final Image img = new Image("file:Assets/Images/Frog.png"); // Placeholder for the image




    public Frog(GameSession gameSession, int x, int y, long operationInterval){
        super(gameSession, x, y, TileType.MOVING_ENEMY, operationInterval);
        this.amoebaCanSpreadToThisTile = true;
    }

    private void calculateFastedPath(){};//engage in pathfinding and then call "moveTo" function (in superclass)

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
