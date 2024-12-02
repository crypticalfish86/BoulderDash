import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Boulder extends FallingObject{


    public static final Image img = new Image("file:Assets/Images/BoulderSymmetrical.png");//TODO: add the image here


    private static final int DELAY_PUSH_BOULDER = 10;

    public Boulder(GameSession gameSession, int x, int y, long operationInterval){
        super(gameSession, x, y, TileType.FALLING_OBJECT, operationInterval);
        this.amoebaCanSpreadToThisTile = false;
    }


    /**
     * Handles pushing the boulder based on the
     * player's position relative to the boulder and
     * whether there is a path in the direction the boulder
     * is being pushed
     * @param tile the tile that is interacting with this tile
     */
    public void interact(Tile tile) {
        switch (tile.tileType) {
            case EXPLOSION:
                //put something here :D
                break;

            case PLAYER:
                if (tile.getYPosition() == this.getYPosition()) {
                    int otherX = tile.getXPosition();
                    int thisX = this.getXPosition();

                    if (otherX == thisX + 1) { pushBoulder(tile, false); }
                    if (otherX == thisX - 1) { pushBoulder(tile, true); }
                }        
            default:
                break;
        }
        
    }

    /**
     * Checks if the boulder should fall or roll based on
     * the presence of tiles surrounding it
     * @param currentTimeInMilliseconds
     * The number of milliseconds since 01/01/1970.
     */
    public void updateTile(long currentTimeInMilliseconds) {
        updatePhysics();
    }


    /**
     * Pushes the boulder in a specified direction
     * @param player the player that is pushing the boulder
     * @param isRight the direction the boulder is being pushed
     */
    private void pushBoulder(Tile player, boolean isRight) {
        System.out.println(isRight);

        //tries to interact behind
        int thisX = getXPosition();
        int thisY = getYPosition();
        Tile nextTile = gameSession.getTileFromGrid(thisX + (isRight ? 1 : -1), thisY);



        nextTile.interact(this);
        //if it's pushed through, pull the player
        if (getXPosition() != thisX) {

            gameSession.getTileFromGrid(thisX, thisY).interact(player);

            this.fallReload = DELAY_PUSH_BOULDER;            
        }


    }



    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }


    public String returnStringTileRepresentation(){
        return "@";
    }
}



