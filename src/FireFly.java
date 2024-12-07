import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class FireFly extends FlyingEnemy{

    public static final Image img = new Image("file:Assets/Images/FireFly.png"); // Placeholder for the image



    public FireFly(GameSession gameSession, int x, int y, long operationInterval, boolean prioritiseLeft){
        super(gameSession, x, y, TileType.MOVING_ENEMY, operationInterval, prioritiseLeft);
        this.amoebaCanSpreadToThisTile = true;
        this.lastTimeStamp = System.currentTimeMillis();
    }
    public void interact(Tile tile){
        if (tile.getTileType() == TileType.FALLING_OBJECT && tile.getYPosition() == this.y - 1) {
            this.triggerExplosion(this.x, this.y, false);
        }
    }

    /**
     * Finds the next move to make and moves every certain number of ticks
     * @param currentTimeInMilliseconds
     * The number of milliseconds since the unix epoch (01/01/1970).
     */
    public void updateTile(long currentTimeInMilliseconds){
        //Set initial direction
        //Doing here because can't be done in constructor
        if(ticksAlive == 0){
            setInitialDirection(this.x,this.y,this.prioritiseLeft);
        }

        ticksAlive++;

        if(ticksAlive % DELAY_FACTOR == 0){
            if(prioritiseLeft){
                moveLeft(this.x, this.y);
            }else{
                moveRight(this.x, this.y);
            }

        }
        //    if(currentTimeInMilliseconds - this.lastTimeStamp >= this.operationInterval){
//            //move(this.x, this.y);
//            this.lastTimeStamp = System.currentTimeMillis();
//
//
//        }
    }



    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }

    public String returnStringTileRepresentation(){
        if (prioritiseLeft){
            return "FL";
        }
        else {
            return "FR";
        }
    }
}
