import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;



/**
 * Manages the game logic internally by providing the canvas.
 * Collaborates with game to work with external features.
 * @author Jace Weerawardena
 * @author Alex (Tsz Tung Yee)
 * @author Armaan Ghadiali
 * @author Isaac Atkinson
 * @author Omar Zarugh
 * @version 1.4
 */
public class GameSession {

    private int gridHeight; //The height of the grid
    private int gridWidth; //The width of the grid

    private long timeLeft; //The starting time stamp (ms since 1970)
    // private long maxTimeToCompleteLevel; //The time given to complete level (in ms)

    private Tile[][] gridTileMap; //The full 2D grid instantiated on the interpretation of the level data
    private final Game game; // Reference to the game that the current game session is attached to
    private GameSessionData currentSessionData; //Reference to this games' game session data
    private Player player; //Reference to the current single game player (inserted into the level in "interpretLevelData"

    private final ArrayList<AmoebaController> amoebaControllerList;
    private int amoebaGrowthRate;
    private int maxAmoebaSize;

    private ArrayList<TeleportWall> teleportWallList;
    private final CanvasLayer cl;
    private final CanvasCompositor cc;

    private final GamePauseMenu gamePauseMenu;


    public static final int GRID_SIZE = 50;

    private boolean isGamePaused = false;



    public static final long OPERATION_INTERVAL = 100;

    

    

    private double cameraScale;
    private double cameraX;
    private double cameraY;


    /**
     * Creates a game session with a given initial state in the second parameter, and with a given starting score.
     * @param game
     * @param gameData the string that represents a state of the game.
     * @param cc a canvas to add a drawing layer.
     * @param accumulatedScore score of the game that is carried on from the last level.
     */
    GameSession(Game game, String gameData, CanvasCompositor cc, int accumulatedScore) {
        this.game = game;
        this.gamePauseMenu = new GamePauseMenu(this, cc);
        amoebaControllerList = new ArrayList<AmoebaController>();
        teleportWallList = new ArrayList<TeleportWall>();
        interpretLevelData(gameData, accumulatedScore);//loads gameSessionData and fills the grid tile map
        

        //the draw method is under the draw of this interface
        //the main class controls it to draw
        this.cl = new CanvasLayer(new CanvasLayer.CanvasLayerI() {

            @Override
            public boolean onMouseDown(double x, double y, boolean hasConsumed) { return true; }

            @Override
            public boolean onMouseUp(double x, double y, boolean hasConsumed) { return true; }

            @Override
            public boolean onMouseMove(double x, double y, boolean hasConsumed) { return true; }

            @Override
            public void draw(GraphicsContext gc, long elapsed) {
                gc.setFill(new Color(.05, .05, .05, 1));
                gc.fillRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
                
                //draw the tiles
                drawTiles(gc);

                // draw the top bar :)
                drawTopBar(gc);

                // Update game state if not paused
                if (!isGamePaused) {
                    updateGameState(elapsed);
                }
            }

            @Override
            public void onKeyDown(KeyCode key) {
                keyDown(key);
            }

            @Override
            public void onKeyUp(KeyCode key) {
                keyUp(key);
            }
        }, 1);


        cc.addLayer(this.cl);
        this.cc = cc;
    }


    /**
     * Initialises GameSessionData and fills the 2DGridTileMap.
     * @param gameData the string to be converted to data
     * @param accumulatedScore the score to be carried on from last level
     * The data used to fill GameSessionData and the grid tile map.
     */
    private void interpretLevelData(String gameData, int accumulatedScore) {
        String[] gameDataArr = gameData.split(";\\s*");

        // file structure
        // line 1: Current Level, Height, Width
        // Line 2: Score, TimeLeft, TimeAllowed
        // Line 3: DiamondCount, DiamondsRequired
        // Line 4: AmoebaSpreadRate, AmoebaSizeLimit
        // Line 5: RedKey, BlueKey, YellowKey, GreenKey

        
        
        // line 1: Current Level, Height, Width
        int currentLevel = Integer.parseInt(gameDataArr[0]);
        this.gridHeight = Integer.parseInt(gameDataArr[2]);
        this.gridWidth = Integer.parseInt(gameDataArr[1]);

        
        
        
        // Line 2: Score, TimeLeft, TimeAllowed
        int score = Integer.parseInt(gameDataArr[3]) + accumulatedScore;
        int timeLeft = Integer.parseInt(gameDataArr[4]) * 1000;
        this.timeLeft = timeLeft;
        
        
        // Line 3: DiamondCount, DiamondsRequired
        int diamondCount = Integer.parseInt(gameDataArr[6]);
        int diamondsRequired = Integer.parseInt(gameDataArr[7]);
        
        
        // Line 4: AmoebaSpreadRate, AmoebaSizeLimit
        this.amoebaGrowthRate = Integer.parseInt(gameDataArr[8]);
        this.maxAmoebaSize = Integer.parseInt(gameDataArr[9]);
        
        
        // Line 5: RedKey, BlueKey, YellowKey, GreenKey
        int redKeys = Integer.parseInt(gameDataArr[10]);
        int blueKeys = Integer.parseInt(gameDataArr[11]);
        int yellowKeys = Integer.parseInt(gameDataArr[12]);
        int greenKeys = Integer.parseInt(gameDataArr[13]);
        


        this.currentSessionData = new GameSessionData(
            this, timeLeft, diamondsRequired, redKeys, blueKeys, yellowKeys, greenKeys,
            diamondCount, score, currentLevel
        );
            
            
        
        
        // Line 6+: Actual level
        String entireLevelString = gameDataArr[14];
        this.gridTileMap = new Tile[this.gridHeight][this.gridWidth];
        fill2DGrid(entireLevelString);


    }


