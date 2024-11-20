public abstract class Enemy extends Tile{
    public Enemy(GameSession gameSession, int x, int y, TileType TileType, long operationInterval){
        super(gameSession, x, y, TileType, operationInterval);
    }

    protected void moveTo(){}; //move to a tile after executing pathfinding
}
