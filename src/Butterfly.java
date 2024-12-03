import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Butterfly extends FlyingEnemy{

    
    public static final Image img = new Image("file:Assets/Images/Butterfly.png"); // Placeholder for the image




    public Butterfly(GameSession gameSession, int x, int y, long operationInterval, boolean prioritiseDirection){
        super(gameSession, x, y, TileType.MOVING_ENEMY, operationInterval, prioritiseDirection);
        this.amoebaCanSpreadToThisTile = true;
    }

    public void interact(Tile tile){
        if (tile.getTileType() == TileType.FALLING_OBJECT && tile.getYPosition() == this.y - 1) {
            this.triggerExplosion(this.x, this.y, true);
        }
    }
    public void updateTile(long currentTimeInMilliseconds){
        ticksAlive++;

        if(ticksAlive % 20 == 0){
            this.newMove(this, this.x, this.y);
        }

        
    }


    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }

    public String returnStringTileRepresentation(){
        return "BF";//TODO discuss how we're doing this with omar (the butterfly direction)
    }
}
