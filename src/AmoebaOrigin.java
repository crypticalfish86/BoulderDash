import java.util.ArrayList;
import java.util.Random;

//class Author: Jace
public class AmoebaOrigin extends Tile{
    protected final long amoebaGrowthRatePerOperationInterval; //how many operation intervals before the amoeba grows by one
    private int currentNumberOfIntervals; //how many intervals it's been since the amoeba has grown

    private final int maxAmoebaChildCount;
    private int amoebaChildCount;

    protected final ArrayList<AmoebaChild> directAmoebaNeighbours;
    public AmoebaOrigin(GameSession gameSession, int x, int y, TileType TileType, long operationInterval, long amoebaGrowthRatePerOperationInterval, int maxAmoebaChildCount) {
        super(gameSession, x, y, TileType, operationInterval);
        this.tileType = TileType.AMOEBA;
        this.maxAmoebaChildCount = maxAmoebaChildCount;
        this.amoebaChildCount = 0;
        this.directAmoebaNeighbours = new ArrayList<AmoebaChild>();
        this.amoebaGrowthRatePerOperationInterval = amoebaGrowthRatePerOperationInterval;
        this.amoebaCanSpreadToThisTile = false;
    }

    /**
     * This tile is un-interactable unless it's being blown up.
     * @param tile
     * The tile that is attempting to interact with this tile.
     */
    public void interact(Tile tile){
        if(tile.getTileType() == TileType.EXPLOSION){
            this.gameSession.setTile(this.x,this.y, tile);
        }
    }

    /**
     * Regulate the growth of the amoeba and convert amoebas to diamonds if max count is reached
     * @param currentTimeInMilliseconds
     * The how many milliseconds it's been since 01/01/1970.
     */
    public void updateTile(long currentTimeInMilliseconds){
        if(amoebaChildCount >= maxAmoebaChildCount){
            for(AmoebaChild amoebaChild : this.directAmoebaNeighbours){
                amoebaChild.triggerDiamondConversion();
            }
            triggerDiamondConversion();
        }
        if(lastTimeStamp - currentTimeInMilliseconds >= operationInterval){
            this.currentNumberOfIntervals++;
        }


        if(currentNumberOfIntervals >= amoebaGrowthRatePerOperationInterval) {
            growAmoeba(0);
            this.currentNumberOfIntervals = 0;
        }
    }

    /**
     * Turn this amoeba tile into a diamond.
     */
    public void triggerDiamondConversion(){
        this.gameSession.setTile(this.x,this.y,new Diamond(this.gameSession, this.x,this.y, TileType.FALLING_OBJECT, this.operationInterval));
    }

    /**
     * Grow the Amoeba to surrounding tiles and if impossible pass that growth command to a neighbouring amoeba.
     * @param numberOfReturnsToOrigin
     * How many times the amoeba has attempted to grow in within this operation interval, if larger than 100 growth
     * is most likely not possible (small case where we got extremely unlucky, and it was possible)
     */
    public void growAmoeba(int numberOfReturnsToOrigin){
        if(numberOfReturnsToOrigin >= 100){
            return;
        }
        Tile northTile = gameSession.getTileFromGrid(x,y + 1);
        Tile eastTile = gameSession.getTileFromGrid(x + 1, y);
        Tile southTile = gameSession.getTileFromGrid(x, y - 1);
        Tile westTile = gameSession.getTileFromGrid(x - 1, y);

        if(northTile.amoebaCanSpreadToThisTile()) {
            setNewAmoebaToNeighbouringTile(x, y + 1);
        }
        else if(eastTile.amoebaCanSpreadToThisTile()) {
            setNewAmoebaToNeighbouringTile(x + 1, y);
        }
        else if(southTile.amoebaCanSpreadToThisTile()){
            setNewAmoebaToNeighbouringTile(x, y - 1);
        }
        else if(westTile.amoebaCanSpreadToThisTile()){
            setNewAmoebaToNeighbouringTile(x - 1, y);
        }
        else{
            passGrowthToRandomNeighbouringAmoeba(numberOfReturnsToOrigin);
        }
    }

    /**
     * Helper function to spread the amoeba to a new tile in the grid.
     * @param x
     * The x position of where in the grid you're spreading the amoeba to.
     * @param y
     * The y position of where in the grid you're spreading the amoeba to.
     */
    protected void setNewAmoebaToNeighbouringTile(int x, int y){
        AmoebaChild newNeighbouringAmoeba = new AmoebaChild(this.gameSession, x, y, TileType.AMOEBA, this.operationInterval, this.amoebaGrowthRatePerOperationInterval, this);
        this.directAmoebaNeighbours.add(newNeighbouringAmoeba);
        if(this.gameSession.getTileFromGrid(x,y).tileType == TileType.PLAYER){
            this.gameSession.callKillPlayer();
        }
        this.gameSession.setTile(newNeighbouringAmoeba.getYPosition(), newNeighbouringAmoeba.getXPosition(), newNeighbouringAmoeba);
        this.incrementAmoebaChildCount();
    }

    /**
     * Randomly pass the growth of the amoeba to one of the direct neighbours of the amoeba.
     * @param numberOfReturnsToOrigin
     * How many times the amoeba has attempted to grow in within this operation interval, if larger than 100 growth
     * is most likely not possible.
     */
    protected void passGrowthToRandomNeighbouringAmoeba(int numberOfReturnsToOrigin){
        if(this.directAmoebaNeighbours.isEmpty()){
            return;
        }
        else{
            Random random = new Random();
            int randomIndex = random.nextInt(directAmoebaNeighbours.size());
            directAmoebaNeighbours.get(randomIndex).growAmoeba(numberOfReturnsToOrigin);
        }
    }

    /**
     * Increment the amoeba child count by one.
     */
    public void incrementAmoebaChildCount(){
        this.amoebaChildCount++;
    }
}
