import javafx.scene.image.Image;

public class PathWall extends Wall {



    public static final Image img = new Image("./");//TODO: add the image here



    public PathWall(GameSession gameSession, int x, int y, TileType TileType, long operationInterval) {
        super(gameSession, x, y, TileType, operationInterval);
        this.amoebaCanSpreadToThisTile = true;
    }

    public void interact(Tile Tile){
        //TODO implement an interact function
    }
    




    public void updateTile(long currentTimeInMilliseconds) {
        draw(img, 0, 0);
    }
}