    /**
     * fills the 2d grid of this game by a given string
     * @param stringGridMap the string that is derived from the save file format to be read
     */
    private void fill2DGrid(String stringGridMap) {
        String[] gridMapLinesArray = stringGridMap.split(System.lineSeparator());//split file by new line
        System.out.println(gridMapLinesArray.length);
        for (int y = 0; y < gridMapLinesArray.length; y++) {

            String[] gridLineTileArray = gridMapLinesArray[y].split(" ");//then split each element of that array by
            
            for(int x = 0; x < gridLineTileArray.length; x++) {

                Tile newTile = new PathWall(this, x, y, OPERATION_INTERVAL);
                //this should never finish initialised as this, it should go through the switch statement


                switch (gridLineTileArray[x]) {
                    case "-":
                        newTile = new PathWall(this, x, y, OPERATION_INTERVAL);
                        break;
                    case "E":
                        newTile = new ExitWall(this, x, y, OPERATION_INTERVAL);
                        break;
                    case "W":
                        newTile = new NormalWall(this, x, y, OPERATION_INTERVAL);
                        break;
                    case "T":
                        newTile = new TitaniumWall(this, x, y, OPERATION_INTERVAL);
                        break;
                    case "M":
                        newTile = new MagicWall(this, x, y, OPERATION_INTERVAL);
                        break;
                    case "D":
                        newTile = new DirtWall(this, x, y, OPERATION_INTERVAL);
                        break;
                    case "*":
                        newTile = new Diamond(this, x, y, OPERATION_INTERVAL, false);
                        break;
                    case "@":
                        newTile = new Boulder(this, x, y, OPERATION_INTERVAL,false);
                        break;
                    case "RK":
                        newTile = new Key(this, x, y, OPERATION_INTERVAL, "RK");
                        break;
                    case "BK":
                        newTile = new Key(this, x, y, OPERATION_INTERVAL, "BK");
                        break;
                    case "YK":
                        newTile = new Key(this, x, y, OPERATION_INTERVAL, "YK");
                        break;
                    case "GK":
                        newTile = new Key(this, x, y, OPERATION_INTERVAL, "GK");
                        break;
                    case "RD":
                        newTile = new Door(this, x, y, OPERATION_INTERVAL, "RD");
                        break;
                    case "BD":
                        newTile = new Door(this, x, y, OPERATION_INTERVAL, "BD");
                        break;
                    case "YD":
                        newTile = new Door(this, x, y, OPERATION_INTERVAL, "YD");
                        break;
                    case "GD":
                        newTile = new Door(this, x, y, OPERATION_INTERVAL, "GD");
                        break;
                    case "P":
                        Player player = new Player(this, x, y, OPERATION_INTERVAL);
                        this.player = player;
                        newTile = player;
                        break;
                    case "BL":
                        newTile = new Butterfly(this, x, y, OPERATION_INTERVAL, true);
                        break;
                    case "BR":
                        newTile = new Butterfly(this, x, y, OPERATION_INTERVAL, false);
                        break;
                    case "FL":
                        newTile = new FireFly(this, x, y, OPERATION_INTERVAL, true);
                        break;
                    case "FR":
                        newTile = new FireFly(this, x, y, OPERATION_INTERVAL, false);
                        break;
                    case "F":
                        newTile = new Frog(this, x, y);
                        break;
                    case "A1":
                    case "A2":
                    case "A3":
                    case "A4":
                    case "A5":
                    case "A6":
                    case "A7":
                    case "A8":
                    case "A9":
                        int amoebaID = Integer.parseInt(Character.toString(gridLineTileArray[x].charAt(1)));
                        AmoebaController amoebaController = this.returnAmoebaControllerByID(amoebaID);

                        if (amoebaController != null) {
                            amoebaController.addNewAmoebaChildToCluster(x, y);
                        } else {
                            AmoebaController newAmoebaController = new AmoebaController(this, x, y,
                                    OPERATION_INTERVAL, this.amoebaGrowthRate, this.maxAmoebaSize, amoebaID);
                            this.amoebaControllerList.add(newAmoebaController);
                        }
                        break;
                    case "TE1":
                    case "TE2":
                    case "TE3":
                    case "TE4":
                    case "TE5":
                    case "TE6":
                    case "TE7":
                    case "TE8":
                    case "TE9":
                        int teleportWallID = Integer.parseInt(Character.toString(gridLineTileArray[x].charAt(2)));
                        TeleportWall teleportWall = this.returnTeleportWallByID(teleportWallID);

                        TeleportWall newTeleportWall = new TeleportWall(this, x, y, TileType.TELEPORT_WALL, OPERATION_INTERVAL, teleportWallID);

                        if (teleportWall != null) {
                            teleportWall.setTeleportWallBrother(newTeleportWall);
                            this.gridTileMap[y][x] = newTeleportWall;
                        } else {
                            this.teleportWallList.add(newTeleportWall);
                            this.gridTileMap[y][x] = newTeleportWall;
                        }
                        break;


                    default:
                        newTile = new PathWall(this, x, y, OPERATION_INTERVAL);
                        break;
                }



                if(doesNotEqualAmoebaOrTeleportTile(gridLineTileArray[x])) {
                    this.gridTileMap[y][x] = newTile;
                }
            }
        }
    }

