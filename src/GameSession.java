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
    private final GameSessionData currentSessionData; //Reference to this games' game session data
    private Player player; //Reference to the current single game player (inserted into the level in "interpretLevelData"

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

        this.currentSessionData = new GameSessionData(this,
            0, 0, 0, 0,
            0, 0, 0, 0
        );
        this.gamePauseMenu = new GamePauseMenu(this, cc);
        //TODO update this to account for loading games in the middle of play




        // this.player = new Player(this, 10, 10, 10); // TODO: change the values
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


                
                gc.setFill(new Color(.05, .05, .05, 1));
                gc.fillRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

                for (int y = 0; y < gridHeight; ++y) {
                    for (int x = 0; x < gridWidth; ++x) {

                        gridTileMap[y][x].drawTile(gc);

                    }
                }

                // draw the top bar :)

                drawTopBar(gc);



                

                if (isGamePaused) {




                } else {
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
                    
                    // cameraScale = 15 / Math.exp(Math.abs(cameraX - player.x) + Math.abs(cameraY - player.y)) + 10;
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

        // Vers2
        // line 1: Current Level, Height, Width
        // Line 2: Score, TimeLeft, TimeAllowed
        // Line 3: DiamondCount, DiamondsRequired
        // Line 4: AmeobaSpreadRate, AmeobaSizeLimit
        // Line 5: RedKey, BlueKey, YellowKey, GreenKey
        // Line 6+: Actual level
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

        this.player = new Player(this, playerX, playerY, OPERATION_INTERVAL);
        this.gridTileMap[playerY][playerX] = this.player;


        this.timeLeft = 60*60*1000;


        
        int frogX = rand.nextInt(sizeX - 2) + 1;
        int frogY = rand.nextInt(sizeY - 2) + 1;
        this.gridTileMap[frogY][frogX] = new Frog(this, frogX, frogY);

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

}
