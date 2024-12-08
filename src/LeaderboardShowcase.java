import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * This class represents the canvas display for the leaderboard screen.
 * Once a new class of this type is created, it is initialised
 * inside the game class and is displayed onto the screen. This class
 * is where the player goes to after the winning screen to see the high scores
 * or to check the high scores from the menu.
 * @author Armaan Ghadiali
 * @version 1.6
 */
public class LeaderboardShowcase {
    // Attributes to link the drawing of images and manipulation of layers
    private final CanvasCompositor cc;
    private final CanvasLayer cl;

    // Image files for interactable buttons and non-interactable images
    private static final Image IMAGE_EXIT_TO_MAIN_MENU =
            new Image("file:Assets/Buttons/ExitToMainMenuButton.png");
    private static final Image IMAGE_LEADERBOARD =
            new Image("file:Assets/Buttons/LeaderboardButton.png");

    // The string returned from the Leaderboard backend class
    private final String leaderboardDisplay;

    // Holds whether the mouse has been clicked on within the button's
    // dimensions
    private final boolean[] mouseDownOnExitToMainMenu;

    /**
     * Constructor for the game over screen.
     * Takes in the current game instance and canvas compositor for layering.
     * @param game the current instance of the Game class which holds the
     *             current game session.
     * @param cc used for managing the different layers of the canvas.
     */
    public LeaderboardShowcase(Game game, CanvasCompositor cc) {
        this.cc = cc;

        // Retrieves the output string for the leaderboard
        leaderboardDisplay = new Leaderboard().getLeaderBoardDisplay();

        // Automatically sets this to false since we assume the mouse has not
        // clicked on the button yet
        mouseDownOnExitToMainMenu = new boolean[]{false};

        this.cl = new CanvasLayer(new CanvasLayer.CanvasLayerI() {
            @Override
            public boolean onMouseDown(double x, double y, boolean hasConsumed) {
                mouseDownOnExitToMainMenu[0] = isMouseOnExitToMainMenu(x, y);
                return true;
            }

            @Override
            public boolean onMouseUp(double x, double y, boolean hasConsumed) {
                if (mouseDownOnExitToMainMenu[0] && isMouseOnExitToMainMenu(x, y)) {
                    game.onExitToMainMenuButtonClicked();
                }
                return true;
            }

            @Override
            public boolean onMouseMove(double x, double y, boolean hasConsumed) {
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
                UIHelper.drawImageRelativeXX(gc, IMAGE_LEADERBOARD, .5, .15, .5);

                //draws the lower play button
                UIHelper.drawImageRelativeXX(gc, IMAGE_EXIT_TO_MAIN_MENU, .5, .85, .25);

                gc.fillText(null, elapsed, elapsed);

                // Draws the leaderboard string onto the screen
                drawLeaderboard(gc);
            }
        }, 1);

        // Adds the layer to the canvas for display
        cc.addLayer(cl);
    }

    /**
     * Returns true if mouse is on the end of main menu button,
     * used for returning the user back to the main menu
     * @param mouseX x-position of the mouse
     * @param mouseY y-position of the mouse
     * @return if the mouse is on the play button
     */
    private boolean isMouseOnExitToMainMenu(double mouseX, double mouseY) {
        // Check for exit to main menu button
        return UIHelper.checkIsXYInBoxRelativeXX(mouseX, mouseY, IMAGE_EXIT_TO_MAIN_MENU, .5, .85, .25);
    }


    /**
     * Hides the leaderboard display from the canvas.
     */
    public void hide() {
        cc.removeLayer(cl);
    }

    /**
     * Shows the leaderboard display on the canvas.
     */
    public void show() {
        cc.addLayer(cl);
    }

    /**
     * Sets up and outputs the leaderboard onto the screen.
     * @param gc Used to issue draw calls to the canvas using a buffer.
     */
    private void drawLeaderboard(GraphicsContext gc) {
        // Aligns the text centrally for output string
        gc.setTextAlign(TextAlignment.CENTER);

        // Sets the colour and font of the text and then draws the leaderboard
        // string to the screen
        gc.setFill(new Color(1, 1, 1, 1));
        gc.setFont(new Font(Main.WINDOW_WIDTH * .03));
        gc.fillText(leaderboardDisplay, Main.WINDOW_WIDTH * .5, Main.WINDOW_HEIGHT * .315,
                Main.WINDOW_WIDTH * .4);
    }
}
