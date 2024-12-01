import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DirtWall extends Wall {

    public static final Image img = new Image("file:Assets/Images/Dirt.png"); // Placeholder for the dirt wall image

    public DirtWall(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.DIRT_WALL, operationInterval);
        this.amoebaCanSpreadToThisTile = true; // Amoebas can spread to dirt walls
    }

    // Interaction logic for the dirt wall
    @Override
    public void interact(Tile inputTileObject) {
        // if (inputTileObject instanceof Player) {
        //     System.out.println("Dirt wall dug out by the player.");

        //     // Remove the dirt wall from the game grid by setting the tile to null
        //     gameSession.setTile(getYPosition(), getXPosition(), null);

        //     // Optionally, you can trigger a visual or audio effect here to represent the wall being dug out
        // }
    }

    // Update logic for dirt walls
    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        // Dirt walls typically don't have dynamic behavior, but this can be extended
    }

    // Draw the dirt wall image
    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0); // Render the dirt wall
    }
}
