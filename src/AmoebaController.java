import java.util.ArrayList;
import java.util.Random;
//TODO write javadoc
public class AmoebaController {
    private GameSession gameSession;
    private final ArrayList<AmoebaTile> amoebaChildren; //The list of children to this amoeba controller
    private final int maxAmoebaChildCount; //The maximum amount of allowed children to this amoeba
    private int currentAmoebaChildCount; //The current number of children
    protected long lastTimeStamp;
    private long operationInterval;
    private final long operationIntervalsPerAmoebaGrowthRate; //how many operation intervals before the amoeba grows by one
    private int currentNumberOfIntervals; //how many intervals it's been since the amoeba has grown


    /**
     *
     * @param gameSession
     * @param startingAmoebaX
     * @param startingAmoebaY
     * @param operationInterval
     * @param amoebaGrowthRatePerOperationInterval
     * @param maxAmoebaChildCount
     */
    public AmoebaController(GameSession gameSession, int startingAmoebaX, int startingAmoebaY, long operationInterval, long amoebaGrowthRatePerOperationInterval, int maxAmoebaChildCount){
        this.gameSession = gameSession;
        this.amoebaChildren = new ArrayList<AmoebaTile>();
        if(maxAmoebaChildCount > 0){
            this.amoebaChildren.add(new AmoebaTile(this.gameSession, startingAmoebaX, startingAmoebaY, operationInterval, this));
            this.currentAmoebaChildCount = 1;
        }
        this.maxAmoebaChildCount = maxAmoebaChildCount;
        this.operationInterval = operationInterval;
        this.operationIntervalsPerAmoebaGrowthRate = amoebaGrowthRatePerOperationInterval;
        this.currentNumberOfIntervals = 0;
        //TODO add a starting timestamp here
    }

    /**
     *
     * @param currentTimeInMilliseconds
     */
    public void updateAmoebaCluster(long currentTimeInMilliseconds){
        if(this.currentAmoebaChildCount == 0){//TODO replace this with a removal of this amoeba cluster from the arraylist in game session
            return;
        }

        //check if maxamoeba count has reached and if so trigger diamond conversion then break this function
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

    public void triggerDiamondConversion(){
        for(AmoebaTile amoeba : this.amoebaChildren){
            Diamond replacementDiamond = new Diamond(this.gameSession, amoeba.getXPosition(), amoeba.getYPosition(), this.operationInterval);
            this.gameSession.setTile(amoeba.getXPosition(), amoeba.getYPosition(), replacementDiamond);
        }

        this.currentAmoebaChildCount = 0;
    }

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
     * Add a new amoeba to the cluster
     * @param x
     *
     * @param y
     *
     */
    public void addNewAmoebaChildToCluster(int x, int y){
        //Add a new amoeba to the array list
        AmoebaTile newAmoeba = new AmoebaTile(this.gameSession, x, y, this.operationInterval, this);//instantiate new amoeba
        this.amoebaChildren.add(newAmoeba);//add new amoeba to cluster
        if(this.gameSession.getTileFromGrid(x,y).tileType == TileType.PLAYER){
            this.gameSession.callKillPlayer();
        }
        this.gameSession.setTile(newAmoeba.getYPosition(), newAmoeba.getXPosition(), newAmoeba);

        this.currentAmoebaChildCount++;
    }

    /**
     * Remove amoeba from cluster and replace with an explosion
     * @param x
     * @param y
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
