public abstract class FallingObject extends Tile{
    protected boolean isFalling;
    
    private static final String LEFT_DIRECTION = "Left";
    private static final String RIGHT_DIRECTION = "Right";


    

    public FallingObject(GameSession gameSession, int x, int y, TileType TileType, long operationInterval){
        super(gameSession, x, y, TileType, operationInterval);
        isFalling = false;
    }

    /**
     * Causes the object to fall downwards and
     * replaces its currentTile with a {@link PathWall}
     * @param XPosition the current x co-ordinate of the object
     * @param YPosition the current y co-ordinate of the object
     */
    protected void fall(int XPosition, int YPosition){
        PathWall pathWall = new PathWall(gameSession, XPosition, YPosition, getOperationInterval());
        Tile outgoingTile = gameSession.getTileFromGrid(XPosition,YPosition + 1);
        gameSession.updateTilePositions(pathWall, this, outgoingTile);
    }

    /**
     * Causes the object to roll in a specified direction and
     * replaces its currentTile with a {@link PathWall}
     * @param XPosition the current x co-ordinate of the object
     * @param YPosition the current y co-ordinate of the object
     * @param direction the direction the object should roll ("right" or "left")
     * @throws IllegalArgumentException if the provided direction is invalid
     */
    protected void roll(int XPosition, int YPosition, String direction){
        int offset;
        switch(direction){
            case "Right":
                offset = 1;
                break;
            case "Left":
                offset = -1;
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction + "Allowed values are 'Left' or 'Right ");
        }

        PathWall pathWall = new PathWall(gameSession, XPosition, YPosition, getOperationInterval());
        Tile outgoingTile = gameSession.getTileFromGrid(XPosition + offset, YPosition);
        gameSession.updateTilePositions(pathWall, this,outgoingTile);
    }




    /**
     * 
     */
    protected void updatePhysics() {
        int xPosition = getXPosition();
        int yPosition = getYPosition();

        if (yPosition != 0) { //Check object is above the bottom layer of the grid
            Tile tileBelow = gameSession.getTileFromGrid(xPosition,yPosition + 1);

            //Check if object should fall
            if (tileBelow.getTileType() == TileType.PATH) {
                this.fall(xPosition, yPosition);
                this.isFalling = true;
            }else{
                this.isFalling = false;
            }

            if(tileBelow.getTileType() == TileType.PLAYER && isFalling){
                tileBelow.interact(this); //Call interact on player
            }else if(tileBelow.getTileType() == TileType.MOVING_ENEMY && isFalling){
                tileBelow.interact(this); //Call interact on enemy
            }else if(tileBelow.getTileType() == TileType.MAGIC_WALL && isFalling){
                tileBelow.interact(this); //Call interact on magic wall
            }
        }

        if (xPosition != 0 && yPosition != 0) { //Check object not on left edge or bottom of grid
            Tile tileToLeft = gameSession.getTileFromGrid(xPosition - 1, yPosition);
            Tile tileLeftBelow = gameSession.getTileFromGrid(xPosition - 1, yPosition + 1);

            //Check if object should roll left
            if (tileToLeft.getTileType() == TileType.PATH && tileLeftBelow.getTileType() == TileType.PATH) {
                this.roll(xPosition, yPosition, LEFT_DIRECTION);
            }
        }


        if (xPosition < (gameSession.getGridWidth() - 1) && yPosition != 0) {//Check object not on left edge or bottom of grid
            Tile tileToRight = gameSession.getTileFromGrid(xPosition + 1, yPosition);
            Tile tileRightBelow = gameSession.getTileFromGrid(xPosition + 1, yPosition + 1);

            //Check if object should roll right
            if (tileToRight.getTileType() == TileType.PATH && tileRightBelow.getTileType() == TileType.PATH){
                this.roll(xPosition, yPosition, RIGHT_DIRECTION);
            }
        }
    }
}


