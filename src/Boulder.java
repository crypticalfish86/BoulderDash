import javafx.scene.image.Image;

public class Boulder extends FallingObject{


    public static final Image img = new Image("./");//TODO: add the image here









    public Boulder(GameSession gameSession, int x, int y, TileType TileType, long operationInterval){
        super(gameSession, x, y, TileType, operationInterval);
        this.amoebaCanSpreadToThisTile = false;
    }

    public void interact(Tile Tile){
        //TODO implement an interact function for the boulder
    }

    /**
     * Checks if the boulder should fall or roll based on
     * the presence of tiles surrounding it
     * @param currentTimeInMilliseconds
     * The number of milliseconds since 01/01/1970.
     */
    public void updateTile(long currentTimeInMilliseconds) {
        int XPosition = getXPosition();
        int YPosition = getYPosition();

        if (YPosition != 0) { //Check boulder is above the bottom layer of the grid
            Tile tileBelow = gameSession.getTileFromGrid(XPosition,YPosition - 1);
            if (tileBelow instanceof PathWall) { //Check if boulder should fall
                this.fall(XPosition, YPosition);
            }
        }

        if (XPosition != 0 && YPosition != 0) { //Check boulder not on left edge or bottom of grid
            Tile tileToLeft = gameSession.getTileFromGrid(XPosition - 1, YPosition);
            Tile tileLeftBelow = gameSession.getTileFromGrid(XPosition - 1, YPosition - 1);

            //Check if boulder should roll left
            if (tileToLeft instanceof PathWall && tileLeftBelow instanceof PathWall) {
                String direction = "Left";
                this.roll(XPosition, YPosition, direction);
            }
        }


        if (XPosition < (gameSession.getGridWidth() - 1) && YPosition != 0) {//Check boulder not on left edge or bottom of grid
            Tile tileToRight = gameSession.getTileFromGrid(XPosition + 1, YPosition);
            Tile tileRightBelow = gameSession.getTileFromGrid(XPosition + 1, YPosition - 1);

            //Check if boulder should roll right
            if (tileToRight instanceof PathWall && tileRightBelow instanceof PathWall) {
                String direction = "Right";
                this.roll(XPosition, YPosition, direction);
            }
        }
    }
}



