import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The teleport tile should allow the player (and only the player)
 * to teleport to its brother teleport tile on the map.
 * @author
 * Jace Weerawardena(crypticalfish86).
 * @version 1.0
 */
public class TeleportWall extends Wall{
    private int teleportWallID;
    private TeleportWall teleportWallBrother;
    private static final Image img = new Image("file:Assets/Images/TeleportationPortal.png");

    /**
     * Instantiates a new TeleportWall which allows the player to teleport to its brother TeleportWall tile.
     * @param gameSession
     * The game session associated with the tile.
     * @param x
     * The x position of the tile.
     * @param y
     * The y position of the tile.
     * @param tileType
     * The tile type.
     * @param operationInterval
     * The tiles operation interval.
     */
    public TeleportWall(GameSession gameSession, int x, int y, TileType tileType, long operationInterval, int teleportWallID){
        super(gameSession, x, y, tileType, operationInterval);
        this.teleportWallID = teleportWallID;
    }

    /**
     * Returns the teleport wall id.
     * @return
     * The teleport wall id.
     */
    public int getTeleportWallID() {
        return this.teleportWallID;
    }

    /**
     * Sets the other side of the teleport wall.
     * @param brother
     * The TeleportWall object that is related to this TeleportWall.
     */
    public void setTeleportWallBrother(TeleportWall brother){
        if(this.teleportWallBrother == null){
            this.teleportWallBrother = brother;
            this.teleportWallBrother.setTeleportWallBrother(this);
        }
    }

    /**
     * updates the tile with any frame by frame updates.
     * @param currentTimeInMilliseconds
     * The current time in milliseconds since the Unix Epoch (01/01/1970).
     */
    @Override
    public void updateTile(long currentTimeInMilliseconds) {

    }

    /**
     * If allows the player to teleport to
     * @param tile
     */
    @Override
    public void interact(Tile tile) {
        if (tile.getTileType() == TileType.PLAYER && this.teleportWallBrother != null){
            this.teleportWallBrother.teleportPlayer(tile);
        }
    }

    /**
     * Teleports a player to one of the sides of the teleport.
     * @param player
     */
    public void teleportPlayer(Tile player){
        System.out.println("attempting teleport");
        Tile tileToTeleportPlayerTo = null;
        if(this.gameSession.getTileFromGrid(this. x + 1, this.y).getTileType() == TileType.PATH){
            tileToTeleportPlayerTo = this.gameSession.getTileFromGrid(this.x + 1, this.y);
        } else if(this.gameSession.getTileFromGrid(this. x - 1, this.y).getTileType() == TileType.PATH){
            tileToTeleportPlayerTo = this.gameSession.getTileFromGrid(this.x - 1, this.y);
        } else if(this.gameSession.getTileFromGrid(this. x, this.y + 1).getTileType() == TileType.PATH){
            tileToTeleportPlayerTo = this.gameSession.getTileFromGrid(this.x, this.y + 1);
        } else if(this.gameSession.getTileFromGrid(this. x, this.y - 1).getTileType() == TileType.PATH){
            tileToTeleportPlayerTo = this.gameSession.getTileFromGrid(this.x, this.y - 1);
        }

        if(tileToTeleportPlayerTo != null){
            PathWall pathWall = new PathWall(this.gameSession, this.x, this.y, this.operationInterval);
            this.gameSession.updateTilePositions(pathWall, player, tileToTeleportPlayerTo);
        }

    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }


    /**
     * Returns a string tile representation of the teleport wall.
     * @return
     * The string tile representation of the teleport wall.
     */
    @Override
    public String returnStringTileRepresentation() {
        return "TE" + Integer.toString(this.teleportWallID);
    }
}
