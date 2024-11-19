public abstract class FlyingEnemy extends Enemy{
    protected boolean prioritiseRight;
    public FlyingEnemy(GameSession gameSession, int x, int y, TileType TileType, long operationInterval, boolean prioritiseRight){
        super(gameSession, x, y, TileType, operationInterval);
        this.prioritiseRight = prioritiseRight;
    }
}
