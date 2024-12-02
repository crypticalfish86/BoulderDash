import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Explosion extends Tile {

    private int ticksAlive;
    private final int ticksToConvert = 20;

    private Boolean replaceWithDiamond;
    
    public static final Image img = new Image("file:Assets/Images/Explosion.png"); // Placeholder for the image




    public Explosion(GameSession gameSession, int x, int y, long operationInterval, Boolean replaceWithDiamond) {
        super(gameSession, x, y, TileType.EXPLOSION, operationInterval);
        this.replaceWithDiamond = replaceWithDiamond;
        ticksAlive = 1;

    }
    public void interact(Tile Tile) {

    }
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
