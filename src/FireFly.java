public class FireFly extends FlyingEnemy{
    public FireFly(GameSession gameSession, int x, int y, TileType TileType, long operationInterval, boolean prioritiseRight){
        super(gameSession, x, y, TileType, operationInterval, prioritiseRight);
    }
    public void interact(Tile Tile){
        //TODO implement an interact function
    }
    public void updateTile(long currentTimeInMilliseconds){
        //TODO implement an updateTile every certain number of ms
    }
}
