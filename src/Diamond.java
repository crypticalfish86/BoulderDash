import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents a diamond in the game, which can be collected by the player.
 * @author Isaac Atkinson
 * @author Alex (Tsz Tung Yee)
 * @version 1.2
 */

public class Diamond extends FallingObject{

    private final static int SCORE_VALUE = 50;
    
    private static final Image img = new Image("file:Assets/Images/Diamond.png");//TODO: add the image here






    /**
     * Constructs a diamond tile.
     * @param gameSession The current game session.
     * @param x the x position of the diamond.
     * @param y the y position of the diamond.
     * @param operationInterval The time in ms between operations.
     */
    public Diamond(GameSession gameSession, int x, int y, long operationInterval){
        super(gameSession, x, y, TileType.DIAMOND, operationInterval);
        
    }

    /**
     * Moves player to current tile and deletes diamond if player interacts
     * with this tile. Also updates the score and diamond count for
     * the current game session.
     * @param tile the tile that is interacting with this tile.
     */
    public void interact(Tile tile){
        if(tile.getTileType() == TileType.PLAYER){
            PathWall pathWall = new PathWall(gameSession, tile.getXPosition(), tile.getYPosition(), getOperationInterval());
            gameSession.updateTilePositions(pathWall, tile,this); //Move player to this tile and delete diamond

            updateGameSessionData();
        }
    }

    /**
     * Checks if the diamond should fall or roll based on
     * the presence of tiles surrounding it.
     * @param currentTimeInMilliseconds
     * The number of milliseconds since 01/01/1970.
     */
    public void updateTile(long currentTimeInMilliseconds) {
        updatePhysics();
    }


    private void updateGameSessionData(){
        GameSessionData currentSessionData = gameSession.getCurrentSessionData();
        currentSessionData.updateScore(SCORE_VALUE); //Update player's score
        currentSessionData.incrementDiamondCount(); //update player's diamond count
    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }

    public String returnStringTileRepresentation(){
        return "*";
    }
}
