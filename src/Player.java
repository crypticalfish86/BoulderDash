import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player extends Tile {

    // Image representing the player (replace with actual image path)
    public static final Image img = new Image("file:Assets/Images/Ameoba.png"); // Placeholder for the image
    
    //added because NOBODY DECIDED TO CODE THE PLAYER
    //WTF
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

    

    // Constructor for Player
    public Player(GameSession gameSession, int x, int y, long operationInterval) {
        // Call the parent class (Tile) constructor with the gameSession and other parameters
        super(gameSession, x, y, TileType.PLAYER, operationInterval);
    }

    // Example method to access GameSession
    public void somePlayerLogic() {
        GameSession session = getGameSession(); // This should work now, as Player inherits from Tile
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
    public void interact(Tile other) {
        // Implement interaction logic with other tiles

        //check for tile kills

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
                interact(gameSession.getTileFromGrid(this.getXPosition(), this.getYPosition() + 1));
                System.out.println("moving");
                break;
                
            case 'S':
                //interact with the tile on the right
                interact(gameSession.getTileFromGrid(this.getXPosition(), this.getYPosition() - 1));
                System.out.println("moving");
                break;
            
            case 'A':
                //interact with the tile on the right
                interact(gameSession.getTileFromGrid(this.getXPosition() - 1, this.getYPosition()));
                System.out.println("moving");
                break;
                
            case 'D':
                //interact with the tile on the right
                interact(gameSession.getTileFromGrid(this.getXPosition() + 1, this.getYPosition()));
                System.out.println("moving");
                break;
        }


        
    }

    public void killPlayer() {
    }


    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }
}
