public class Door extends Wall{

    private char doorColour;
    

    public Door(GameSession gameSession, int x, int y, TileType TileType, long operationInterval) {
        super(gameSession, x, y, TileType, operationInterval);
        this.amoebaCanSpreadToThisTile = false;
    }

    public char getDoorColour() {
        return this.doorColour;
    }

    public void interact(Tile Tile){
        //TODO implement an interact function
    }
    public void updateTile(long currentTimeInMilliseconds){
        //TODO implement an updateTile every certain number of ms
    }
}