    /**
     * Determines whether a tile is not an amoeba or a teleport tile.
     * @param tileString The string representation of the tile to check.
     * @return true if the tile is an amoeba or teleport tile, false otherwise.
     */
    public boolean doesNotEqualAmoebaOrTeleportTile(String tileString) {
        switch(tileString) {
            case "A1":
            case "A2":
            case "A3":
            case "A4":
            case "A5":
            case "A6":
            case "A7":
            case "A8":
            case "A9":
            case "TE1":
            case "TE2":
            case "TE3":
            case "TE4":
            case "TE5":
            case "TE6":
            case "TE7":
            case "TE8":
            case "TE9":
                return false;
            default:
                return true;
        }
    }


    /**
     * Retreives the save string that can be used to reconstruct this game
     * @return string of the data, can be saved in a text file
     */
    public String buildSaveString() {
        String saveString = "";

        int[] data = this.currentSessionData.returnAllGameSessionData();
        String newLineString = System.lineSeparator();

        saveString += currentSessionData.getLevel() + ";";


        // height and width
        saveString += Integer.toString(this.gridWidth) + ";" +
                    Integer.toString(this.gridHeight) + ";" + newLineString;


        // Score, timeLeft, time allowed
        saveString += Integer.toString(data[0]) + ";" + timeLeft / 1000 + ";" +
                    Integer.toString(data[2] / 1000) + ";" + newLineString;


        // diamondCount and diamondsRequired
        saveString += Integer.toString(data[3]) + ";" + Integer.toString(data[4])
                + ";" + newLineString;


        // amoebaSpreadRate and amoeba max size 
        saveString += Integer.toString(this.amoebaGrowthRate) + ";" +
                    Integer.toString(this.maxAmoebaSize) + ";" + newLineString;


        // keys
        saveString += Integer.toString(data[5]) + ";" + Integer.toString(data[6]) + ";" +
                    Integer.toString(data[7]) + ";" + Integer.toString(data[8]) + ";";




        for (int y = 0; y < gridTileMap.length; y++) {
            saveString += newLineString + gridTileMap[y][0].returnStringTileRepresentation();
            for (int x = 1; x < gridTileMap[y].length; x++) {
                saveString += " " + gridTileMap[y][x].returnStringTileRepresentation();
            }
        }


        return saveString;
    }



