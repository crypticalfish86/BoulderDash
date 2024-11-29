import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class AmoebaTile extends Tile{

    public static final Image img = new Image("file:Assets/Images/Ameoba.png"); // Placeholder for the image
    private AmoebaController thisAmoebaTilesController;

    public AmoebaTile(GameSession gameSession, int x, int y, long operationInterval, AmoebaController thisAmoebaTilesController){
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


    public void updateTile(long currentTimeInMilliseconds) {
        return;
    }

    //TODO make it randomly spread to a tile instead of always starting with a north tile then east then south etc.
    public void growThisAmoebaTile(int numberOfGrowthAttempts){
        Tile northTile = gameSession.getTileFromGrid(x,y + 1);
        Tile eastTile = gameSession.getTileFromGrid(x + 1, y);
        Tile southTile = gameSession.getTileFromGrid(x, y - 1);
        Tile westTile = gameSession.getTileFromGrid(x - 1, y);

        if(northTile.amoebaCanSpreadToThisTile()) {
            this.thisAmoebaTilesController.addNewAmoebaChildToCluster(x, y + 1);
        }
        else if(eastTile.amoebaCanSpreadToThisTile()) {
            this.thisAmoebaTilesController.addNewAmoebaChildToCluster(x + 1, y);
        }
        else if(southTile.amoebaCanSpreadToThisTile()){
            this.thisAmoebaTilesController.addNewAmoebaChildToCluster(x, y - 1);
        }
        else if(westTile.amoebaCanSpreadToThisTile()){
            this.thisAmoebaTilesController.addNewAmoebaChildToCluster(x - 1, y);
        }
        else{
            this.thisAmoebaTilesController.attemptAmoebaClusterGrowth(numberOfGrowthAttempts + 1);
        }
    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }

}
