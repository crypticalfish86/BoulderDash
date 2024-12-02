public abstract class FallingObject extends Tile {
    


    private static final int DELAY_FALL = 8;
    private static final int DELAY_ROLL = 13;

    protected int fallReload = 0;
    private boolean isFalling = false;





    public FallingObject(GameSession gameSession, int x, int y, TileType TileType, long operationInterval) {
        super(gameSession, x, y, TileType, operationInterval);
    }

    /**
     * Causes the object to fall downwards and
     * replaces its currentTile with a {@link PathWall}
     * @param lowerTile the tile below the boulder
     */
    private boolean fall(Tile lowerTile) {
        
        int thisY = this.getYPosition();
        lowerTile.interact(this); //somebody decided to not make interact to return a boolean so i have to check :(
        
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
     * Causes the object to roll in a specified direction and
     * replaces its currentTile with a {@link PathWall}
     * @param nextTile the adjacent tile
     * @param nextTileBelow the adjacent tile's lower tile
     */
    protected void roll(Tile nextTile, Tile nextTileBelow){

        
        nextTile.interact(this);
        nextTileBelow.interact(this);
        isFalling = true;

        fallReload = DELAY_ROLL;
    }




    /**
     * Signals the falling object to try falling or rolling.
     * Should be called during update when other calcualtiosn are done.
     */
    protected void updatePhysics() {

        if (fallReload > 0) { fallReload--; return; }

        
        int xPosition = getXPosition();
        int yPosition = getYPosition();

        //no operations, if the boulder is on the bottom of the grid
        if (yPosition >= gameSession.getGridHeight() - 1) { return; }



        
        Tile tileBelow = gameSession.getTileFromGrid(xPosition, yPosition + 1);


        //boulder will try to interact with tiles below if it has momentum, or if the lower tile os a path
        if (tileBelow.tileType == TileType.PATH || isFalling) {
            if (fall(tileBelow)) { return; }
        }




        //try to roll left
        if (xPosition > 0) {
            Tile tileLeft = gameSession.getTileFromGrid(xPosition - 1, yPosition);
            Tile tileLeftBelow = gameSession.getTileFromGrid(xPosition - 1, yPosition + 1);

            //Check if boulder should roll left
            if (tileLeft.getTileType() == TileType.PATH && tileLeftBelow.getTileType() == TileType.PATH) {
                this.roll(tileLeft, tileLeftBelow);
                return;
            }
        }



        //try to roll right
        if (xPosition < gameSession.getGridWidth() - 1) {
            Tile tileRight = gameSession.getTileFromGrid(xPosition + 1, yPosition);
            Tile tileRightBelow = gameSession.getTileFromGrid(xPosition + 1, yPosition + 1);

            //Check if boulder should roll right
            if (tileRight.getTileType() == TileType.PATH && tileRightBelow.getTileType() == TileType.PATH){
                this.roll(tileRight, tileRightBelow);
                return;
            }
        }
    }
}


