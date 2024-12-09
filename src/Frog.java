import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Frog extends Enemy {




    private static final Image img = new Image("file:Assets/Images/Frog.png"); // Placeholder for the image

    private ArrayList<int[]> path = new ArrayList<>();
    private int coolDown = 0; // holds the amount of frames that the frog should not move for
    private int pathRefresh = 0; // holds the amount of frames until the path should refresh automatically
    

    private static final int COOLDOWN_MOVE = 30;
    private static final int PATH_REFRESH_RATE = 120;


    /**
     * Constructs a frog.
     * @param gameSession The current gameSession.
     * @param x The x position of the frog.
     * @param y The y position of the frog.
     */
    public Frog(GameSession gameSession, int x, int y){
        super(gameSession, x, y, 0);
        
    }


    @Override
    public void interact(Tile tile) {

        if ((tile.getTileType() == TileType.BOULDER || tile.getTileType() == TileType.DIAMOND || tile.getTileType() == TileType.AMOEBA) && tile.getYPosition() == this.y - 1) {
            this.triggerExplosion(this.x, this.y, false);
        }
    }

    
    @Override
    public void updateTile(long currentTimeInMilliseconds) {

        //Kill frog if next to an amoeba
        if(isNextToAmoeba(this.x,this.y)){
            PathWall pathWall = new PathWall(gameSession, this.x, this.y, operationInterval);
            gameSession.setTile(this.y,this.x,pathWall);
        }
        
        //checks if the frog should move
        if (pathRefresh > 0) {
            pathRefresh--;
        }

        if (coolDown > 0) { coolDown--; return; }
        coolDown = COOLDOWN_MOVE;



        //moves the frog if there is a path, if not, generate one
        if (pathRefresh == 0 || path.size() == 0) { refreshPath(); }

        if (path.size() == 0) { doRandomMove(); return; }


        int[] pathTarget = path.remove(0);

        if (!moveTo(pathTarget[0], pathTarget[1])) {
            refreshPath();
        }

        checkForPlayer();
    }





    /**
     * Refreshes the path of the frog
     */
    private void refreshPath() {
        pathRefresh = PATH_REFRESH_RATE;
        
        PathFinder<Tile> pf = new PathFinder<>(gameSession.getGridTileMap(), gameSession.getGridWidth(), gameSession.getGridHeight());
        this.path = pf.computeGridFill(new int[] {this.x, this.y}, new int[] {gameSession.getPlayerX(), gameSession.getPlayerY()}, tile -> {
            return canMoveToTileType(tile.tileType);
        });
    }


    /**
     * Allows the frog to do a random move along its possible moves
     */
    private void doRandomMove() {

        int[][] possibleMoves = new int[4][2];
        int numPossibleMoves = 0;



        //check moves out of the 4 directions

        //up
        if (isXYInBounds(this.x, this.y - 1)) {
            if (canMoveToTileType(gameSession.getTileFromGrid(this.x, this.y - 1).getTileType())) {
                possibleMoves[numPossibleMoves][0] = 0;
                possibleMoves[numPossibleMoves][1] = -1;
                ++numPossibleMoves;
            }
        }

        //down
        if (isXYInBounds(this.x, this.y + 1)) {
            if (canMoveToTileType(gameSession.getTileFromGrid(this.x, this.y + 1).getTileType())) {
                possibleMoves[numPossibleMoves][0] = 0;
                possibleMoves[numPossibleMoves][1] = 1;
                ++numPossibleMoves;
            }
        }

        //left
        if (isXYInBounds(this.x - 1, this.y)) {
            if (canMoveToTileType(gameSession.getTileFromGrid(this.x - 1, this.y).getTileType())) {
                possibleMoves[numPossibleMoves][0] = -1;
                possibleMoves[numPossibleMoves][1] = 0;
                ++numPossibleMoves;
            }
        }

        //right
        if (isXYInBounds(this.x + 1, this.y)) {
            if (canMoveToTileType(gameSession.getTileFromGrid(this.x + 1, this.y).getTileType())) {
                possibleMoves[numPossibleMoves][0] = 1;
                possibleMoves[numPossibleMoves][1] = 0;
                ++numPossibleMoves;
            }
        }


        
        //perform the move out of the possible moves


        if (numPossibleMoves == 0) { return; }


        Random rand = new Random();
        int nextMoveInArray = (numPossibleMoves == 1) ? 0 : (rand.nextInt(numPossibleMoves));
        

        moveTo(this.x + possibleMoves[nextMoveInArray][0], this.y + possibleMoves[nextMoveInArray][1]);
    }



    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }

    

    public boolean canMoveToTileType(TileType tileType) {
        return tileType == TileType.PATH || tileType == TileType.PLAYER;
    }

    public String returnStringTileRepresentation() {
        return "F";
    }
}


