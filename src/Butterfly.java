public class Butterfly extends FlyingEnemy{
    public Butterfly(GameSession gameSession, int x, int y, TileType TileType, long operationInterval, boolean prioritiseRight){
        super(gameSession, x, y, TileType, operationInterval, prioritiseRight);
        this.amoebaCanSpreadToThisTile = true;
    }

    public void interact(Tile Tile){
        //TODO implement an interact function
    }
    public void updateTile(long currentTimeInMilliseconds){
        //TODO implement an updateTile every certain number of ms
    }
}
