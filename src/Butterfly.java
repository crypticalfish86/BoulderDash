import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * This class represents a butterfly in the game, which drops diamonds when killed by a
 * falling object.
 * @author Isaac Atkinson
 * @author Omar Zarugh
 * @version 1.0
 */

public class Butterfly extends FlyingEnemy{

    public static final Image img = new Image("file:Assets/Images/Butterfly.png"); // Placeholder for the image




    public Butterfly(GameSession gameSession, int x, int y, long operationInterval, boolean prioritiseLeft){
        super(gameSession, x, y, TileType.MOVING_ENEMY, operationInterval, prioritiseLeft);
        
        this.lastTimeStamp = System.currentTimeMillis();
    }

    public void interact(Tile tile){
        if ((tile.getTileType() == TileType.BOULDER || tile.getTileType() == TileType.DIAMOND) && tile.getYPosition() == this.y - 1) {
            this.triggerExplosion(this.x, this.y, true);
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

        //Kill butterfly if next to an amoeba
        if(isNextToAmoeba(this.x,this.y)){
            PathWall pathWall = new PathWall(gameSession, this.x, this.y, operationInterval);
            gameSession.setTile(this.y,this.x,pathWall);
        }

        checkForPlayer();

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
