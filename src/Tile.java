
public abstract class Tile {
    protected int x;
    protected int y;
    protected long lastTimeStamp;
    protected long operationInterval;


    public Tile(GameSession GameSession, int x, int y, TileType TileType, long operationInterval) {
        //TODO decided i would wait and see how the rest went down before expanding on these.
    }
    public TileType getTileType() {
        return (TileType);
        //TODO this really wants an expression. Just going off the convergent UML, this is what i came up with but it needs more refining.
    }
    public int getXPosition() {
        return x;
    }
    public int getYPosition() {
        return y;
    }
    public void setNewPosition(int x, int y) {
        //TODO decided i would wait and see how the rest went down before expanding on these.
    }
    public void interactInputTileObject(Tile Tile) {
        //TODO decided i would wait and see how the rest went down before expanding on these.
    }
    public void updateTile(long currentTimeInMilliseconds) {
        //TODO decided i would wait and see how the rest went down before expanding on these.
    }
}