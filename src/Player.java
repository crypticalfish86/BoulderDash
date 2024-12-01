import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player extends Tile {

    public static final Image img = new Image("file:Assets/Images/Ameoba.png"); // Placeholder for the image

    public Player(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.PLAYER, operationInterval);
    }

    public void somePlayerLogic() {
        GameSession session = getGameSession();
        if (session != null) {
            System.out.println("Game Session is available.");
        }
    }

    public void onKeyPressed(String key) {
        System.out.println("Key " + key + " pressed.");
        switch (key.toLowerCase()) {
            case "w": move(0, -1); break;
            case "a": move(-1, 0); break;
            case "s": move(0, 1); break;
            case "d": move(1, 0); break;
            default: System.out.println("Unrecognized key: " + key); break;
        }
    }

    public void onKeyReleased(String key) {
        System.out.println("Key " + key + " released.");
    }

    private void move(int deltaX, int deltaY) {
        int newX = getXPosition() + deltaX;
        int newY = getYPosition() + deltaY;
        if (canMoveTo(newX, newY)) {
            setNewPosition(newX, newY);
            System.out.println("Player moved to position: (" + newX + ", " + newY + ")");
        } else {
            System.out.println("Cannot move to position: (" + newX + ", " + newY + ")");
        }
    }

    private boolean canMoveTo(int x, int y) {
        return x >= 0 && y >= 0 && x < getGameSession().getGridWidth() && y < getGameSession().getGridHeight();
    }

    @Override
    public void interact(Tile other) {
        System.out.println("Player interacting with tile: " + other.getTileType());
    }

    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        draw(this.gameSession.getGraphicsContext(), img, getXPosition(), getYPosition());
    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }

    // Add the killPlayer method
    public void killPlayer() {
        System.out.println("Player has been killed.");
        // Example: Trigger game-over logic
        gameSession.endGame(); // Assuming endGame is implemented in GameSession
    }
}
