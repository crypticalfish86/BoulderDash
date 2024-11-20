public class Frog extends Enemy{
    public Frog(GameSession gameSession, int x, int y, TileType TileType, long operationInterval){
        super(gameSession, x, y, TileType, operationInterval);
    }

    private void calculateFastedPath(){};//engage in pathfinding and then call "moveTo" function (in superclass)

    public void interact(Tile Tile){
        //TODO implement an interact function
    }
    public void updateTile(long currentTimeInMilliseconds){
        //TODO implement an updateTile every certain number of ms
    }
}
