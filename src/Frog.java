import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Frog extends Enemy {




    public static final Image img = new Image("file:Assets/Images/Frog.png"); // Placeholder for the image

    private ArrayList<int[]> path = new ArrayList<>();
    private int cooldown = 0;
    private int pathRefresh = 0;
    

    private static final int COOLDOWN_MOVE = 30;
    private static final int PATH_REFRESH_RATE = 120;

    public Frog(GameSession gameSession, int x, int y){
        super(gameSession, x, y, TileType.MOVING_ENEMY, 0);
        this.amoebaCanSpreadToThisTile = true;
    }


    public void interact(Tile tile) {

        if ((tile.getTileType() == TileType.BOULDER || tile.getTileType() == TileType.DIAMOND) && tile.getYPosition() == this.y - 1) {
            this.triggerExplosion(this.x, this.y, false);
        }
    }

    public void updateTile(long currentTimeInMilliseconds) {
        
        //checks if the frog should move
        if (pathRefresh > 0) {
            pathRefresh--;
        }

        if (cooldown > 0) { cooldown--; return; }
        cooldown = COOLDOWN_MOVE;



        //moves the frog if there is a path, if not, generate one
        if (pathRefresh == 0 || path.size() == 0) { refreshPath(); }

        if (path.size() == 0) { doRandomMove(); return; }


        int[] pathTarget = path.remove(0);

        if (!moveTo(pathTarget[0], pathTarget[1])) {
            refreshPath();
        }
    }





    /**
     * Refreshes the path of the frog
     */
    private void refreshPath() {
        pathRefresh = PATH_REFRESH_RATE;
        
        PathFinder<Tile> pf = new PathFinder<>(gameSession.getGridTileMap(), gameSession.getGridWidth(), gameSession.getGridHeight());
        // System.out.println("recalculating path");
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


