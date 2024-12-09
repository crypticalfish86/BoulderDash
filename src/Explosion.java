import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents an explosion in the game, which quickly disappear and are
 * replaced by either a path or a diamond.
 * @author Isaac Atkinson
 * @version 1.1
 */

public class Explosion extends Tile {

    private int ticksAlive;
    private final int ticksToConvert = 35;

    private boolean replaceWithDiamond;
    private boolean wasPlayer;
    
    private static final Image img = new Image("file:Assets/Images/Explosion.png");


    /**
     * Constructs an explosion tile.
     * @param gameSession The current game session.
     * @param x the x position of the explosion.
     * @param y the y position of the explosion.
     * @param operationInterval The time in ms between operations.
     * @param replaceWithDiamond Represents whether the explosion
     *                           should leave behind a diamond.
     * @param wasPlayer Represents whether the tile was the player
     *                  before becoming an explosion.
     */
    public Explosion(GameSession gameSession, int x, int y,
                     long operationInterval, boolean replaceWithDiamond, boolean wasPlayer) {

        super(gameSession, x, y, TileType.EXPLOSION, operationInterval);
        this.replaceWithDiamond = replaceWithDiamond;
        this.wasPlayer = wasPlayer;
        ticksAlive = 0;
    }

    public void interact(Tile Tile) {

    }

    /**
     * Converts the explosion to another tile after a certain number of ticks.
     * @param currentTimeInMilliseconds The number of milliseconds since 01/01/1970.
     */
    public void updateTile(long currentTimeInMilliseconds) {
        ticksAlive++;

        if(ticksAlive == ticksToConvert){
            convertExplosion();
        }
    }



    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }


    /**
     * Converts the explosion to a path or a diamond based on what enemy exploded.
     */
    private void convertExplosion(){
        if(replaceWithDiamond){
            Diamond diamond = new Diamond(gameSession, this.x, this.y,
                    operationInterval);
            gameSession.setTile(this.y,this.x, diamond);
        }else{
            PathWall pathWall = new PathWall(gameSession, this.x, this.y,
                    operationInterval);
            gameSession.setTile(this.y,this.x, pathWall);
        }

        if(wasPlayer){
            gameSession.onGameOver(false);
        }


    }

    public String returnStringTileRepresentation(){
        return "EX"; //TODO make sure we code for this in loading, inform armaan
    }
}
