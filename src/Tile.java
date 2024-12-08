import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Tile {
    protected GameSession gameSession;

    protected int x;
    protected int y;

    protected TileType tileType;
    protected long lastTimeStamp;
    protected long operationInterval;


    public Tile(GameSession gameSession, int x, int y, TileType tileType, long operationInterval) {
        this.gameSession = gameSession;
        this.x = x;
        this.y = y;
        this.tileType = tileType;
        //TODO add a starting timestamp here
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
    public long getOperationInterval(){return this.operationInterval;}
    public void setNewPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }




    public abstract void interact(Tile Tile);
    public abstract void updateTile(long currentTimeInMilliseconds);
    public abstract String returnStringTileRepresentation();

    //method used for drawing the icon
    protected void draw(GraphicsContext gc, Image img, int xOffset, int yOffset) {
        gc.drawImage(img,
            (this.x + xOffset - this.gameSession.getCameraX()) * this.gameSession.getCameraScale() + Main.WINDOW_WIDTH / 2,
            (this.y + yOffset - this.gameSession.getCameraY()) * this.gameSession.getCameraScale() + Main.WINDOW_HEIGHT / 2,
            this.gameSession.getCameraScale(),
            this.gameSession.getCameraScale()
        );
    }

    /**
     * Draws the tile to the graphics context.
     * @param gc
     * The graphics context you're drawing to.
     */
    public abstract void drawTile(GraphicsContext gc);

    public GameSession getGameSession() {
        return this.gameSession;
    }
}

