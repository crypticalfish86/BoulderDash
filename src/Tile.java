import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Tile {
    protected GameSession gameSession;

    protected int x;
    protected int y;

    protected TileType tileType;
    protected long lastTimeStamp;
    protected long operationInterval;

    protected boolean amoebaCanSpreadToThisTile;


    public Tile(GameSession gameSession, int x, int y, TileType tileType, long operationInterval) {
        this.gameSession = gameSession;
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
    public long getOperationInterval(){return this.operationInterval;}
    public void setNewPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean amoebaCanSpreadToThisTile() {
        return this.amoebaCanSpreadToThisTile;
    }
    public abstract void interact(Tile Tile);
    public abstract void updateTile(long currentTimeInMilliseconds);

    //method used for drawing the icon
    protected void draw(GraphicsContext gc, Image img, int xOffset, int yOffset) {
        gc.drawImage(img,
            (this.x + xOffset - this.gameSession.cameraX) * this.gameSession.gridScale + Main.WINDOW_WIDTH / 2,
            (this.y + yOffset - this.gameSession.cameraY) * this.gameSession.gridScale + Main.WINDOW_HEIGHT / 2,
            this.gameSession.gridScale,
            this.gameSession.gridScale
        );
    }

    public abstract void drawTile(GraphicsContext gc);

    public GameSession getGameSession() {
        return this.gameSession;
    }
}