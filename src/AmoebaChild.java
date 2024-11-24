import java.util.Random;

public class AmoebaChild extends AmoebaOrigin{

    private AmoebaOrigin originOfThisChildAmoeba;

    //TODO possibly change the maxAmoebaChildCount from 0 to whatever is specified in the level file format
    public AmoebaChild(GameSession gameSession, int x, int y, TileType tileType, long operationInterval, long amoebaGrowthRatePerOperationInterval, AmoebaOrigin originOfThisChildAmoeba) {
        super(gameSession, x, y, tileType, operationInterval, amoebaGrowthRatePerOperationInterval,0);
        this.originOfThisChildAmoeba = originOfThisChildAmoeba;
        this.tileType = TileType.AMOEBA;
        this.amoebaCanSpreadToThisTile = false;

    }

    //TODO possibly remove this once the maxAmoebaChildCount from the level file format is fixed
    /**
     * Overriden method to prevent the parent method being called which would turn this amoeba into diamonds
     * @param currentTimeInMilliseconds
     * The how many milliseconds it's been since 01/01/1970.
     */
    @Override
    public void updateTile(long currentTimeInMilliseconds) {

    }

    /**
     * Helper function to spread the amoeba to a new tile in the grid, overridden to have a constant AmoebaOrigin.
     * @param x
     * The x position of where in the grid you're spreading the amoeba to.
     * @param y
     * The y position of where in the grid you're spreading the amoeba to.
     */
    @Override
    protected void setNewAmoebaToNeighbouringTile(int x, int y){
        AmoebaChild newNeighbouringAmoeba = new AmoebaChild(gameSession, x, y, TileType.AMOEBA, this.operationInterval, this.amoebaGrowthRatePerOperationInterval, this.originOfThisChildAmoeba);
        this.directAmoebaNeighbours.add(newNeighbouringAmoeba);
        if(gameSession.getTileFromGrid(x,y).tileType == TileType.PLAYER){
            gameSession.callKillPlayer();
        }
        gameSession.setTile(newNeighbouringAmoeba.getYPosition(), newNeighbouringAmoeba.getXPosition(), newNeighbouringAmoeba);
        this.originOfThisChildAmoeba.incrementAmoebaChildCount();
    }

    /**
     * Randomly pass the growth of the amoeba to one of the direct neighbours of the amoeba, if no neighbours then attempt growth from origin again incrementing the number of attempts.
     * @param numberOfReturnsToOrigin
     *How many times the amoeba has attempted to grow within this operation interval.
     */
    @Override
    protected void passGrowthToRandomNeighbouringAmoeba(int numberOfReturnsToOrigin){
        if(this.directAmoebaNeighbours.isEmpty()){
            this.originOfThisChildAmoeba.growAmoeba(numberOfReturnsToOrigin + 1);
        }
        else{
            Random random = new Random();
            int randomIndex = random.nextInt(directAmoebaNeighbours.size());
            directAmoebaNeighbours.get(randomIndex).growAmoeba(numberOfReturnsToOrigin);
        }
    }
}
