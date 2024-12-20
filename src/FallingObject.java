/**
 * This class represents a blueprint for falling objects which are capable of
 * rolling and falling.
 * @author Isaac Atkinson
 * @author Alex (Tsz Tung Yee)
 * @version 1.2
 */
public abstract class FallingObject extends Tile {

    private static final int DELAY_FALL = 8;
    private static final int DELAY_ROLL = 13;

    protected int fallReload = 0;
    private boolean isFalling = false;

    /**
     * Constructor blueprint for falling objects.
     * @param gameSession The current game session.
     * @param x the x position of the falling object.
     * @param y the y position of the object.
     * @param TileType The tile type.
     * @param operationInterval The time in ms between operations.
     */
    public FallingObject(GameSession gameSession, int x, int y, TileType TileType,
                         long operationInterval, boolean isFalling) {
        super(gameSession, x, y, TileType, operationInterval);
        this.isFalling = isFalling;
    }

    /**
     * Causes the object to fall downwards.
     * @param lowerTile the tile below the boulder.
     */
    private boolean fall(Tile lowerTile) {
        
        int thisY = this.getYPosition();
        lowerTile.interact(this);
        
        if (thisY != getYPosition()) {
            fallReload = DELAY_FALL;
            isFalling = true;
            return true;
        } else {
            isFalling = false;
            return false;
        }
    }

    /**
     * Causes the object to roll in a specified direction.
     * @param nextTile the adjacent tile.
     * @param nextTileBelow the adjacent tile's lower tile.
     */
    protected void roll(Tile nextTile, Tile nextTileBelow){
        nextTile.interact(this);
        nextTileBelow.interact(this);
        isFalling = true;

        fallReload = DELAY_ROLL;
    }




    /**
     * Signals the falling object to try falling or rolling.
     * Should be called during update when other calculations are done.
     */
    protected void updatePhysics() {
        if (fallReload > 0) { fallReload--; return; }

        
        int xPosition = getXPosition();
        int yPosition = getYPosition();

        //no operations, if the boulder is on the bottom of the grid
        if (yPosition >= gameSession.getGridHeight() - 1) { return; }



        
        Tile tileBelow = gameSession.getTileFromGrid(xPosition, yPosition + 1);


        //boulder will try to interact with tiles below if it has momentum,
        // or if the lower tile is a path or magic wall
        if (    tileBelow.tileType == TileType.PATH ||
                tileBelow.tileType == TileType.MAGIC_WALL ||
                isFalling) {

            if (fall(tileBelow)) { return; }
        }




        //try to roll left
        if (xPosition > 0) {
            Tile tileLeft =
                    gameSession.getTileFromGrid(xPosition - 1, yPosition);
            Tile tileLeftBelow =
                    gameSession.getTileFromGrid(xPosition - 1, yPosition + 1);

            //Check if boulder should roll left
            if (    tileLeft.getTileType() == TileType.PATH &&
                    tileLeftBelow.getTileType() == TileType.PATH &&
                    onCurvedTile(this.x,this.y)) {

                this.roll(tileLeft, tileLeftBelow);
                return;
            }

        }

        //try to roll right
        if (xPosition < gameSession.getGridWidth() - 1) {
            Tile tileRight =
                    gameSession.getTileFromGrid(xPosition + 1, yPosition);
            Tile tileRightBelow =
                    gameSession.getTileFromGrid(xPosition + 1, yPosition + 1);

            //Check if boulder should roll right
            if (    tileRight.getTileType() == TileType.PATH &&
                    tileRightBelow.getTileType() == TileType.PATH &&
                    onCurvedTile(this.x,this.y)){

                this.roll(tileRight, tileRightBelow);
                return;
            }
        }
    }

    /**
     * Determines whether a falling object is on a curved tile.
     * @param x the x position of the falling object.
     * @param y the y position of the falling object.
     * @return true if the falling object is on a curved tile, false otherwise.
     */
    private boolean onCurvedTile(int x, int y) {
        Tile tileBelow = gameSession.getTileFromGrid(x,y + 1);
        if(tileBelow.getTileType() == TileType.BOULDER){
            return true;
        }else if(tileBelow.getTileType() == TileType.DIAMOND){
            return true;
        }else if(tileBelow.getTileType() == TileType.TITANIUM_WALL){
            return true;
        }else if(tileBelow.getTileType() == TileType.DIRT_WALL) {
            return true;
        }else if(tileBelow.getTileType() == TileType.NORMAL_WALL) {
            return true;
        }else if(tileBelow.getTileType() == TileType.EXIT_WALL){
            return true;
        }
        else{
            return false;
        }
    }
}