    /**
     * Gets the tile on the specified x and y. may error if x and y are out of bounds.
     * @param x x-position of the grid, from left to right.
     * @param y y-position of the grid, from top to down.
     * @return tile on the grid
     */
    public Tile getTileFromGrid(int x, int y) {
        return gridTileMap[y][x];
    }

    
    /**
     * move a tile to another tile in the gridMap and replace where the tile was with another tile.
     * @param replacementTile
     * The tile that will replace the current incoming tiles location.
     * @param incomingTile
     * The incoming tile that will be moved to the outgoing tile location.
     * @param outgoingTile
     * The outgoing tile that is being deleted.
     */
    public void updateTilePositions(Tile replacementTile, Tile incomingTile, Tile outgoingTile) {
        setTile(incomingTile.getYPosition(), incomingTile.getXPosition(), replacementTile);
        setTile(outgoingTile.getYPosition(), outgoingTile.getXPosition(), incomingTile);
    }

    /**
     * Set a new or existing tile at a specific location in the gridMap.
     * @param yTileLocation
     * The y location in the gridMap you want to set a new tile for.
     * @param xTileLocation
     * The x location in the gridMap you want to set a new tile for.
     * @param tile
     * The tile that will be set at this location.
     */
    public void setTile(int yTileLocation, int xTileLocation, Tile tile) {
        gridTileMap[yTileLocation][xTileLocation] = tile;
        tile.setNewPosition(xTileLocation, yTileLocation);
    }

    /**
     * Calls the kill player method on this game session's player.
     */
    public void callKillPlayer() {
        player.killPlayer();
    }


    

    public int getGridWidth() {
        return this.gridWidth;
    }

    public int getGridHeight() {
        return this.gridHeight;
    }

    public Tile[][] getGridTileMap() { return this.gridTileMap; }

    public int getPlayerX() { return this.player.getXPosition(); }
    public int getPlayerY() { return this.player.getYPosition(); }

    public GameSessionData getCurrentSessionData(){
        return this.currentSessionData;
    }

   

    // function to be fired when the menu close button is clicked
    public void resume() {
        setIsPaused(false);
    }

    // decouples the layer to the display
    private void endGame() {
        cc.removeLayer(cl);
    }

    /**
     * Should be called by the pause menu to leave the game
     */
    public void exitGame() {
        endGame();
        game.showMenu();
    }


    

    private void startProfile1() {
        game.loadGame("Profiles/profile1.txt");
    }

    private void startProfile2() {
        game.loadGame("Profiles/profile2.txt");
    }

    private void startProfile3(){
        game.loadGame("Profiles/profile3.txt");
    }


    public double getCameraX() {
        return this.cameraX;
    }

    
    public double getCameraY() {
        return this.cameraY;
    }

    
    public double getCameraScale() {
        return this.cameraScale;
    }




    /**
     * Function that is called when a keyboard button is down.
     * @param key the keycode that corresponds to the down key event.
     */
    private void keyDown(KeyCode key) {
        switch (key) {
            case ESCAPE:
                setIsPaused(!isGamePaused);
                break;

            case UP:
            case W:
                if (isGamePaused) { break; }
                //input up
                player.onKeyPressed('W');
                break;


            case DOWN:
            case S:
                if (isGamePaused) { break; }
                //input down
                player.onKeyPressed('S');
                break;


            case LEFT:
            case A:
                if (isGamePaused) { break; }
                //input left
                player.onKeyPressed('A');
                break;


            case RIGHT:
            case D:
                if (isGamePaused) { break; }
                //input right
                player.onKeyPressed('D');
                break;
                

            default:
                break;
        }
    }
    

    /**
     * Function that is called when a keyboard button is up.
     * @param key the keycode that corresponds to the up key event.
     */
    private void keyUp(KeyCode key) {
        switch (key) {
            case UP:
            case W:
                //input up
                player.onKeyReleased('W');
                break;


            case DOWN:
            case S:
                //input down
                player.onKeyReleased('S');
                break;


            case LEFT:
            case A:
                //input left
                player.onKeyReleased('A');
                break;


            case RIGHT:
            case D:
                //input right
                player.onKeyReleased('D');
                break;
                

            default:
                break;
        }
    }




    
    /**
     * draws the tiles to a given graphics context
     * @param gc Graphics context derived from a canvas
     */
    private void drawTiles(GraphicsContext gc) {
        for (int y = 0; y < gridHeight; ++y) {
            for (int x = 0; x < gridWidth; ++x) {
                gridTileMap[y][x].drawTile(gc);
            }
        }
    }


    /**
     * draws the top bar on the given graphics context.
     * @param gc Graphics context derived from a canvas
     */
    private void drawTopBar(GraphicsContext gc) {
        gc.setFill(new Color(.5, .5, .5, .7));
        gc.fillRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT * .1);


        
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", Main.WINDOW_HEIGHT * .05));


