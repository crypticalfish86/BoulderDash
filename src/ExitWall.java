public class ExitWall extends Wall{
    private boolean isActive;
    public ExitWall(GameSession gameSession, int x, int y, TileType TileType, long operationInterval) {
        super(gameSession, x, y, TileType, operationInterval);
        this.isActive = false;
    }

    public void activate(){
        this.isActive = true;
    }
    public void interact(Tile Tile){
        //TODO implement an interact function
    }
    public void updateTile(long currentTimeInMilliseconds){
        //TODO implement an updateTile every certain number of ms
    }

    public void changeLevel(){
        //TODO implement a method of changing the level
    }
}
