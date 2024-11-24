import javafx.scene.image.Image;

public class MagicWall extends Wall {


    public static final Image img = new Image("file:Assets/Images/MagicWall.png");//TODO: add the image here

    public MagicWall(GameSession gameSession, int x, int y, TileType TileType, long operationInterval) {
        super(gameSession, x, y, TileType, operationInterval);
        this.amoebaCanSpreadToThisTile = false;
    }

    public void interact(Tile Tile) {
        //TODO implement an interact function
    }
    public void updateTile(long currentTimeInMilliseconds) {
        draw(img, 0, 0);
    }
}
