import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Butterfly extends FlyingEnemy{

    private int ticksAlive;
    public static final Image img = new Image("file:Assets/Images/Butterfly.png"); // Placeholder for the image




    public Butterfly(GameSession gameSession, int x, int y, long operationInterval, boolean prioritiseLeft){
        super(gameSession, x, y, TileType.MOVING_ENEMY, operationInterval, prioritiseLeft);
        this.amoebaCanSpreadToThisTile = true;
        this.lastTimeStamp = System.currentTimeMillis();
        ticksAlive = 0;
    }

    public void interact(Tile tile){
        if (tile.getTileType() == TileType.FALLING_OBJECT && tile.getYPosition() == this.y - 1) {
            this.triggerExplosion(this.x, this.y, true);
        }
    }


    /**
     * Moves one tile every operation interval.
     * @param currentTimeInMilliseconds
     * The number of milliseconds since the unix epoch (01/01/1970).
     */
    public void updateTile(long currentTimeInMilliseconds){
        if(currentTimeInMilliseconds - this.lastTimeStamp >= this.operationInterval){
            //move(this.x, this.y, true);
            this.lastTimeStamp = System.currentTimeMillis();
        }

        ticksAlive++;
        if(ticksAlive % 20 == 0){
            if(prioritiseLeft){
                moveLeft(this.x, this.y);
            }else{
                moveRight(this.x, this.y);
            }

        }

    }


    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }

    public String returnStringTileRepresentation(){
        if(this.prioritiseLeft){
            return "BL";
        }
        else {
            return "BR";
        }
    }
}
