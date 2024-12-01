import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Random;


public class Frog extends Enemy {

    private static final String RIGHT_MOVE = "Right";
    private static final String LEFT_MOVE = "Left";
    private static final String UP_MOVE = "Up";
    private static final String DOWN_MOVE = "Down";
    private static final String NO_MOVE = "NoMove";



    public static final Image img = new Image("file:Assets/Images/Frog.png"); // Placeholder for the image


    public Frog(GameSession gameSession, int x, int y, long operationInterval){
        super(gameSession, x, y, TileType.MOVING_ENEMY, operationInterval);
        this.amoebaCanSpreadToThisTile = true;
    }


    public void interact(Tile tile) {

        if (tile.getTileType() == TileType.FALLING_OBJECT && tile.getYPosition() == this.y + 1) {
            this.triggerExplosion(this.x, this.y, false);
        }
    }

    public void updateTile(long currentTimeInMilliseconds) {
        String nextMove = this.calculateNextMove();

        if (nextMove.equals(RIGHT_MOVE)){
            Tile tileToInteractWith = gameSession.getTileFromGrid(this.x + 1, this.y);
            tileToInteractWith.interact(this);
        }else if (nextMove.equals(LEFT_MOVE)){
            Tile tileToInteractWith = gameSession.getTileFromGrid(this.x - 1, this.y);
            tileToInteractWith.interact(this);
        }else if (nextMove.equals(UP_MOVE)){
            Tile tileToInteractWith = gameSession.getTileFromGrid(this.x, this.y + 1);
            tileToInteractWith.interact(this);
        }else if (nextMove.equals(DOWN_MOVE)){
            Tile tileToInteractWith = gameSession.getTileFromGrid(this.x, this.y - 1);
            tileToInteractWith.interact(this);
        }else if (nextMove.equals(NO_MOVE)){
            //Frog will not move
        }else{
            throw new IllegalArgumentException("Invalid move" + nextMove);
        }


    }


    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }

    private String calculateNextMove() {
        Tile[][] grid = gameSession.getGridTileMap();
        int gridHeight = gameSession.getGridHeight();
        int gridWidth = gameSession.getGridWidth();
        int playerX = gameSession.getPlayerX();
        int playerY = gameSession.getPlayerY();

        int[][] navigableGrid = new int[gridWidth][gridHeight];

        //initialise a grid for pathdinding where 0 means the frog
        // can move to that tile and 1 means it can't
        for(int i = 0; i < gridWidth; i++){
            for(int j = 0; j < gridHeight; j++){
                if(grid[i][j].getTileType() == TileType.PATH){
                    navigableGrid[i][j] = 0;
                }else{
                    navigableGrid[i][j] = 1;
                }
            }
        }

        //TODO Pathfinding on navigable grid to find next move

        return generateRandomMove(); //Generate a random move if no path to player
    }



    private String generateRandomMove() {
        int gridHeight = gameSession.getGridHeight();
        int gridWidth = gameSession.getGridWidth();
        Tile[][] grid = gameSession.getGridTileMap();

        ArrayList<String> validMoves = new ArrayList<>();

        //Find valid moves frog can make
        if (isInBounds(this.x - 1, this.y) && grid[this.x - 1][this.y].getTileType() == TileType.PATH) {
            validMoves.add(LEFT_MOVE);
        }else if (isInBounds(this.x + 1, this.y) && grid[this.x + 1][this.y].getTileType() == TileType.PATH) {
            validMoves.add(RIGHT_MOVE);
        }else if (isInBounds(this.x, this.y + 1) && grid[this.x][this.y + 1].getTileType() == TileType.PATH) {
            validMoves.add(UP_MOVE);
        }if (isInBounds(this.x, this.y - 1) && grid[this.x][this.y - 1].getTileType() == TileType.PATH) {
            validMoves.add(DOWN_MOVE);
        }

        //Randomly pick a move from the list of valid moves or return 'No_Move' if the frog can't move
        int randomDirection = new Random().nextInt(validMoves.size() - 1);
        if(validMoves.size() == 0) {
            return NO_MOVE;
        }else{
            return validMoves.get(randomDirection);
        }
    }

    private Boolean isInBounds(int x, int y) {
        int gridHeight = gameSession.getGridHeight();
        int gridWidth = gameSession.getGridWidth();

        if (x >= 0 && x < gridWidth - 1 && y >= 0 && y < gridHeight - 1) {
            return true;
        } else {
            return false;
        }
    }
}


