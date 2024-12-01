import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class GameSession {

    private int gridHeight; //The height of the grid
    private int gridWidth; //The width of the grid

    private GraphicsContext graphicsContext; // Store the GraphicsContext


    private long startTimeStamp; //The starting time stamp (ms since 1970)
    private long maxTimeToCompleteLevel; //The time given to complete level (in ms)

    private Tile[][] gridTileMap; //The full 2D grid instantiated on the interpretation of the level data
    private final Game game; // Reference to the game that the current game session is attached to
    private final GameSessionData currentSessionData; //Reference to this games' game session data
    private final Player player; //Reference to the current single game player (inserted into the level in "interpretLevelData"

    private final CanvasLayer cl;
    private final CanvasCompositor cc;


    public static final int GRID_SIZE = 50;

    private boolean isGamePaused = false;



    public static final long OPERATION_INTERVAL = 100;
    private static final String ESCAPE_KEYCODE = "Escape";

    

    private double cameraScale;
    private double cameraX;
    private double cameraY;

    private int timeLeft;


    GameSession(Game game, String gameData, CanvasCompositor cc) {
        this.game = game;
        this.graphicsContext = graphicsContext; // Initialize the GraphicsContext

        this.currentSessionData = new GameSessionData(this,
            0, 0, 0, 0,
            0, 0
        );
        //TODO update this to account for loading games in the middle of play




        this.player = new Player(this, 10, 10, 10); // TODO: change the values
        interpretLevelData(gameData);



        int mapSizeX = 10;
        int mapSizeY = 10;

        this.gridHeight = mapSizeY;
        this.gridWidth = mapSizeX;











        generateSampleGame();


        this.cl = new CanvasLayer(new CanvasLayer.CanvasLayerI() {

            @Override
            public boolean onMouseDown(double x, double y, boolean hasConsumed) {
                return true;
            }

            @Override
            public boolean onMouseUp(double x, double y, boolean hasConsumed) {
                return true;
            }

            @Override
            public boolean onMouseMove(double x, double y, boolean hasConsumed) {
                return true;
            }

            @Override
            public void draw(GraphicsContext gc, long elapsed) {


                

                    for (int y = 0; y < gridHeight; ++y) {
                        for (int x = 0; x < gridWidth; ++x) {
    
                            gridTileMap[y][x].drawTile(gc);
    
                        }
                    }


                if (!isGamePaused) {

                    for (int y = 0; y < gridHeight; ++y) {
                        for (int x = 0; x < gridWidth; ++x) {
                            if (gridTileMap[y] == null || gridTileMap[y][x] == null) { System.out.printf("%d, %d\n", x, y);}
                            gridTileMap[y][x].updateTile(elapsed);
    
                        }
                    }
                    
                    double epsilon = Math.min(.5, Math.log(Math.max(10, elapsed)) / Math.log(1000));
                    
                    cameraScale = 15 / Math.exp(Math.abs(cameraX - player.x) + Math.abs(cameraY - player.y)) + 10;

                    cameraX = (double) player.x * epsilon + cameraX * (1 - epsilon);
                    cameraY = (double) player.y * epsilon + cameraY * (1 - epsilon);
                }
                
            }

            @Override
            public void onKeyDown(KeyCode key) {
                switch (key) {
                    case ESCAPE:
                        isGamePaused = !isGamePaused;
                        break;

                    case W:
                        //input up

                
                    default:
                        break;
                }
            }

            @Override
            public void onKeyUp(KeyCode key) {
                if (key == KeyCode.ESCAPE) {
                    
                }
            }

        }, 1);


        cc.addLayer(this.cl);
        this.cc = cc;
    }

    /*
        method used to interpret the gameData (from a levelFileFormat file) fills the gridTileMap, initiates timer,
        determine the
    */
    private void interpretLevelData(String gameData) {
        //TODO implement a function  to interpret level data
        // String[] gameDataArr = gameData.split(" ");

        // line 1: Height, Width
        // Line 2: TimeAllowed, DiamondsRequired, AmeobaSpreadRate, AmeobaSizeLimit
        // Line 3: TimeLeft, Score, DiamondCount
        // Line 4: RedKey, BlueKey, YellowKey, GreenKey
        // Line 5+: Actual level



    }
    public GraphicsContext getGraphicsContext() {
        return this.graphicsContext; // Return the stored GraphicsContext
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

    public GameSessionData getCurrentSessionData(){
        return this.currentSessionData;
    }

    //TODO determine a method of input before implementing
    public void onInput(){ return; }



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


    public void endGame() {
        this.cc.removeLayer(this.cl);
    }







    private void generateSampleGame() {
        Random rand = new Random();
        int sizeX = rand.nextInt(40) + 10;
        int sizeY = rand.nextInt(40) + 10;




        //fills the game with dirt
        //fills the border with wall
        gridHeight = sizeY;
        gridWidth = sizeX;

        this.gridTileMap = new Tile[sizeY][sizeX];

        for (int y = 0; y < sizeY; ++y) {
            this.gridTileMap[y][0] = new TitaniumWall(this, 0, y, OPERATION_INTERVAL);
            this.gridTileMap[y][sizeX - 1] = new TitaniumWall(this, sizeX - 1, y, OPERATION_INTERVAL);
        }

        for (int x = 1; x < sizeX - 1; ++x) {
            this.gridTileMap[0][x] = new TitaniumWall(this, x, 0, OPERATION_INTERVAL);
            this.gridTileMap[sizeY - 1][x] = new TitaniumWall(this, x, sizeY - 1, OPERATION_INTERVAL);
        }



        for (int y = 1; y < sizeY - 1; ++y) {
            for (int x = 1; x < sizeX - 1; ++x) {
                
                int random = rand.nextInt(100);

                if (random < 4) {
                    this.gridTileMap[y][x] = new TitaniumWall(this, x, y, OPERATION_INTERVAL);
                } else if (random < 10) {
                    this.gridTileMap[y][x] = new Boulder(this, x, y, OPERATION_INTERVAL);
                } else if (random < 15) {
                    this.gridTileMap[y][x] = new Diamond(this, x, y, OPERATION_INTERVAL);
                } else {
                    this.gridTileMap[y][x] = new DirtWall(this, x, y, OPERATION_INTERVAL);
                }
            } 
        }

        //set the player
        int playerX = rand.nextInt(sizeX - 5) + 2;
        int playerY = rand.nextInt(sizeY - 5) + 2;

        this.gridTileMap[playerY][playerX] = new Player(this, playerX, playerY, OPERATION_INTERVAL);

    }


    public double getCameraX() {
        return this.cameraX;
    }

    
    public double getCameraY() {
        return this.cameraX;
    }

    
    public double getCameraScale() {
        return this.cameraScale;
    }

}
