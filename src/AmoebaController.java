import java.util.ArrayList;
import java.util.Random;
public class AmoebaController {
    private final GameSession gameSession;
    private final ArrayList<AmoebaTile> amoebaChildren; //The list of children to this amoeba controller
    private int maxAmoebaChildCount; //The maximum amount of allowed children to this amoeba
    private int currentAmoebaChildCount; //The current number of children
    protected long lastTimeStamp;
    private final long operationInterval;
    private long operationIntervalsPerAmoebaGrowthRate; //how many operation intervals before the amoeba grows by one
    private int currentNumberOfIntervals; //how many intervals it's been since the amoeba has grown


    /**
     * Construct an amoeba controller which acts as the controller for an amoeba cluster, regulating growth and cluster members.
     * @param gameSession
     * The game session the amoeba cluster is a part of.
     * @param startingAmoebaX
     * The x position of the first amoeba in the cluster on the map.
     * @param startingAmoebaY
     * The y position of the first amoeba in the cluster on the map.
     * @param operationInterval
     * The time in ms between operations.
     * @param amoebaGrowthRatePerOperationInterval
     * The number of operation intervals between attempted amoeba cluster growths.
     * @param maxAmoebaChildCount
     * The maximum number of amoeba tiles in the cluster before diamond conversion triggers.
     */
    public AmoebaController(GameSession gameSession, int startingAmoebaX, int startingAmoebaY, long operationInterval, long amoebaGrowthRatePerOperationInterval, int maxAmoebaChildCount){
        this.gameSession = gameSession;
        this.amoebaChildren = new ArrayList<AmoebaTile>();
        if(maxAmoebaChildCount > 0){
            this.currentAmoebaChildCount = 0;
            addNewAmoebaChildToCluster(startingAmoebaX, startingAmoebaY);
        }
        this.maxAmoebaChildCount = maxAmoebaChildCount;
        this.operationInterval = operationInterval;
        this.operationIntervalsPerAmoebaGrowthRate = amoebaGrowthRatePerOperationInterval;
        this.currentNumberOfIntervals = 0;
        //TODO add a starting timestamp here
    }

    /**
     *If the amoeba cluster still contains tiles on the grid, regulate amoeba growth and check for diamond conversion trigger.
     * @param currentTimeInMilliseconds
     * The current time in milliseconds since 1970
     */
    public void updateAmoebaCluster(long currentTimeInMilliseconds){
        if(this.currentAmoebaChildCount == 0){//TODO replace this with a removal of this amoeba cluster from the arraylist in game session
            return;
        }

        if(this.currentAmoebaChildCount >= this.maxAmoebaChildCount){
            triggerDiamondConversion();
            return;
        }

        if(currentTimeInMilliseconds - this.lastTimeStamp >= this.operationInterval){
            this.currentNumberOfIntervals++;
            //TODO update the timestamp here
        }

        if(this.currentNumberOfIntervals >= this.operationIntervalsPerAmoebaGrowthRate){
            attemptAmoebaClusterGrowth(0);
        }
    }

    /**
     * Convert all amoeba tiles in this cluster into diamonds.
     */
    public void triggerDiamondConversion(){
        for(AmoebaTile amoeba : this.amoebaChildren){
            Diamond replacementDiamond = new Diamond(this.gameSession, amoeba.getXPosition(), amoeba.getYPosition(), this.operationInterval);
            this.gameSession.setTile(amoeba.getXPosition(), amoeba.getYPosition(), replacementDiamond);
        }

        this.currentAmoebaChildCount = 0;
    }

    /**
     * Randomly select and amoeba in the cluster to spread one tile (if possible).
     * @param numberOfGrowthAttempts
     * The number of attempts the amoeba has attempted to grow in the current growth attempt.
     */
    public void attemptAmoebaClusterGrowth(int numberOfGrowthAttempts){
        if(numberOfGrowthAttempts >= 100){ //change to (> number in cluster) this should be done by copying the array list and removing from list when once isn't possible
            return;
        }
        else{
            Random random = new Random();
            int randomIndex = random.nextInt(amoebaChildren.size());
            amoebaChildren.get(randomIndex).growThisAmoebaTile(numberOfGrowthAttempts);
        }
    }

    /**
     * Add a new amoeba to the cluster at a specified position.
     * @param x
     * The grid tile x position of the new amoeba tile.
     * @param y
     * The grid tile y position of the new amoeba tile.
     */
    public void addNewAmoebaChildToCluster(int x, int y){
        //Add a new amoeba to the array list
        AmoebaTile newAmoeba = new AmoebaTile(this.gameSession, x, y, this.operationInterval, this);//instantiate new amoeba
        this.amoebaChildren.add(newAmoeba);//add new amoeba to cluster

        //TODO: Verify this code
        //change it so it only spreads on a path, potentially making a new tiletype for paths

        if(this.gameSession.getTileFromGrid(x,y).tileType == TileType.PATH){
            // this.gameSession.callKillPlayer();
            // TODO: i don't think amoeba kills players?
            this.gameSession.setTile(newAmoeba.getYPosition(), newAmoeba.getXPosition(), newAmoeba);
        }

        this.currentAmoebaChildCount++;
    }

    /**
     * Remove amoeba from cluster and replace with an explosion.
     * @param x
     * The x position of the amoeba tile that is being removed from the cluster.
     * @param y
     * The y position of the amoeba tile that is being removed from the cluster.
     * @param explosion
     * The obj reference for the explosion tile that will replace the amoeba tile at it's position.
     */
    public void removeChildFromController(int x, int y, Tile explosion){
        for(AmoebaTile amoeba : this.amoebaChildren){
            if(amoeba.getXPosition() == x && amoeba.getYPosition() == y){
                this.gameSession.setTile(x, y, explosion);
                this.amoebaChildren.remove(amoeba);
                this.currentAmoebaChildCount--;
            }
        }
    }
}
