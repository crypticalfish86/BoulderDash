import javafx.scene.image.Image;

public class Diamond extends FallingObject{



    private final static int SCORE_VALUE = 50;
    public static final Image img = new Image("file:Assets/Images/Diamond.png");//TODO: add the image here




    public Diamond(GameSession gameSession, int x, int y, TileType TileType, long operationInterval){
        super(gameSession, x, y, TileType, operationInterval);
        this.amoebaCanSpreadToThisTile = false;
    }

    public void interact(Tile Tile){
        //TODO implement an interact function
    }
    public void updateTile(long currentTimeInMilliseconds){
        int XPosition = getXPosition();
        int YPosition = getYPosition();

        if (YPosition != 0) { //Check boulder is above the bottom layer of the grid
            Tile tileBelow = gameSession.getTileFromGrid(XPosition,YPosition - 1);
            if (tileBelow instanceof PathWall) { //Check if diamond should fall
                this.fall(XPosition, YPosition);
            }
        }

        if (XPosition != 0 && YPosition != 0) { //Check boulder not on left edge or bottom of grid
            Tile tileToLeft = gameSession.getTileFromGrid(XPosition - 1, YPosition);
            Tile tileLeftBelow = gameSession.getTileFromGrid(XPosition - 1, YPosition - 1);

            //Check if diamond should roll left
            if (tileToLeft instanceof PathWall && tileLeftBelow instanceof PathWall) {
                String direction = "Left";
                this.roll(XPosition, YPosition, direction);
            }
        }

        if (XPosition < (gameSession.getGridWidth() - 1) && YPosition != 0) {//Check boulder not on left edge or bottom of grid
            Tile tileToRight = gameSession.getTileFromGrid(XPosition + 1, YPosition);
            Tile tileRightBelow = gameSession.getTileFromGrid(XPosition + 1, YPosition - 1);

            //Check if diamond should roll right
            if (tileToRight instanceof PathWall && tileRightBelow instanceof PathWall) {
                String direction = "Right";
                this.roll(XPosition, YPosition, direction);
            }
        }

        draw(img, 0, 0);
    }
}
