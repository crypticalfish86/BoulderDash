import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class GameSession {

    private int gridHeight; //The height of the grid
    private int gridWidth; //The width of the grid

    private long startTimeStamp; //The starting time stamp (ms since 1970)
    private long maxTimeToCompleteLevel; //The time given to complete level (in ms)

    private Tile[][] gridTileMap; //The full 2D grid instantiated on the interpretation of the level data
    private final Game game; // Reference to the game that the current game session is attached to
    private final GameSessionData currentSessionData; //Reference to this games' game session data
    private final Player player; //Reference to the current single game player (inserted into the level in "interpretLevelData"



    private final Canvas canvas;
    private final GraphicsContext gc;


    public static final int GRID_SIZE = 50;

    private boolean isGamePaused = false;




    private static final String ESCAPE_KEYCODE = "Escape";

    GameSession(Game game, String gameData, Pane gamePane) {
        this.game = game;
        this.currentSessionData = new GameSessionData(this);
        this.player = new Player(this, 10, 10, 10); // TODO: change the values
        interpretLevelData(gameData);







        int mapSizeX = 10;
        int mapSizeY = 10;// TODO: implement these

        this.gridHeight = mapSizeY;
        this.gridWidth = mapSizeX;


        this.canvas = new Canvas(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        this.gc = canvas.getGraphicsContext2D();

        gamePane.getChildren().addAll(canvas);
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
        player.killPlayer();
    }

    //Updates every tile in the game
    private void updateGame(long currentTimeInMilliseconds){
        //TODO: change it so that draw and update is independent of each other
        
        
        if (isGamePaused) {
            //TODO: call the tiles to be drawn
            //TODO: call the menu to be drawn

            
        } else {



            for(Tile[] tileColumn : gridTileMap) {
                for(Tile tile : tileColumn) {
                    //TODO call updateTile for every tile in here
                    
    
                }
            }

            //TODO: call the tiles to be drawn
        }
    }

    public int getGridWidth() {
        return this.gridWidth;
    }

    public int getGridHeight() {
        return this.gridHeight;
    }

    //TODO determine a method of input before implementing
    public void onInput(){ return; }

    public GraphicsContext gc() {
        return this.gc;
    }

    public void onKeyPressed(String key) {
        if (key == ESCAPE_KEYCODE) { //TODO

            //TODO: open menu
            isGamePaused = true;
            return;
        }


        if (isGamePaused) {

            //TODO: direct inputs to the pausing menu
        } else {

            player.onKeyPressed(key);
        }
    }

    public void onKeyReleased(String key) {
        if (key == ESCAPE_KEYCODE) { //TODO

            //TODO: close menu
            isGamePaused = false;
            return;
        }


        if (isGamePaused) {
            
            //TODO: direct inputs to the pausing menu
        } else {

            player.onKeyReleased(key);
        }
    }

    // function to be fired when the menu close button is clicked
    public void onMenuClosed() {
        this.isGamePaused = false;

    }
}
