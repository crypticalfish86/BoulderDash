import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class Player extends Tile {

    public static final Image img = new Image("file:Assets/Images/PlayerForward.png"); // Placeholder for the image
    
    //added because NOBODY DECIDED TO CODE THE PLAYER
    private boolean keyUp = false;
    private boolean keyDown = false;
    private boolean keyLeft = false;
    private boolean keyRight = false;

    //either stores W, S, A, D or space (no input currently)
    private char currentDirection = ' ';
    private char lastDirection = ' ';
    private int recurringFrameCount = 0;

    public static final int RECURRING_DIRECTION_SPEED_UP = 4;
    public static final int RECURRING_DIRECTION_INITIAL_SPEED = 20;
    public static final int RECURRING_DIRECTION_FAST_SPEED = 7; // in frames

    

    public Player(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.PLAYER, operationInterval);
        // Initialize the inventory, not needed because you should call the game session data, and key should decide the logic instead of the player
    }

    public void somePlayerLogic() {
        GameSession session = getGameSession();
        if (session != null) {
            System.out.println("Game Session is available.");
        }
    }





    // Handle player input (example)
    public void onKeyPressed(char key) {
        
        // setting these boolean allows us to check which key the player is inputting
        switch (key) {
            case 'W':
                keyUp = true;
                currentDirection = 'W';
                break;
            case 'S':
                keyDown = true;
                currentDirection = 'S';
                break;
            case 'A':
                keyLeft = true;
                currentDirection = 'A';
                break;
            case 'D':
                keyRight = true;
                currentDirection = 'D';
                break;
            default:
                System.out.printf("Warning: input down not supported in player: %c\n", key);
        }
    }

    public void onKeyReleased(char key) {

        // setting these boolean allows us to check which key the player is inputting
        switch (key) {
            case 'W':
                keyUp = false;
                break;
            case 'S':
                keyDown = false;
                break;
            case 'A':
                keyLeft = false;
                break;
            case 'D':
                keyRight = false;
                break;
            default:
                System.out.printf("Warning: input up not supported in player: %c\n", key);
        }

        //find out which key is still down
        if (keyUp) {
            currentDirection = 'W';
        } else if (keyDown) {
            currentDirection = 'S';
        } else if (keyLeft) {
            currentDirection = 'A';
        } else if (keyRight) {
            currentDirection = 'D';
        } else {
            currentDirection = ' ';
        }

    }

    @Override
    public void interact(Tile tile) {
        if(tile.getTileType() == TileType.FALLING_OBJECT && tile.getYPosition() == this.y + 1) {
            PathWall pathWall = new PathWall(gameSession, this.x, this.y + 1, operationInterval );
            gameSession.updateTilePositions(pathWall, tile, this);
            this.killPlayer();
        }


    }

    @Override
    public void updateTile(long currentTimeInMilliseconds) {
        if (currentDirection != lastDirection) {
            //resets the counter
            recurringFrameCount = 0;

        } else {
            ++recurringFrameCount;

        }
        lastDirection = currentDirection;

        int framesRequiredToSpeedUp = RECURRING_DIRECTION_INITIAL_SPEED * RECURRING_DIRECTION_SPEED_UP;
        boolean shouldMove;
        if (recurringFrameCount > framesRequiredToSpeedUp) {
            shouldMove = recurringFrameCount % RECURRING_DIRECTION_INITIAL_SPEED == 0;
        } else {
            shouldMove = (recurringFrameCount - framesRequiredToSpeedUp) % RECURRING_DIRECTION_FAST_SPEED == 0;
        }

        
        if (!shouldMove) { return; } // if this tile should not move then nothing happens


        //interact with the respective direction
        
        switch (currentDirection) {
            case 'W':
                //interact with the tile on the right
                gameSession.getTileFromGrid(this.getXPosition(), this.getYPosition() - 1).interact(this);
                break;
                
            case 'S':
                //interact with the tile on the right
                gameSession.getTileFromGrid(this.getXPosition(), this.getYPosition() + 1).interact(this);
                break;
            
            case 'A':
                //interact with the tile on the right
                gameSession.getTileFromGrid(this.getXPosition() - 1, this.getYPosition()).interact(this);
                break;
                
            case 'D':
                //interact with the tile on the right
                gameSession.getTileFromGrid(this.getXPosition() + 1, this.getYPosition()).interact(this);
                break;
        }


        
    }

    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }



    // Add the killPlayer method
    public void killPlayer() {
        System.out.println("Player has been killed.");
        gameSession.endGame(); // Assuming endGame is implemented in GameSession
    }

    // // Add key to the player's inventory
    // public void addKeyToInventory(Key key) {
    //     inventory.add(key);
    //     System.out.println("Key of color " + key.getKeyColour() + " added to inventory.");
    // }

    // // Get the player's inventory
    // public List<Key> getInventory() {
    //     return inventory;
    // }
}
