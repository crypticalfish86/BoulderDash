import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The Tile class represents every type of tile that will be on the grid tile map.
 * @author Jace Weerawardena (crypticalfish86).
 * @version 2.0
 */
public abstract class Tile {
    protected GameSession gameSession;

    protected int x;
    protected int y;

    protected TileType tileType;
    protected long lastTimeStamp;
    protected long operationInterval;


    /**
     * Instantiates a Tile which represents every type of tile that will be on the grid tile map.
     * @param gameSession
     * The game session associated with this tile.
     * @param x
     * The tiles x position on the grid tile map.
     * @param y
     * The tiles y position on the grid tile map.
     * @param tileType
     * The tiles "tile type".
     * @param operationInterval
     * The operation interval between updates of this tile.
     */
    public Tile(GameSession gameSession, int x, int y, TileType tileType, long operationInterval) {
        this.gameSession = gameSession;
        this.x = x;
        this.y = y;
        this.tileType = tileType;
        //TODO add a starting timestamp here
    }

    /**
     * Return the type of tile this tile is.
     * @return
     * The type of tile this tile is.
     */
    public TileType getTileType() {
        return this.tileType;
    }

    /**
     * Return the x position of the tile.
     * @return
     * The x position of the tile.
     */
    public int getXPosition() {
        return this.x;
    }

    /**
     * Return the y position of the tile.
     * @return
     * The y position of the tile.
     */
    public int getYPosition() {
        return this.y;
    }

    /**
     * Return the operation interval of the tile.
     * @return
     * The operation interval of the tile.
     */
    public long getOperationInterval(){return this.operationInterval;}

    /**
     * update the x and y position of the tile.
     * @param x
     * The new x position of the tile.
     * @param y
     * The new y position of the tile.
     */
    public void setNewPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Runs blocks of code depending on the tile that is interacting with it.
     * @param Tile
     * The tile that is interacting with this tile.
     */
    public abstract void interact(Tile Tile);

    /**
     * Perform any time based updates of the tile every operation interval.
     * @param currentTimeInMilliseconds
     * The current time in milliseconds since the unix epoch (01/01/1970).
     */
    public abstract void updateTile(long currentTimeInMilliseconds);

    /**
     * Return a string tile representation of the tile to build a savestate for the map.
     * @return
     * The string tile representation of this tile.
     */
    public abstract String returnStringTileRepresentation();

    /**
     * draws the specified image on the given graphics context, with respect to the tile position.
     * @param gc
     * The graphics context.
     * @param img
     * The image we're drawing.
     * @param xOffset
     * x offset of the image in pixels.
     * @param yOffset
     * y offset of the image in pixels.
     */
    protected void draw(GraphicsContext gc, Image img, int xOffset, int yOffset) {
        gc.drawImage(img,
            (this.x + xOffset - this.gameSession.getCameraX()) *
                    this.gameSession.getCameraScale() + Main.WINDOW_WIDTH / 2,
            (this.y + yOffset - this.gameSession.getCameraY()) *
                    this.gameSession.getCameraScale() + Main.WINDOW_HEIGHT / 2,
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

    /**
     * Returns the GameSession associated with this tile.
     * @return
     * The GameSession associated with this tile.
     */
    public GameSession getGameSession() {
        return this.gameSession;
    }
}

