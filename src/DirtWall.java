public class DirtWall extends Wall{
    public DirtWall(GameSession gameSession, int x, int y, TileType TileType, long operationInterval) {
        super(gameSession, x, y, TileType, operationInterval);
        this.amoebaCanSpreadToThisTile = true;
    }

    public void interact(Tile Tile){
        //TODO implement an interact function
    }
    public void updateTile(long currentTimeInMilliseconds){
        //TODO implement an updateTile every certain number of ms
    }
}
