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

    private final CanvasLayer cl;
    private final CanvasCompositor cc;

    private final GamePauseMenu gamePauseMenu;


    public static final int GRID_SIZE = 50;

    private boolean isGamePaused = false;



    public static final long OPERATION_INTERVAL = 100;
    

    

    private double cameraScale;
    private double cameraX;
    private double cameraY;



    GameSession(Game game, String gameData, CanvasCompositor cc) {
        this.game = game;
        this.gamePauseMenu = new GamePauseMenu(this, cc);

        interpretLevelData(gameData);//loads gameSessionData and fills the grid tile map




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



                
                //simulate game if the game is not paused
                if (!isGamePaused) {
                    timeLeft -= 1000 / 60;
                    if (timeLeft <= 0) {
                        player.killPlayer();//LOL
                    }

                    for (int y = 0; y < gridHeight; ++y) {
                        for (int x = 0; x < gridWidth; ++x) {
                            if (gridTileMap[y] == null || gridTileMap[y][x] == null) { System.out.printf("%d, %d\n", x, y);}
                            gridTileMap[y][x].updateTile(elapsed);
    
                        }
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
        amoebaControllerList = new ArrayList<AmoebaController>();
    }


    /**
     * Initialises GameSessiondata and fills the 2DGridTileMap.
     * @param gameData
     * The data used to fill GameSessionData and the grid tile map.
     */
    private void interpretLevelData(String gameData) {

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

        int score = Integer.parseInt(gameDataArr[3]);
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
            String[] gridMapLinesArray = stringGridMap.split("\n");//split file by new line
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
                            newTile = new Door(this, x, y, OPERATION_INTERVAL, 'R');//TODO this may break the game when saving, come back to this
                            break;
                        case "BD":
                            newTile = new Door(this, x, y, OPERATION_INTERVAL, 'B');//TODO this may break the game when saving, come back to this
                            break;
                        case "YD":
                            newTile = new Door(this, x, y, OPERATION_INTERVAL, 'Y');//TODO this may break the game when saving, come back to this
                            break;
                        case "GD":
                            newTile = new Door(this, x, y, OPERATION_INTERVAL, 'G');//TODO this may break the game when saving, come back to this
                            break;
                        case "P":
                            Player player = new Player(this, x, y, OPERATION_INTERVAL);
                            this.player = player;
                            newTile = player;
                            break;
                        case "BF"://TODO ask omar or isaac the proper way to do this
                            newTile = new Butterfly(this, x, y, OPERATION_INTERVAL, true);//TODO ask omar or isaac about prioritise direction
                            break;
                        case "FF"://TODO ask omar or isaac the proper way to do this
                            newTile = new FireFly(this, x, y, OPERATION_INTERVAL, true);//TODO ask omar or isaac about prioritise direction
                            break;
                        case "F":
                            newTile = new Frog(this, x, y);//TODO ask alex why there is no operation interval for frog
                            break;
                        case "A":
                            AmoebaController newAmoebaController = new AmoebaController(this, x, y, OPERATION_INTERVAL, this.amoebaGrowthRate, this.maxAmoebaSize);
                            this.amoebaControllerList.add(newAmoebaController);
                            break;
                        default:
                            System.out.println("ERROR TILE NOT RECOGNISED, PRINTING PATHWALL BY DEFAULT");
                            newTile = new PathWall(this, x, y, OPERATION_INTERVAL);
                            break;
                    }
//                    System.out.printf("filling (%d, %d)\n", x, y);




                    this.gridTileMap[y][x] = newTile;
                }
            }
        }

    public String buildSaveString(){
        String saveString = "";

        int[] data = this.currentSessionData.returnAllGameSessionData();
        //TODO figure out where "current level" is stored, also clean up inline comments
        saveString += "1;"; //change this when you figure it out
        saveString += Integer.toString(this.gridHeight) + ";" + Integer.toString(this.gridWidth) + ";"; //height and width
        saveString += Integer.toString(data[0]) + ";" + Integer.toString(data[1]) + ";" + Integer.toString(data[2]); //Score, timeleft, time allowed
        saveString += Integer.toString(data[3]) + ";" + Integer.toString(data[4]) + ";";//diamondCount and diamondsRequired
        saveString += "4;4;"; //TODO add amoebaspreadrate and ameoba max size somewhere in gamesession
        saveString += Integer.toString(data[5]) + ";" + Integer.toString(data[6]) + ";" + Integer.toString(data[7]) + ";" + Integer.toString(data[8]);//keys

        for (int i = 0; i < gridTileMap.length; i++){
            for (int ii = 0; i < gridTileMap[i].length; ii++){
                saveString += gridTileMap[i][ii].returnStringTileRepresentation() + " ";
            }
            saveString += "\n";
        }
        return saveString;
    }

    public void setAllGameSession(int currentLevel, int height, int width,
                                   int score, int timeLeft, int timeAllowed, int startingTime,
                                   int diamondCount, int diamondsRequired,
                                   int redKeys, int blueKeys, int yellowKeys, int greenKeys) {
        currentSessionData.setAllGameSessionData(score, timeAllowed, startingTime,
                diamondCount, diamondsRequired,
                redKeys, blueKeys, yellowKeys, greenKeys);
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

    // /**
    //  * Calls the kill player method on this game session's player.
    //  */
    // public void callKillPlayer() {
    //     player.killPlayer();
    // }


    

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


    public void endGame() {
        this.cc.removeLayer(this.cl);
    }

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

        String scoreString = String.format("%04d", currentSessionData.getDiamondCount());
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

    public ArrayList<AmoebaController> getAmeobaControllerList() {
        return amoebaControllerList;
    }
}
