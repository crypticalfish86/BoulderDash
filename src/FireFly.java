import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class FireFly extends FlyingEnemy{

    
    public static final Image img = new Image("file:Assets/Images/FireFly.png"); // Placeholder for the image



    public FireFly(GameSession gameSession, int x, int y, long operationInterval, boolean prioritiseDirection){
        super(gameSession, x, y, TileType.MOVING_ENEMY, operationInterval, prioritiseDirection);
        this.amoebaCanSpreadToThisTile = true;
    }
    public void interact(Tile tile){
        if (tile.getTileType() == TileType.FALLING_OBJECT && tile.getYPosition() == this.y - 1) {
            this.triggerExplosion(this.x, this.y, false);
        }
    }
    public void updateTile(long currentTimeInMilliseconds){
        ticksAlive++;

        if(ticksAlive % 20 == 0){
            this.move( this.x, this.y);
        }
    }


    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }

    public String returnStringTileRepresentation(){
        return "FF";//TODO ask omar how we're doing this (firefly directions)
    }
}
