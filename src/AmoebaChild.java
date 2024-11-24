import java.util.Random;

public class AmoebaChild extends AmoebaOrigin{

    private AmoebaOrigin originOfThisChildAmoeba;

    public AmoebaChild(GameSession gameSession, int x, int y, TileType TileType, long operationInterval, long amoebaGrowthRatePerOperationInterval, int maxAmoebaChildCount, AmoebaOrigin originOfThisChildAmoeba) {
        super(gameSession, x, y, TileType, operationInterval, amoebaGrowthRatePerOperationInterval,maxAmoebaChildCount);
        this.originOfThisChildAmoeba = originOfThisChildAmoeba;
        this.tileType = TileType.AMOEBA;
        this.amoebaCanSpreadToThisTile = false;

    }

    //TODO, this is kind of ugly to have an overridden empty method, if you have time come back and turn this into something else.
    /**
     * Overriden method to prevent the parent method being called which would turn this amoeba into diamonds prematurely
     * @param currentTimeInMilliseconds
     * The how many milliseconds it's been since 01/01/1970.
     */
    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        return;
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
        AmoebaChild newNeighbouringAmoeba = new AmoebaChild(this.gameSession, x, y, TileType.AMOEBA, this.operationInterval, this.amoebaGrowthRatePerOperationInterval,this.maxAmoebaChildCount, this.originOfThisChildAmoeba);
        this.directAmoebaNeighbours.add(newNeighbouringAmoeba);
        if(this.gameSession.getTileFromGrid(x,y).tileType == TileType.PLAYER){
            this.gameSession.callKillPlayer();
        }
        this.gameSession.setTile(newNeighbouringAmoeba.getYPosition(), newNeighbouringAmoeba.getXPosition(), newNeighbouringAmoeba);
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
