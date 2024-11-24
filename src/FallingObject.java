public abstract class FallingObject extends Tile{
    protected boolean isFalling;

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
        PathWall pathWall = new PathWall(gameSession, XPosition, YPosition,TileType.STATIC_TILE,getOperationInterval());
        Tile outgoingTile = gameSession.getTileFromGrid(XPosition,YPosition - 1);
        gameSession.updateTilePositions(pathWall, this,outgoingTile);
        PathWall pathWall = new PathWall(gameSession, XPosition, YPosition,TileType.STATIC_TILE,getOperationInterval());
        Tile outgoingTile = gameSession.getTileFromGrid(XPosition,YPosition - 1);
        gameSession.updateTilePositions(pathWall, this,outgoingTile);
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
        if(direction.equals("Right")){
            offset = 1;
        }else{
            offset = -1;
        }
        PathWall pathWall = new PathWall(gameSession, XPosition, YPosition,TileType.STATIC_TILE,getOperationInterval());
        Tile outgoingTile = gameSession.getTileFromGrid(XPosition + offset, YPosition);
        gameSession.updateTilePositions(pathWall, this,outgoingTile);
    }
}


