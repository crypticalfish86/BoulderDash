import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class GameWin extends DisplayLayer {
    // Attributes to link the drawing of images and manipulation of layers
    private final CanvasCompositor cc;
    private final CanvasLayer cl;
    private final Game game;


    private boolean hasAddedScore = false;



    private final UITextBox textBox;

    // Includes data for the game stored in the GameSessionData class
    private final GameSessionData gameSessionData;

    // Image files for interactable buttons and non-interactable images
    private static final Image IMAGE_GAME_COMPLETE =
            new Image("file:Assets/Buttons/GameComplete.png");
    private static final Image IMAGE_SCORE =
            new Image("file:Assets/Buttons/ScoreButton.png");
    private static final Image IMAGE_RETURN_MENU =
            new Image("file:Assets/Buttons/ExitToMainMenuButton.png");

    // Holds whether the mouse has been clicked on within the button's
    // dimensions
    private final boolean[] mouseDownOnExit;

    /**
     * Constructor for the game win screen.
     * Takes in the current game instance and canvas compositor for layering.
     * @param game the current instance of the Game class which holds the
     *             current game session.
     * @param cc used for managing the different layers of the canvas.
     */
    public GameWin(Game game, CanvasCompositor cc,
                   GameSessionData gameSessionData) {
        this.cc = cc;
        this.game = game;
        this.gameSessionData = gameSessionData;

        // Automatically sets this to false since we assume the mouse has not
        // clicked on the button yet
        mouseDownOnExit = new boolean[]{false};

        this.textBox = new UITextBox(.5, .75, .7, .15, new Color(1, 1, 1, 1));

        // Creates a new canvas layer to manage interaction between the player
        // and the screen
        this.cl = new CanvasLayer(new CanvasLayer.CanvasLayerI() {
            @Override
            public boolean onMouseDown(double x, double y, boolean hasConsumed) {

                textBox.onMouseDown(x, y);
                mouseDownOnExit[0] = isMouseOnExit(x, y);
                return true;
            }

            @Override
            public boolean onMouseUp(double x, double y, boolean hasConsumed) {

                textBox.onMouseUp(x, y);
                if (mouseDownOnExit[0] && isMouseOnExit(x, y)) {
                    addScoreToLeaderboard(textBox.getText());
                    game.onExitButtonClicked();
                }
                return true;
            }

            @Override
            public boolean onMouseMove(
                    double x, double y, boolean hasConsumed) {
                return true;
            }

            @Override
            public void onKeyDown(KeyCode key) {
                textBox.onKeyDown(key);
            }

            @Override
            public void onKeyUp(KeyCode key) {
                textBox.onKeyUp(key);
            }

            @Override
            public void draw(GraphicsContext gc, long elapsed) {
                // gc.setFill(new Color(.15, .1, .05, 1));
                gc.setFill(new Color(.05, .05, .05, 1));
                gc.fillRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

                // Draws the game complete image
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_GAME_COMPLETE, .5, .2, .5);

                // Draws the score image
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_SCORE, .4, .45, .15);



                // Sets the text colour to white and writes out the user's score
                // at the end of the level
                gc.setFill(Color.WHITE);
                gc.setFont(new Font("Arial", Main.WINDOW_HEIGHT * .08));
                String score = String.format("%04d",
                        gameSessionData.getScore());
                gc.fillText(score, Main.WINDOW_WIDTH * .65,
                        Main.WINDOW_HEIGHT * .49);

                // Draws the return to menu button
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_RETURN_MENU, .5, .8, .3);

                // Creates textbox for user to enter text into
                if (textBox.getIsFocused()) {
                        gc.setFill(new Color(.4, .4, .4, 1));
                } else {
                        gc.setFill(new Color(.7, .7, .7, 1));
                }
                gc.fillRect(
                        .15 * Main.WINDOW_WIDTH, .625 * Main.WINDOW_HEIGHT,
                        .7 * Main.WINDOW_WIDTH, .15 * Main.WINDOW_HEIGHT
                );

                textBox.draw(gc);
            }
        }, 1);

        // Adds the layer to the canvas for display
        cc.addLayer(cl);
    }

    /**
     * Returns true if mouse is on the exit button, used for returning back to
     * the main menu to replay the game.
     * @param mouseX x-position of the mouse.
     * @param mouseY y-position of the mouse.
     * @return if the mouse is on the play button.
     */
    private boolean isMouseOnExit(double mouseX, double mouseY) {
        //check for play button
        return UIHelper.checkIsXYInBoxRelativeXX(mouseX,
                mouseY, IMAGE_RETURN_MENU, .5, .8, .3);
    }

    /**
     * Hides the game over screen from the canvas.
     */
    public void hide() {
        cc.removeLayer(cl);
    }

    /**
     * Shows the game over screen on the canvas.
     */
    public void show() {
        cc.addLayer(cl);
    }

    /**
     * Adds a score to the leaderboard
     * @param name the name to be written next to the score
     */
    private void addScoreToLeaderboard(String name) {
        
        String finalName = name;
        if (finalName.isEmpty()) {
                finalName = "Unknown";
        }

        if (!hasAddedScore) {
            hasAddedScore = true;
            new Leaderboard().writeNewNameToLeaderboard(finalName, gameSessionData.getScore());
        }
    }
}
