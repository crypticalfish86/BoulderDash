public abstract class FallingObject extends Tile{
    protected boolean isFalling;

    public FallingObject(GameSession gameSession, int x, int y, TileType TileType, long operationInterval){
        super(gameSession, x, y, TileType, operationInterval);
        isFalling = false;
    }

    /**
     * Causes the object to fall downwards
     * @param XPosition the current x co-ordinate of the object
     * @param YPosition the current y co-ordinate of the object
     */
    protected void fall(int XPosition, int YPosition){
        PathWall pathWall = new PathWall(thisTilesGamesession, XPosition, YPosition,TileType.STATIC_TILE,getOperationInterval());
        Tile outgoingTile = thisTilesGamesession.getTileFromGrid(XPosition,YPosition - 1);
        thisTilesGamesession.updateTilePositions(pathWall, this,outgoingTile);
    }

    /**
     * Causes the object to roll in a specified direction
     * @param XPosition the current x co-ordinate of the object
     * @param YPosition the current y co-ordinate of the object
     * @param direction the direction the object should roll ("right" or "left")
     */
    protected void roll(int XPosition, int YPosition, String direction){
        int offset;
        if(direction.equals("Right")){
            offset = 1;
        }else{
            offset = -1;
        }
        PathWall pathWall = new PathWall(thisTilesGamesession, XPosition, YPosition,TileType.STATIC_TILE,getOperationInterval());
        Tile outgoingTile = thisTilesGamesession.getTileFromGrid(XPosition + offset, YPosition);
        thisTilesGamesession.updateTilePositions(pathWall, this,outgoingTile);
    }
}


