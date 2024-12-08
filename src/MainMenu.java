import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

/**
 * This class represents the canvas display for the main menu.
 * Once a new class of this type is created, it is initialised
 * inside the game class and is displayed onto the screen.
 * @author Armaan Ghadiali
 * @version 1.3
 */
public class MainMenu {
    // Attributes to link the drawing of images and manipulation of layers
    private final CanvasCompositor cc;
    private final CanvasLayer cl;

    // Image files for interactable buttons and non-interactable images
    private static final Image IMAGE_TITLE =
            new Image("file:Assets/Buttons/MenuTitle.png");
    private static final Image IMAGE_PLAY =
            new Image("file:Assets/Buttons/PlayButton.png");
    private static final Image IMAGE_BOARD =
            new Image("file:Assets/Buttons/LeaderboardButton.png");

    // Holds whether the mouse has been clicked on within the button's
    // dimensions
    private final boolean[] mouseDownOnPlay;
    private final boolean[] mouseDownOnLeaderboard;


    /**
     * Constructor for the main menu.
     * Takes in the current game instance and canvas compositor for layering.
     * @param game the current instance of the Game class which holds the
     *             current game session.
     * @param cc used for managing the different layers of the canvas.
     */
    public MainMenu(Game game, CanvasCompositor cc) {
        this.cc = cc;

        // Automatically sets these to false since we assume the mouse has not
        // clicked on the play or leaderboard button yet
        mouseDownOnPlay = new boolean[]{false};
        mouseDownOnLeaderboard = new boolean[]{false};

        // Creates a new canvas layer to manage interaction between the player
        // and the screen
        this.cl = new CanvasLayer(new CanvasLayer.CanvasLayerI() {
            @Override
            public boolean onMouseDown(double x, double y,
                                       boolean hasConsumed) {
                // Checks if mouse is on the play button
                mouseDownOnPlay[0] = isMouseOnPlay(x, y);
                // Checks if mouse is
                mouseDownOnLeaderboard[0] = isMouseOnLeaderboard(x, y);
                return true;
            }

            @Override
            public boolean onMouseUp(double x, double y,
                                     boolean hasConsumed) {
                if (mouseDownOnPlay[0] && isMouseOnPlay(x, y)) {
                    game.onPlayButtonClicked();
                } else if (mouseDownOnLeaderboard[0]
                        && isMouseOnLeaderboard(x, y)) {
                    game.onLeaderboardButtonClicked();
                }
                return true;
            }

            @Override
            public boolean onMouseMove(double x, double y,
                                       boolean hasConsumed) {
                return true;
            }

            @Override
            public void onKeyDown(KeyCode key) {
            }

            @Override
            public void onKeyUp(KeyCode key) {
            }

            @Override
            public void draw(GraphicsContext gc, long elapsed) {
                // gc.setFill(new Color(.15, .1, .05, 1));
                gc.setFill(new Color(.05, .05, .05, 1));
                gc.fillRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

                //draw the title
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_TITLE, .5, .2, .5);

                //draws the lower play button
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_PLAY, .5, .5, .13);

                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_BOARD, .5, .65, .25);
                gc.fillText(null, elapsed, elapsed);
            }
        }, 1);

        // Adds the layer to the canvas for display
        cc.addLayer(cl);
    }

    /**
     * Returns true if mouse is on the play button, used for opening up a
     * profile selector.
     * @param mouseX x-position of the mouse.
     * @param mouseY y-position of the mouse.
     * @return if the mouse is on the play button.
     */
    private boolean isMouseOnPlay(double mouseX, double mouseY) {
        // Check for play button
        return UIHelper.checkIsXYInBoxRelativeXX(
                mouseX, mouseY, IMAGE_PLAY, .5, .5, .13);
    }

    /**
     * Returns true if mouse is on the leaderboard button, used for checking out
     * the current status of the leaderboard (top 10 scores).
     * @param mouseX x-position of the mouse
     * @param mouseY y-position of the mouse
     * @return if the mouse is on the leaderboard button
     */
    private boolean isMouseOnLeaderboard(double mouseX, double mouseY) {
        // Check for leaderboard button
        return UIHelper.checkIsXYInBoxRelativeXX(
                mouseX, mouseY, IMAGE_BOARD, .5, .65, .25);
    }

    /**
     * Hides the main menu from the canvas.
     */
    public void hide() {
        cc.removeLayer(cl);
    }

    /**
     * Shows the main menu canvas layer onto the screen.
     */
    public void show() {
        cc.addLayer(cl);
    }
}