        int secondsLeft = (int) (timeLeft / 1000);

        //draw the timer
        gc.setTextAlign(TextAlignment.LEFT);
        String timeString = String.format("%02d:%02d", secondsLeft / 60, secondsLeft % 60);
        gc.fillText(timeString, Main.WINDOW_WIDTH * .05, Main.WINDOW_HEIGHT * .07);


        //draw the diamond
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(new Color(.5, .6, .9, 1));
        String diamondString = String.format("%03d", currentSessionData.getDiamondCount());
        gc.fillText(diamondString, Main.WINDOW_WIDTH * .5, Main.WINDOW_HEIGHT * .07);


        //draw the score
        gc.setTextAlign(TextAlignment.RIGHT);
        gc.setFill(new Color(1, 1, 1, 1));
        String scoreString = String.format("%04d", currentSessionData.getScore());
        gc.fillText(scoreString, Main.WINDOW_WIDTH * .95, Main.WINDOW_HEIGHT * .07);
    }

    
    /**
     * Updates the game
     * @param elapsed milliseconds of the game has passed between last frame
     */
    private void updateGameState(long elapsed) {
        timeLeft -= 1000 / 60; // Reduce time (frame-based)

        // Check if time has run out
        if (timeLeft <= 0) {
            callKillPlayer();
            return; // Stop further updates
        }

        //update individual tiles
        for (int y = 0; y < gridHeight; ++y) {
            for (int x = 0; x < gridWidth; ++x) {
                if (gridTileMap[y] == null || gridTileMap[y][x] == null) { System.out.printf("%d, %d\n", x, y);}
                gridTileMap[y][x].updateTile(elapsed);

            }
        }

        //update amoeba
        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < amoebaControllerList.size(); ++i) {
            amoebaControllerList.get(i).updateAmoebaCluster(currentTime);
        }
        
        double epsilon = Math.min(.5, Math.log(Math.max(10, elapsed)) / Math.log(1000));
        
        cameraScale = 60;

        cameraX = (double) player.getXPosition() * epsilon + cameraX * (1 - epsilon);
        cameraY = (double) player.getYPosition() * epsilon + cameraY * (1 - epsilon);
    }








    /**
     * Set the paused state of the game.
     * @param isPaused if the game should be paused
     */
    private void setIsPaused(boolean isPaused) {
        if (isPaused ^ isGamePaused) {
            isGamePaused = isPaused;

            if (isGamePaused) {
                gamePauseMenu.show();
            } else {
                gamePauseMenu.hide();
            }
        }
    }



    /**
     * Get how many milliseconds are there left in the game
     * @return milliseconds the player has for the game
     */
    public long getTimeLeft() {
        return timeLeft;
    }



    /**
     * return the amoeba controller list.
     * @return
     * The amoeba controller list.
     */
    public ArrayList<AmoebaController> getAmoebaControllerList() {
        return amoebaControllerList;
    }



    /**
     * returns the AmoebaController by ID.
     * @param idNumber
     * The id number of the amoeba controller you're searching for.
     * @return
     * If the AmoebaController exists it is returned, otherwise return null.
     */
    public AmoebaController returnAmoebaControllerByID(int idNumber){
        for(AmoebaController controller : this.amoebaControllerList){
            if(controller.getClusterID() == idNumber){
                return controller;
            }
        }
        return null;
    }

    /**
     * returns a teleport wall in the teleportWallList by its ID.
     * @param idNumber
     * The id number of the teleport wall you're searching for.
     * @return
     * If the TeleportWall exists it is returned, otherwise return null.
     */
    public TeleportWall returnTeleportWallByID(int idNumber){
        for(TeleportWall teleportWall : this.teleportWallList){
            if(teleportWall.getTeleportWallID() == idNumber){
                return teleportWall;
            }
        }
        return null;
    }

    /**
     * Called by objects to end the game.
     * @param hasWon if the end game is counted as winning
     */
    public void onGameOver(boolean hasWon) {
        if (hasWon) {

        } else {

        }

        // Call the game's method to handle the end of the session
        game.onGameOver(hasWon, currentSessionData, timeLeft);

        endGame();
        gamePauseMenu.hide();
    }


    

    /**
     * performs save game on this game session.
     */
    public void onSaveGameClicked() {
        game.saveGameButton();
        gamePauseMenu.hide();
    }

    /**
     * performs load game on this game session. Brings up the menu and ends the current game.
     */
    public void onLoadGameClicked() {
        game.loadGameButton();
        endGame();
        gamePauseMenu.hide();
    }
}
