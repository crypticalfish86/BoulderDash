public class Boulder extends FallingObject{
    public Boulder(GameSession gameSession, int x, int y, TileType TileType, long operationInterval){
        super(gameSession, x, y, TileType, operationInterval);
        this.amoebaCanSpreadToThisTile = false;
    }

    public void interact(Tile Tile){
        //TODO implement an interact function for the boulder
    }

    public void updateTile(long currentTimeInMilliseconds) {
        int XPosition = getXPosition();
        int YPosition = getYPosition();

        if (YPosition != 0) { //Check boulder is above the bottom layer of the grid
            Tile tileBelow = thisTilesGamesession.getTileFromGrid(XPosition,YPosition - 1);
            if (tileBelow instanceof PathWall) { //Check if boulder should fall
                this.fall(XPosition, YPosition);
            }
        }

        if (XPosition != 0 && YPosition != 0) { //Check boulder not on left edge or bottom of grid
            Tile tileToLeft = thisTilesGamesession.getTileFromGrid(XPosition - 1, YPosition);
            Tile tileLeftBelow = thisTilesGamesession.getTileFromGrid(XPosition - 1, YPosition - 1);

            //Check if boulder should roll left
            if (tileToLeft instanceof PathWall && tileLeftBelow instanceof PathWall) {
                String direction = "Left";
                this.roll(XPosition, YPosition, direction);
            }
        }


        if (XPosition < (thisTilesGamesession.getGridWidth() - 1) && YPosition != 0) {//Check boulder not on left edge or bottom of grid
            Tile tileToRight = thisTilesGamesession.getTileFromGrid(XPosition + 1, YPosition);
            Tile tileRightBelow = thisTilesGamesession.getTileFromGrid(XPosition + 1, YPosition - 1);

            //Check if boulder should roll right
            if (tileToRight instanceof PathWall && tileRightBelow instanceof PathWall) {
                String direction = "Right";
                this.roll(XPosition, YPosition, direction);
            }
        }
    }
}



