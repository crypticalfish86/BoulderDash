import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Diamond extends FallingObject{

    private final static int SCORE_VALUE = 50;
    
    public static final Image img = new Image("file:Assets/Images/Diamond.png");//TODO: add the image here


    private static final String LEFT_DIRECTION = "Left";
    private static final String RIGHT_DIRECTION = "Right";





    public Diamond(GameSession gameSession, int x, int y, long operationInterval){
        super(gameSession, x, y, TileType.FALLING_OBJECT, operationInterval);
        this.amoebaCanSpreadToThisTile = false;
    }

    /**
     * Moves player to current tile and deletes diamond if player interacts
     * with this tile. Also updates the score and diamond count for
     * the current game session.
     * @param tile the tile that is interacting with this tile
     */
    public void interact(Tile tile){
        if(tile.getTileType() == TileType.PLAYER){
            PathWall pathWall = new PathWall(gameSession, tile.getXPosition(), tile.getYPosition(),
                    TileType.STATIC_TILE,getOperationInterval());
            gameSession.updateTilePositions(pathWall, tile,this); //Move player to this tile and delete diamond

            updateGameSessionData();
        }
    }

    /**
     * Checks if the diamond should fall or roll based on
     * the presence of tiles surrounding it
     * @param currentTimeInMilliseconds
     * The number of milliseconds since 01/01/1970.
     */
    public void updateTile(long currentTimeInMilliseconds){
        int xPosition = getXPosition();
        int yPosition = getYPosition();

        if (yPosition != 0) { //Check boulder is above the bottom layer of the grid
            Tile tileBelow = gameSession.getTileFromGrid(xPosition,yPosition - 1);

            //Check if diamond should fall
            if (tileBelow instanceof PathWall) {
                this.fall(xPosition, yPosition);
            }
        }

        if (xPosition != 0 && yPosition != 0) { //Check boulder not on left edge or bottom of grid
            Tile tileToLeft = gameSession.getTileFromGrid(xPosition - 1, yPosition);
            Tile tileLeftBelow = gameSession.getTileFromGrid(xPosition - 1, yPosition - 1);

            //Check if diamond should roll left
            if (tileToLeft instanceof PathWall && tileLeftBelow instanceof PathWall) {
                this.roll(xPosition, yPosition, LEFT_DIRECTION);
            }
        }

        if (xPosition < (gameSession.getGridWidth() - 1) && yPosition != 0) {//Check boulder not on left edge or bottom of grid
            Tile tileToRight = gameSession.getTileFromGrid(xPosition + 1, yPosition);
            Tile tileRightBelow = gameSession.getTileFromGrid(xPosition + 1, yPosition - 1);

            //Check if diamond should roll right
            if (tileToRight instanceof PathWall && tileRightBelow instanceof PathWall) {
                this.roll(xPosition, yPosition, RIGHT_DIRECTION);
            }
        }

        // draw(img, 0, 0);
    }


    private void updateGameSessionData(){
        GameSessionData currentSessionData = gameSession.getCurrentSessionData();
        currentSessionData.updateScore(SCORE_VALUE); //Update player's score
        currentSessionData.incrementDiamondCount(); //update player's diamond count
    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }
}
