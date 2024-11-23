public class GameSession {

    private int gridHeight; //The height of the grid
    private int gridWidth; //The width of the grid

    private long startTimeStamp; //The starting time stamp (ms since 1970)
    private long maxTimeToCompleteLevel; //The time given to complete level (in ms)

    private Tile[][] gridTileMap; //The full 2D grid instantiated on the interpretation of the level data
    private final Game game; // Reference to the game that the current game session is attached to
    private final GameSessionData currentSessionData; //Reference to this games' game session data
    private final Player gamePlayerTile; //Reference to the current single game player (inserted into the level in "interpretLevelData"
    GameSession(Game game, String gameData) {
        this.game = game;
        this.currentSessionData = new GameSessionData();
        this.gamePlayerTile = new Player();
        interpretLevelData(gameData);
    }

    /*
        method used to interpret the gameData (from a levelFileFormat file) fills the gridTileMap, initiates timer,
        determine the
    */
    private void interpretLevelData(String gameData) {
        //TODO implement a function  to interpret level data
        return;
    }

    //returns a specific tile from the gridTileMap
    public Tile getTileFromGrid(int x, int y) {
        return gridTileMap[y][x];
    }

    //updates the incoming tile position with the replacement tile and the outgoing tile with the incoming tile

    /**
     * move a tile to another tile in the gridmap and replace where the tile was with another tile.
     * @param replacementTile
     * The tile that will replace the current incoming tiles location.
     * @param incomingTile
     * The incoming tile that will be moved to the outgoing tiles location.
     * @param outgoingTile
     * The outgoing tile that is being deleted.
     */
    public void updateTilePositions(Tile replacementTile, Tile incomingTile, Tile outgoingTile) {
        setTile(incomingTile.getYPosition(),incomingTile.getXPosition(), replacementTile);
        setTile(outgoingTile.getYPosition(),outgoingTile.getXPosition(), incomingTile);
    }

    /**
     * Set a new or existing tile at a specific location in the gridmap.
     * @param yTileLocation
     * The y location in the gridmap you want to set a new tile for.
     * @param xTileLocation
     * The x location in the gridmap you want to set a new tile for.
     * @param tile
     * The tile that will be set at this location.
     */
    public void setTile(int yTileLocation, int xTileLocation, Tile tile){
        gridTileMap[yTileLocation][xTileLocation] = tile;
    }

    /**
     * Calls the kill player method on this game session's player.
     */
    public void callKillPlayer(){
        gamePlayerTile.killPlayer();
    }

    //Updates every tile in the game
    private void updateGame(long currentTimeInMilliseconds){
        for(Tile[] tileColumn : gridTileMap) {
            for(Tile tile : tileColumn) {
                //TODO call updateTile for every tile in here
            }
        }
        return;
    }

    public int getGridWidth() {
        return this.gridWidth;
    }

    public int getGridHeight() {
        return this.gridHeight;
    }

    //TODO determine a method of input before implementing
    public void onInput(){return;}

}
