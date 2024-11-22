public class Boulder extends FallingObject{
    public Boulder(GameSession gameSession, int x, int y, TileType TileType, long operationInterval){
        super(gameSession, x, y, TileType, operationInterval);
        this.amoebaCanSpreadToThisTile = false;
    }

    public void interact(Tile Tile){
        //TODO implement an interact function for the boulder
    }

    public void updateTile(long currentTimeInMilliseconds){
        //TODO implement an updateTile every certain number of ms
    }
}
