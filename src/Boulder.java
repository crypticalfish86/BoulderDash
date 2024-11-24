import javafx.scene.image.Image;

public class Boulder extends FallingObject{

    private static final String LEFT_DIRECTION = "Left";
    private static final String RIGHT_DIRECTION = "Right";

    public static final Image img = new Image("./");//TODO: add the image here


    public Boulder(GameSession gameSession, int x, int y, TileType TileType, long operationInterval){
        super(gameSession, x, y, TileType, operationInterval);
        this.amoebaCanSpreadToThisTile = false;
    }

    public void interact(Tile tile){
        //TODO implement interact function
    }

    /**
     * Checks if the boulder should fall or roll based on
     * the presence of tiles surrounding it
     * @param currentTimeInMilliseconds
     * The number of milliseconds since 01/01/1970.
     */
    public void updateTile(long currentTimeInMilliseconds) {
        int xPosition = getXPosition();
        int yPosition = getYPosition();

        if (yPosition != 0) { //Check boulder is above the bottom layer of the grid
            Tile tileBelow = gameSession.getTileFromGrid(xPosition,yPosition - 1);

            //Check if boulder should fall
            if (tileBelow instanceof PathWall) {
                this.fall(xPosition, yPosition);
            }
        }

        if (xPosition != 0 && yPosition != 0) { //Check boulder not on left edge or bottom of grid
            Tile tileToLeft = gameSession.getTileFromGrid(xPosition - 1, yPosition);
            Tile tileLeftBelow = gameSession.getTileFromGrid(xPosition - 1, yPosition - 1);

            //Check if boulder should roll left
            if (tileToLeft instanceof PathWall && tileLeftBelow instanceof PathWall) {
                this.roll(xPosition, yPosition, LEFT_DIRECTION);
            }
        }


        if (xPosition < (gameSession.getGridWidth() - 1) && yPosition != 0) {//Check boulder not on left edge or bottom of grid
            Tile tileToRight = gameSession.getTileFromGrid(xPosition + 1, yPosition);
            Tile tileRightBelow = gameSession.getTileFromGrid(xPosition + 1, yPosition - 1);

            //Check if boulder should roll right
            if (tileToRight instanceof PathWall && tileRightBelow instanceof PathWall){
                this.roll(xPosition, yPosition, RIGHT_DIRECTION);
            }
        }
    }
}



