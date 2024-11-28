import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Door extends Wall {

    
    public static final Image img = new Image("file:Assets/Images/Door.png"); // Placeholder for the image
    private char doorColour;

    public Door(GameSession gameSession, int x, int y, long operationInterval, char doorColour) {
        super(gameSession, x, y, TileType.STATIC_TILE, operationInterval);
        this.doorColour = doorColour;
        this.amoebaCanSpreadToThisTile = false;
    }

    public char getDoorColour() {
        return this.doorColour;
    }

    public void interact(Tile inputTileObject) {
        if (inputTileObject instanceof Key && ((Key) inputTileObject).getKeyColour() == this.doorColour) {
            System.out.println("Door unlocked with the correct key!");
            // Logic to open the door or remove it
        } else {
            System.out.println("Door cannot be opened without the correct key.");
        }
    }

    public void updateTile(long currentTimeInMilliseconds) {
        System.out.println("Door update logic here."); // Optional update behavior
    }


    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }
}
