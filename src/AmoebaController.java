import java.util.ArrayList;
import java.util.Random;

/**
 * The AmoebaController class regulates an amoeba cluster of AmoebaTile's on the board.
 * @author
 * Jace Weerawardena(crypticalfish86).
 * @version 2.0
 */
public class AmoebaController {
    private final GameSession gameSession;
    private final ArrayList<AmoebaTile> amoebaChildren; //The list of children to this cluster
    private int maxAmoebaChildCount; //The maximum amount of allowed children to this amoeba
    private int currentAmoebaChildCount; //The current number of children
    protected long lastTimeStamp;
    private final long operationInterval;
    private long operationIntervalsPerAmoebaGrowthRate; //how many operation intervals per growth
    private int currentNumberOfIntervals; //how many intervals it's been since the amoeba has grown


    private static final int AMOEBA_ATTEMPTS = 10;
    private int currentAttempts = 0;

    private int clusterID;


    /**
     * Construct an amoeba controller which acts as the controller for an amoeba cluster,
     * regulating growth and cluster members.
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
    public AmoebaController(
            GameSession gameSession,
            int startingAmoebaX,
            int startingAmoebaY,
            long operationInterval,
            long amoebaGrowthRatePerOperationInterval,
            int maxAmoebaChildCount,
            int clusterID
    ){
        this.gameSession = gameSession;
        this.amoebaChildren = new ArrayList<AmoebaTile>();
        this.clusterID = clusterID;

        if(maxAmoebaChildCount > 0){
            this.currentAmoebaChildCount = 0;
            addNewAmoebaChildToCluster(startingAmoebaX, startingAmoebaY);
        }

        this.maxAmoebaChildCount = maxAmoebaChildCount;
        this.operationInterval = operationInterval;
        this.operationIntervalsPerAmoebaGrowthRate = amoebaGrowthRatePerOperationInterval;
        this.currentNumberOfIntervals = 0;
    }

    /**
     * Gets the clusterID of the amoeba cluster (an int between 1-9).
     * @return
     * The amoeba clusterID.
     */
    public int getClusterID() {
        return this.clusterID;
    }

    /**
     *If the amoeba cluster still contains tiles on the grid, regulate amoeba growth and check
     *  for diamond conversion trigger.
     * @param currentTimeInMilliseconds
     * The current time in milliseconds since the unix epoch (01/01/1970).
     */
    public void updateAmoebaCluster(long currentTimeInMilliseconds){

        if (this.currentAmoebaChildCount == 0) {
            stopController();
            return;
        }

        if (this.currentAmoebaChildCount >= this.maxAmoebaChildCount) {
            convertToBoulder();
            return;
        }

        if (currentTimeInMilliseconds - this.lastTimeStamp >= this.operationInterval) {
            this.lastTimeStamp = currentTimeInMilliseconds;

            ++(this.currentNumberOfIntervals);

            if (this.currentNumberOfIntervals >= this.operationIntervalsPerAmoebaGrowthRate) {
                attemptAmoebaClusterGrowth();
                this.currentNumberOfIntervals = 0;
            }
        }

        
    }

    /**
     * Convert all amoeba tiles in this cluster into diamonds.
     */
    public void convertToDiamond() {
        for (AmoebaTile amoeba : this.amoebaChildren) {
            Diamond replacementDiamond =
                    new Diamond(
                            this.gameSession,
                            amoeba.getXPosition(),
                            amoeba.getYPosition(),
                            this.operationInterval,
                            false
                    );
            this.gameSession.setTile(
                    amoeba.getYPosition(),
                    amoeba.getXPosition(),
                    replacementDiamond
            );
        }

        this.currentAmoebaChildCount = 0;
    }

    /**
     * Convert all amoeba tiles in this cluster into boulder.
     */
    public void convertToBoulder() {
        for (AmoebaTile amoeba : this.amoebaChildren) {
            Boulder replacementBoulder =
                    new Boulder(
                            this.gameSession,
                            amoeba.getXPosition(),
                            amoeba.getYPosition(),
                            this.operationInterval,
                            false
                    );

            this.gameSession.setTile(amoeba.getYPosition(),
                    amoeba.getXPosition(),
                    replacementBoulder
            );
        }

        this.currentAmoebaChildCount = 0;
    }

    /**
     * Randomly select and amoeba in the cluster to spread one tile (if possible),
     * converts to diamond if it hasn't been able to grow for too long.
     */
    public void attemptAmoebaClusterGrowth() {

        if (this.currentAttempts > AMOEBA_ATTEMPTS) {
            convertToDiamond();
            return;
        }
        
        
        for (int i = 0; i < amoebaChildren.size(); ++i) {
            if (amoebaChildren.get(i).tryGrow()) {

                this.currentAttempts = 0;
                return;
            }
        }

        ++(this.currentAttempts);
    }

    /**
     * Add a new amoeba to the cluster at a specified position.
     * @param x
     * The grid tile x position of the new amoeba tile.
     * @param y
     * The grid tile y position of the new amoeba tile.
     */
    public void addNewAmoebaChildToCluster(int x, int y) {

        AmoebaTile newAmoeba =
                new AmoebaTile(
                        this.gameSession,
                        x,
                        y,
                        this.operationInterval,
                        this
                );
        this.amoebaChildren.add(newAmoeba);//add new amoeba to cluster
        this.gameSession.setTile(newAmoeba.getYPosition(), newAmoeba.getXPosition(), newAmoeba);
        
        this.currentAmoebaChildCount++;

    }

    /**
     * Remove amoeba from cluster and replace with an explosion.
     * @param x
     * The x position of the amoeba tile that is being removed from the cluster.
     * @param y
     * The y position of the amoeba tile that is being removed from the cluster.
     * @param explosion
     * The obj reference for the explosion tile that will replace the amoeba tile at its position.
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




    /**
     * Removes the current controller from the game.
     */
    private void stopController() {
        gameSession.getAmoebaControllerList().remove(this);
    }


    /**
     * Check if the amoeba can spread to a neighbouring tile.
     * @param tileType
     * The neighbouring tile.
     * @return
     * True if the tile can be spread to, false otherwise.
     */
    public static boolean canAmoebaSpreadTo(TileType tileType) {
        return (
            tileType == TileType.PATH ||
            tileType == TileType.DIRT_WALL ||
            tileType == TileType.MOVING_ENEMY
        );
    }
}
