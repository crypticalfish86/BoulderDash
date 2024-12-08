import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents an explosion in the game, which quickly disappear and are
 * replaced by either a path or a diamond.
 * @author Isaac Atkinson
 * @version 1.0
 */

public class Explosion extends Tile {

    private int ticksAlive;
    private final int ticksToConvert = 35;

    private Boolean replaceWithDiamond;
    
    public static final Image img = new Image("file:Assets/Images/Explosion.png"); // Placeholder for the image


    
    public Explosion(GameSession gameSession, int x, int y, long operationInterval, Boolean replaceWithDiamond) {
        super(gameSession, x, y, TileType.EXPLOSION, operationInterval);
        this.replaceWithDiamond = replaceWithDiamond;
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
            Diamond diamond = new Diamond(gameSession, this.x, this.y, operationInterval);
            gameSession.setTile(this.y,this.x, diamond);
        }else{
            PathWall pathWall = new PathWall(gameSession, this.x, this.y, operationInterval);
            gameSession.setTile(this.y,this.x, pathWall);
        }
    }

    public String returnStringTileRepresentation(){
        return "EX"; //TODO make sure we code for this in loading, inform armaan
    }
}
