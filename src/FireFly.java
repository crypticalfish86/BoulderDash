import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents a firefly in the game.
 * @author Isaac Atkinson
 * @author Omar Zarugh
 * @version 1.2
 */
public class FireFly extends FlyingEnemy{

    private static final Image img = new Image("file:Assets/Images/FireFly.png");

    /**
     * Constructs an instance of a firefly.
     * @param gameSession The current game session.
     * @param x The x position of the firefly.
     * @param y The y position of the firefly.
     * @param operationInterval The time interval between each operation.
     * @param prioritiseLeft The edge the firefly is following.
     */
    public FireFly(GameSession gameSession, int x, int y, long operationInterval,
                   boolean prioritiseLeft){

        super(gameSession, x, y, TileType.MOVING_ENEMY, operationInterval,
                prioritiseLeft);

        this.lastTimeStamp = System.currentTimeMillis();
    }

    /**
     * Handles killing the firefly and triggering an explosion if the
     * butterfly is hit by a boulder or diamond.
     * @param tile The tile that is interacting with this tile.
     */
    public void interact(Tile tile){
        if ((   tile.getTileType() == TileType.BOULDER ||
                tile.getTileType() == TileType.DIAMOND) &&
                tile.getYPosition() == this.y - 1) {
            this.triggerExplosion(this.x, this.y, false);
        }
    }

    /**
     * Finds the next move to make and moves every certain number of ticks.
     * @param currentTimeInMilliseconds
     * The number of milliseconds since the unix epoch (01/01/1970).
     */
    public void updateTile(long currentTimeInMilliseconds){
        //Set initial direction
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

        //Kill firefly if next to an amoeba
        if(isNextToAmoeba(this.x,this.y)){
            PathWall pathWall = new PathWall(gameSession, this.x, this.y,
                    operationInterval);

            gameSession.setTile(this.y,this.x,pathWall);
        }

        checkForPlayer();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }

    /**
     * {@inheritDoc}
     */
    public String returnStringTileRepresentation(){
        if (prioritiseLeft){
            return "FL";
        }
        else {
            return "FR";
        }
    }
}
