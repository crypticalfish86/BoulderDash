import java.util.Random;

public class AmoebaChild extends AmoebaOrigin{

    AmoebaOrigin originOfThisChildAmoeba;
    public AmoebaChild(GameSession gameSession, int x, int y, TileType TileType, long operationInterval, long amoebaGrowthRatePerOperationInterval, AmoebaOrigin amoebaOrigin) {
        super(gameSession, x, y, TileType, operationInterval, amoebaGrowthRatePerOperationInterval);
        this.tileType = TileType.AMOEBA;
        this.amoebaCanSpreadToThisTile = false;
        this.originOfThisChildAmoeba = amoebaOrigin;
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
        AmoebaChild newNeighbouringAmoeba = new AmoebaChild(thisTilesGamesession, x, y, TileType.AMOEBA, this.operationInterval, this.amoebaGrowthRatePerOperationInterval, this.originOfThisChildAmoeba);
        this.directAmoebaNeighbours.add(newNeighbouringAmoeba);
        if(this.thisTilesGamesession.getTileFromGrid(x,y).tileType == TileType.PLAYER){
            this.thisTilesGamesession.callKillPlayer();
        }
        this.thisTilesGamesession.setTile(newNeighbouringAmoeba.getYPosition(), newNeighbouringAmoeba.getXPosition(), newNeighbouringAmoeba);
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
