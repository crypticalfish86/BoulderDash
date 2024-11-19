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
        return;
    }

    //returns a specific tile from the gridTileMap
    public Tile getTileFromGrid(int x, int y) {
        return gridTileMap[y][x];
    }

    //updates the incoming tile position with the replacement tile and the outgoing tile with the incoming tile
    public void updateTilePositions(Tile replacementTile, Tile incomingTile, Tile outgoingTile) {
        return;
    }

    //Updates every tile in the game
    private void updateGame(long currentTimeInMilliseconds){
        for(Tile[] tileColumn : gridTileMap) {
            for(Tile tile : tileColumn) {
                //call updateTile for every tile in here
            }
        }
        return;
    }

    //TODO determine a method of input before implementing
    public void onInput(){return;}

}
