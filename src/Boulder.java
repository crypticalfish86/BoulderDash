import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents a boulder in the game, which can be pushed by the player.
 * @author Isaac Atkinson
 * @author Alex (Tsz Tung Yee)
 * @version 1.2
 */

public class Boulder extends FallingObject{


    private static final Image img =
            new Image("file:Assets/Images/BoulderSymmetrical.png");


    private static final int DELAY_PUSH_BOULDER = 10;

    /**
     * Constructs a boulder tile.
     * @param gameSession The current game session.
     * @param x the x position of the boulder.
     * @param y the y position of the boulder.
     * @param operationInterval The time in ms between operations.
     */
    public Boulder(GameSession gameSession, int x, int y, long operationInterval, boolean isFalling){
        super(gameSession, x, y, TileType.BOULDER, operationInterval, isFalling);
        
    }

    /**
     * Handles pushing the boulder based on the
     * player's position relative to the boulder and
     * whether there is a path in the direction the boulder
     * is being pushed.
     * @param tile the tile that is interacting with this tile.
     */
    public void interact(Tile tile) {
        switch (tile.tileType) {
            case PLAYER:
                if (tile.getYPosition() == this.getYPosition()) {
                    int otherX = tile.getXPosition();
                    int thisX = this.getXPosition();

                    if (otherX == thisX + 1) {
                        pushBoulder(tile, false); }
                    if (otherX == thisX - 1) {
                        pushBoulder(tile, true); }
                }        
            default:
                break;
        }
        
    }

    /**
     * Checks if the boulder should fall or roll based on
     * the presence of tiles surrounding it.
     * @param currentTimeInMilliseconds
     * The number of milliseconds since 01/01/1970.
     */
    public void updateTile(long currentTimeInMilliseconds) {
        updatePhysics();
    }


    /**
     * Pushes the boulder in a specified direction.
     * @param player the player that is pushing the boulder.
     * @param isRight the direction the boulder is being pushed.
     */
    private void pushBoulder(Tile player, boolean isRight) {

        //tries to interact behind
        int thisX = getXPosition();
        int thisY = getYPosition();
        Tile nextTile = gameSession.getTileFromGrid(
                thisX + (isRight ? 1 : -1), thisY);



        nextTile.interact(this);
        //if it's pushed through, pull the player
        if (getXPosition() != thisX) {

            gameSession.getTileFromGrid(thisX, thisY).interact(player);

            this.fallReload = DELAY_PUSH_BOULDER;            
        }


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

        return "@";
    }
}



