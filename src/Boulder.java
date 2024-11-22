public class Boulder extends FallingObject{
    public Boulder(GameSession gameSession, int x, int y, TileType TileType, long operationInterval){
        super(gameSession, x, y, TileType, operationInterval);
        this.amoebaCanSpreadToThisTile = false;
    }

    public void interact(Tile Tile){
        //TODO implement an interact function for the boulder
    }

    public void updateTile(long currentTimeInMilliseconds){
        Tile tileBelow = thisTilesGamesession.getTileFromGrid(getXPosition(), getYPosition() - 1);
        if(tileBelow instanceof PathWall){
            this.fall();

        }
    }

    private void fall(){
        PathWall pathWall = new PathWall(thisTilesGamesession, getXPosition(), getYPosition(),TileType.STATIC_TILE,getOperationInterval());
        Tile outgoingTile = thisTilesGamesession.getTileFromGrid(getXPosition(),getYPosition() - 1);
        thisTilesGamesession.updateTilePositions(pathWall, this,outgoingTile);
    }
}
