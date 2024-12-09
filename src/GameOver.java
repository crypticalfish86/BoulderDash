import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class represents the canvas display for the game over screen.
 * Once a new class of this type is created, it is initialised
 * inside the game class and is displayed onto the screen. This class
 * is where the player goes to when they die or the timer runs out.
 * @author Armaan Ghadiali
 * @version 2.0
 */
public class GameOver extends DisplayLayer {
    // Attributes to link the drawing of images and manipulation of layers
    private final CanvasCompositor cc;
    private final CanvasLayer cl;

    // Includes data for the game stored in the GameSessionData class
    private final GameSessionData gameSessionData;

    // Image files for interactable buttons and non-interactable images
    private static final Image IMAGE_GAME_OVER =
            new Image("file:Assets/Buttons/GameOverButton.png");
    private static final Image IMAGE_SCORE =
            new Image("file:Assets/Buttons/ScoreButton.png");
    private static final Image IMAGE_RETURN_MENU =
            new Image("file:Assets/Buttons/ExitToMainMenuButton.png");
    private static final Image IMAGE_LEVEL1 =
            new Image("file:Assets/Buttons/LevelOne.png");
    private static final Image IMAGE_LEVEL2 =
            new Image("file:Assets/Buttons/LevelTwo.png");
    private static final Image IMAGE_LEVEL3 =
            new Image("file:Assets/Buttons/LevelThree.png");
    private static final Image IMAGE_LEVEL4 =
            new Image("file:Assets/Buttons/LevelFour.png");
    private static final Image IMAGE_LEVEL5 =
            new Image("file:Assets/Buttons/LevelFive.png");

    // Text files which import the current state of the profiles
    private final File profile1 =
            new File("Profiles/profile1.txt");
    private final File profile2 =
            new File("Profiles/profile2.txt");
    private final File profile3 =
            new File("Profiles/profile3.txt");

    // Holds whether the mouse has been clicked on within the button's
    // dimensions
    private final boolean[] mouseDownOnExit;

    /**
     * Constructor for the game over screen.
     * Takes in the current game instance and canvas compositor for layering.
     * @param game the current instance of the Game class which holds the
     *             current game session.
     * @param cc used for managing the different layers of the canvas.
     */
    public GameOver(Game game, CanvasCompositor cc, GameSessionData gameSessionData) {
        this.cc = cc;
        this.gameSessionData = gameSessionData;

        // Automatically sets this to false since we assume the mouse has not
        // clicked on the button yet
        mouseDownOnExit = new boolean[]{false};

        // Creates a new canvas layer to manage interaction between the player
        // and the screen
        this.cl = new CanvasLayer(new CanvasLayer.CanvasLayerI() {
            @Override
            public boolean onMouseDown(
                    double x, double y, boolean hasConsumed) {
                mouseDownOnExit[0] = isMouseOnExit(x, y);
                return true;
            }

            @Override
            public boolean onMouseUp(
                    double x, double y, boolean hasConsumed) {
                if (mouseDownOnExit[0] && isMouseOnExit(x, y)) {
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
            }

            @Override
            public void onKeyUp(KeyCode key) {
            }

            @Override
            public void draw(GraphicsContext gc, long elapsed) {
                // Fills window colour with a variant of black
                gc.setFill(new Color(.05, .05, .05, 1));
                gc.fillRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

                // Draws the game over image
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_GAME_OVER, .5, .2, .5);

                // Draws the score image
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_SCORE, .4, .45, .15);

                // Displays current level
                displayLevel(gc, .5, .6, .2);

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
            }
        }, 1);

        // Adds the layer to the canvas for display
        cc.addLayer(cl);
    }

    /**
     * Outputs the relevant level image based on the current level that the
     * player died on
     * @param gc Issues draw calls to a Canvas using a buffer.
     * @param xPos X-position for where the image is to be placed horizontally.
     * @param yPos Y-position for where the image is to be placed vertically.
     * @param size Double value to indicate the size of the image.
     */
    private void displayLevel(GraphicsContext gc,
                              double xPos, double yPos, double size) {
        switch (gameSessionData.getLevel()) {
            case 1:
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_LEVEL1, xPos, yPos, size);
            case 2:
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_LEVEL2, xPos, yPos, size);
            case 3:
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_LEVEL3, xPos, yPos, size);
            case 4:
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_LEVEL4, xPos, yPos, size);
            case 5:
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_LEVEL5, xPos, yPos, size);
        }
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
        return UIHelper.checkIsXYInBoxRelativeXX(
                mouseX, mouseY, IMAGE_RETURN_MENU, .5, .8, .3);
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

}

