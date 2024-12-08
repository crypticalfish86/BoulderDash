import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

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

    // private final ArrayList<Function<Long, Void>> updateConnections = new ArrayList<>();
    

    

    private double cameraScale;
    private double cameraX;
    private double cameraY;



    GameSession(Game game, String gameData, CanvasCompositor cc, int accumulatedScore) {
        this.game = game;
        this.gamePauseMenu = new GamePauseMenu(this, cc);
        amoebaControllerList = new ArrayList<AmoebaController>();
        teleportWallList = new ArrayList<TeleportWall>();
        interpretLevelData(gameData, accumulatedScore);//loads gameSessionData and fills the grid tile map

        


         //generateSampleGame();

        //the draw method is under the draw of this interface
        //the main class controlls it to draw
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


                //draw the tiles
                gc.setFill(new Color(.05, .05, .05, 1));
                gc.fillRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

                for (int y = 0; y < gridHeight; ++y) {
                    for (int x = 0; x < gridWidth; ++x) {

                        gridTileMap[y][x].drawTile(gc);

                    }
                }

                // draw the top bar :)
                drawTopBar(gc);



                // Update game state if not paused
                if (!isGamePaused) {
                    timeLeft -= 1000 / 60; // Reduce time (frame-based)

                    // Check if time has run out
                    if (timeLeft <= 0) {
                        System.out.println("Time's up! Player killed.");
                        
                        onGameOver(false); // Trigger the game-over logic
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
     * Initialises GameSessiondata and fills the 2DGridTileMap.
     * @param gameData the string to be converted to data
     * @param accumulatedScore the score to be carried on from last level
     * The data used to fill GameSessionData and the grid tile map.
     */
    private void interpretLevelData(String gameData, int accumulatedScore) {

        // file structure
        // line 1: Current Level, Height, Width
        // Line 2: Score, TimeLeft, TimeAllowed
        // Line 3: DiamondCount, DiamondsRequired
        // Line 4: AmeobaSpreadRate, AmeobaSizeLimit
        // Line 5: RedKey, BlueKey, YellowKey, GreenKey
        // Line 6+: Actual level

        String[] gameDataArr = gameData.split(";\\s*");
        this.gridHeight = Integer.parseInt(gameDataArr[2]);
        this.gridWidth = Integer.parseInt(gameDataArr[1]);
        System.out.printf("Map size: (%d, %d)\n", this.gridWidth, this.gridHeight);

        this.gridTileMap = new Tile[this.gridHeight][this.gridWidth];

        int score = Integer.parseInt(gameDataArr[3]) + accumulatedScore;
        int timeLeft = Integer.parseInt(gameDataArr[4]) * 1000;
        int diamondCount = Integer.parseInt(gameDataArr[6]);
        int diamondsRequired = Integer.parseInt(gameDataArr[7]);
        this.amoebaGrowthRate = Integer.parseInt(gameDataArr[8]);
        this.maxAmoebaSize = Integer.parseInt(gameDataArr[9]);
        int redKeys = Integer.parseInt(gameDataArr[10]);
        int blueKeys = Integer.parseInt(gameDataArr[11]);
        int yellowKeys = Integer.parseInt(gameDataArr[12]);
        int greenKeys = Integer.parseInt(gameDataArr[13]);

        this.timeLeft = timeLeft;
        this.currentSessionData = new GameSessionData(this, timeLeft, diamondsRequired, redKeys, blueKeys, yellowKeys, greenKeys, diamondCount, score);


        String entireLevelString = gameDataArr[14];
        //TODO fill grid tile map
        fill2DGrid(entireLevelString);


    }
        private void fill2DGrid(String stringGridMap){
            String[] gridMapLinesArray = stringGridMap.split(System.lineSeparator());//split file by new line
            System.out.println(gridMapLinesArray.length);
            for (int y = 0; y < gridMapLinesArray.length; y++){

                String[] gridLineTileArray = gridMapLinesArray[y].split(" ");//then split each element of that array by
                
                for(int x = 0; x < gridLineTileArray.length; x++){

                    Tile newTile = new PathWall(this, x, y, OPERATION_INTERVAL);//this should never finish itialised as this, it should go through the switch statement
                    switch (gridLineTileArray[x]){
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
                            newTile = new Diamond(this, x, y, OPERATION_INTERVAL);
                            break;
                        case "@":
                            newTile = new Boulder(this, x, y, OPERATION_INTERVAL);
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
                            newTile = new Frog(this, x, y);//TODO ask alex why there is no operation interval for frog
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

                            if(amoebaController != null){
                                amoebaController.addNewAmoebaChildToCluster(x, y);
                            } else{
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

                            if(teleportWall != null){
                                teleportWall.setTeleportWallBrother(newTeleportWall);
                                this.gridTileMap[y][x] = newTeleportWall;
                            }
                            else {
                                this.teleportWallList.add(newTeleportWall);
                                this.gridTileMap[y][x] = newTeleportWall;
                            }
                            break;


                        default:
                            System.out.println("ERROR TILE NOT RECOGNISED, PRINTING PATHWALL BY DEFAULT");
                            newTile = new PathWall(this, x, y, OPERATION_INTERVAL);
                            break;
                    }



                    if(!gridLineTileArray[x].equals("A")) {
                        this.gridTileMap[y][x] = newTile;
                    }
                }
            }
        }

    public boolean doesNotEqualAmoebaOrTeleportTile(String tileString){
        switch(tileString){
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

    public String buildSaveString() {
        String saveString = "";

        int[] data = this.currentSessionData.returnAllGameSessionData();
        //TODO figure out where "current level" is stored, also clean up inline comments
        String newLineString = System.lineSeparator();

        saveString += "1;"; //change this when you figure it out
        saveString += Integer.toString(this.gridWidth) + ";" + Integer.toString(this.gridHeight) + ";" + newLineString; //height and width
        saveString += Integer.toString(data[0]) + ";" + timeLeft / 1000 + ";" + Integer.toString(data[2] / 1000) + ";" + newLineString; //Score, timeleft, time allowed
        saveString += Integer.toString(data[3]) + ";" + Integer.toString(data[4]) + ";\n";//diamondCount and diamondsRequired
        saveString += Integer.toString(this.amoebaGrowthRate) + ";" + Integer.toString(this.maxAmoebaSize) + ";" + newLineString; //TODO add amoebaspreadrate and ameoba max size somewhere in gamesession
        saveString += Integer.toString(data[5]) + ";" + Integer.toString(data[6]) + ";" + Integer.toString(data[7]) + ";" + Integer.toString(data[8]) + ";";//keys

        for (int y = 0; y < gridTileMap.length; y++){
            saveString += newLineString + gridTileMap[y][0].returnStringTileRepresentation();
            for (int x = 1; x < gridTileMap[y].length; x++){
                saveString += " " + gridTileMap[y][x].returnStringTileRepresentation();
            }


        }
        return saveString;
    }

    public void setAllGameSession(int currentLevel, int height, int width,
                                   int score, int timeLeft, int timeAllowed, int startingTime,
                                   int diamondCount, int diamondsRequired,
                                   int redKeys, int blueKeys, int yellowKeys, int greenKeys) {
        currentSessionData.setAllGameSessionData(score, timeAllowed, startingTime,
                diamondCount, diamondsRequired,
                redKeys, blueKeys, yellowKeys, greenKeys, currentLevel);
        this.gridHeight = height;
        this.gridWidth = width;
        this.timeLeft = timeLeft;
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
        setTile(incomingTile.getYPosition(), incomingTile.getXPosition(), replacementTile);
        setTile(outgoingTile.getYPosition(), outgoingTile.getXPosition(), incomingTile);
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

    public Tile[][] getGridTileMap(){ return this.gridTileMap; }

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
        game.endGame();
    }


    private void generateSampleGame() {
        Random rand = new Random();
        int sizeX = rand.nextInt(10) + 10;
        int sizeY = rand.nextInt(10) + 10;




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
                } else if (random < 4) {
                    this.gridTileMap[y][x] = new Butterfly(this, x, y, OPERATION_INTERVAL, true);
                }  else {
                    this.gridTileMap[y][x] = new DirtWall(this, x, y, OPERATION_INTERVAL);
                }

                Tile butterfly = new Butterfly(this, x, y, OPERATION_INTERVAL, true);
                this.setTile(3,3, butterfly);
            } 
        }

        //set the player
        int playerX = rand.nextInt(sizeX - 5) + 2;
        int playerY = rand.nextInt(sizeY - 5) + 2;

        this.player = new Player(this, playerX, playerY, OPERATION_INTERVAL);
        this.gridTileMap[playerY][playerX] = this.player;


        this.timeLeft = 60*60*1000;


        
        int frogX = rand.nextInt(sizeX - 2) + 1;
        int frogY = rand.nextInt(sizeY - 2) + 1;
        this.gridTileMap[frogY][frogX] = new Frog(this, frogX, frogY);

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





    private void drawTopBar(GraphicsContext gc) {
        gc.setFill(new Color(.5, .5, .5, .7));
        gc.fillRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT * .1);


        

        
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", Main.WINDOW_HEIGHT * .05));

        
        // long timeDiff = startTimeStamp + maxTimeToCompleteLevel - System.currentTimeMillis();
        int secondsLeft = (int) (timeLeft / 1000);

        //draw the timer
        gc.setTextAlign(TextAlignment.LEFT);
        String timeString = String.format("%02d:%02d", secondsLeft / 60, secondsLeft % 60);
        gc.fillText(timeString, Main.WINDOW_WIDTH * .05, Main.WINDOW_HEIGHT * .07);



        gc.setTextAlign(TextAlignment.RIGHT);
        //draw the score

        String scoreString = String.format("%04d", currentSessionData.getScore());
        gc.fillText(scoreString, Main.WINDOW_WIDTH * .95, Main.WINDOW_HEIGHT * .07);



    }


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

    public long getTimeLeft() {
        return timeLeft;
    }

    /**
     * return the amoeba controller list.
     * @return
     * The amoeba controller list.
     */
    public ArrayList<AmoebaController> getAmeobaControllerList() {
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
            System.out.println("Congratulations! Level Complete.");
        } else {
            System.out.println("Game Over. Better luck next time.");
        }

        // Call the game's method to handle the end of the session
        game.onGameOver(hasWon, currentSessionData);

        endGame();
        gamePauseMenu.hide();
    }


    


    public void onSaveGameClicked() {
        game.saveGameButton();
        gamePauseMenu.hide();
    }


    public void onLoadGameClicked() {
        game.loadGameButton();
        endGame();
        gamePauseMenu.hide();
    }

    //primarily used for amoeba, removed
    // public void connectToGameUpdate(Function<Long, Void> function) {
    //     updateConnections.add(function);
    // }

    // public void disconnectToGameUpdate(Function<Long, Void> function) {
    //     updateConnections.remove(function);
    // }


}
