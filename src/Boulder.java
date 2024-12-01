import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Boulder extends FallingObject{

    private static final String LEFT_DIRECTION = "Left";
    private static final String RIGHT_DIRECTION = "Right";

    public static final Image img = new Image("file:Assets/Images/BoulderSymmetrical.png");//TODO: add the image here


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
    public void interact(Tile tile){
         if(tile.getTileType() == TileType.PLAYER){

             //Check if player to left of boulder and a path to right of boulder
             if(tile.getXPosition() == this.x - 1 &&
                     gameSession.getTileFromGrid(this.x + 1,this.y).getTileType() == TileType.PATH){
                 pushBoulder(tile, RIGHT_DIRECTION);
             }

             //Check if player to right of boulder and a path to left of boulder
             if(tile.getXPosition() == this.x + 1 &&
                     gameSession.getTileFromGrid(this.x - 1,this.y).getTileType() == TileType.PATH){
                 pushBoulder(tile, LEFT_DIRECTION);
             }
         }
    }

    /**
     * Checks if the boulder should fall or roll based on
     * the presence of tiles surrounding it
     * @param currentTimeInMilliseconds
     * The number of milliseconds since 01/01/1970.
     */
    public void updateTile(long currentTimeInMilliseconds) {
        int xPosition = getXPosition();
        int yPosition = getYPosition();

        if (yPosition != 0) { //Check boulder is above the bottom layer of the grid
            Tile tileBelow = gameSession.getTileFromGrid(xPosition,yPosition - 1);

            //Check if boulder should fall
            if (tileBelow.getTileType() == TileType.PATH) {
                this.fall(xPosition, yPosition);
                this.isFalling = true;
            }else{
                this.isFalling = false;
            }

            if(tileBelow.getTileType() == TileType.PATH && isFalling){
                tileBelow.interact(this); //Call interact on player
            }
        }

        if (xPosition != 0 && yPosition != 0) { //Check boulder not on left edge or bottom of grid
            Tile tileToLeft = gameSession.getTileFromGrid(xPosition - 1, yPosition);
            Tile tileLeftBelow = gameSession.getTileFromGrid(xPosition - 1, yPosition - 1);

            //Check if boulder should roll left
            if (tileToLeft.getTileType() == TileType.PATH && tileLeftBelow.getTileType() == TileType.PATH) {
                this.roll(xPosition, yPosition, LEFT_DIRECTION);
            }
        }


        if (xPosition < (gameSession.getGridWidth() - 1) && yPosition != 0) {//Check boulder not on left edge or bottom of grid
            Tile tileToRight = gameSession.getTileFromGrid(xPosition + 1, yPosition);
            Tile tileRightBelow = gameSession.getTileFromGrid(xPosition + 1, yPosition - 1);

            //Check if boulder should roll right
            if (tileToRight.getTileType() == TileType.PATH && tileRightBelow.getTileType() == TileType.PATH) {
                this.roll(xPosition, yPosition, RIGHT_DIRECTION);
            }
        }
    }


    /**
     * Pushes the boulder in a specified direction
     * @param player the player that is pushing the boulder
     * @param direction the direction the boulder is being pushed
     */
    private void pushBoulder(Tile player, String direction){
        int offset;
        switch (direction){
            case "Right":
                offset = 1;
                break;
            case "Left":
                offset = -1;
                break;
            default:
                throw new IllegalArgumentException("Invalid direction " + direction + " Allowed values are 'Left' or 'Right");
        }

        PathWall pathWall = new PathWall(gameSession, this.x + (-offset), this.y, getOperationInterval());
        gameSession.updateTilePositions(pathWall, player,this);
        gameSession.setTile(this.x + offset,this.y, this);
    }



    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }
}



