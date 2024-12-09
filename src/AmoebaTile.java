import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The AmoebaTile is an actual tile on the map that is part of the cluster,
 * regulated through the AmoebaController class.
 * @author
 * Jace Weerawardena (Crypticalfish86).
 * @version 2.0
 */
public class AmoebaTile extends Tile{

    private static final Image img = new Image("file:Assets/Images/Ameoba.png");
    private final AmoebaController thisAmoebaTilesController;

    /**
     * Construct a single amoeba tile that is part of a greater amoeba cluster.
     * @param gameSession
     * The game session the amoeba tile is a part of.
     * @param x
     * The x position of the amoeba tile.
     * @param y
     * The y position of the amoeba tile.
     * @param operationInterval
     * The time in ms between operations.
     * @param thisAmoebaTilesController
     * Obj reference to the amoeba cluster controller that this amoeba tile is a part of.
     */
    public AmoebaTile(
            GameSession gameSession,
            int x,
            int y,
            long operationInterval,
            AmoebaController thisAmoebaTilesController)
    {
        super(gameSession, x, y,TileType.AMOEBA, operationInterval);
        this.thisAmoebaTilesController = thisAmoebaTilesController;
    }


    /**
     * This tile is un-interactable unless it's being blown up.
     * @param tile
     * The tile that is attempting to interact with this tile.
     */
    public void interact(Tile tile){
        if(tile.getTileType() == TileType.EXPLOSION){
            this.thisAmoebaTilesController.removeChildFromController(this.x,this.y, tile);
        }
    }


    /**
     * Perform any time based updates to the tile.
     * @param currentTimeInMilliseconds
     * The current time in ms since 01/01/1970.
     */
    public void updateTile(long currentTimeInMilliseconds) {

    }

    /**
     * Try if this amoeba can grow to its neighbour.
     * @return if this growth was successful.
     */
    public boolean tryGrow() {
        boolean canSpreadNorth =
                AmoebaController.canAmoebaSpreadTo(
                        gameSession.getTileFromGrid(x, y + 1).getTileType()
                );
        boolean canSpreadSouth =
                AmoebaController.canAmoebaSpreadTo(
                        gameSession.getTileFromGrid(x, y - 1).getTileType()
                );
        boolean canSpreadEast =
                AmoebaController.canAmoebaSpreadTo(
                        gameSession.getTileFromGrid(x + 1, y).getTileType()
                );
        boolean canSpreadWest =
                AmoebaController.canAmoebaSpreadTo(
                        gameSession.getTileFromGrid(x - 1, y).getTileType()
                );

        //up
        if (canSpreadNorth) {
            this.thisAmoebaTilesController.addNewAmoebaChildToCluster(x, y + 1);
            return true;
        }

        //down
        if (canSpreadSouth) {
            this.thisAmoebaTilesController.addNewAmoebaChildToCluster(x, y - 1);
            return true;
        }

        //right
        if (canSpreadEast) {
            this.thisAmoebaTilesController.addNewAmoebaChildToCluster(x + 1, y);
            return true;
        }

        //left
        if (canSpreadWest) {
            this.thisAmoebaTilesController.addNewAmoebaChildToCluster(x - 1, y);
            return true;
        }

        return false;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }

    /**
     * Return a string representation of the amoeba tile.
     * @return
     * The string representation of the amoeba tile.
     */
    public String returnStringTileRepresentation(){
        return "A" + Integer.toString(this.thisAmoebaTilesController.getClusterID());
    }

}
