
public abstract class Tile {
    protected GameSession thisTilesGamesession;

    protected int x;
    protected int y;

    protected TileType tileType;
    protected long lastTimeStamp;
    protected long operationInterval;


    public Tile(GameSession gameSession, int x, int y, TileType tileType, long operationInterval) {
        this.thisTilesGamesession = gameSession;
        this.x = x;
        this.y = y;
        this.tileType = tileType;
    }
    public TileType getTileType() {
        return this.tileType;
    }
    public int getXPosition() {
        return this.x;
    }
    public int getYPosition() {
        return this.y;
    }
    public void setNewPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public abstract void interact(Tile Tile);
    public abstract void updateTile(long currentTimeInMilliseconds);
}