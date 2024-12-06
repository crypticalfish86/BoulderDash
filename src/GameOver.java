import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class GameOver {
    CanvasCompositor cc;
    CanvasLayer cl;


    Game game;

    public static final Image IMAGE_GAME_OVER = new Image("file:Assets/Buttons/GameOverButton.png");
    public static final Image IMAGE_SCORE = new Image("file:Assets/Buttons/ScoreButton.png");
    public static final Image IMAGE_RETURN_MENU = new Image("file:Assets/Buttons/ExitToMainMenuButton.png");


    public GameOver(Game game, CanvasCompositor cc) {
        this.cc = cc;
        this.game = game;

        boolean[] mouseDownOnExit = {false};

        this.cl = new CanvasLayer(new CanvasLayer.CanvasLayerI() {
            @Override
            public boolean onMouseDown(double x, double y, boolean hasConsumed) {
                mouseDownOnExit[0] = isMouseOnExit(x, y);
                return true;
            }

            @Override
            public boolean onMouseUp(double x, double y, boolean hasConsumed) {
                if (mouseDownOnExit[0] && isMouseOnExit(x, y)) {
                    game.onExitButtonClicked();
                }
                return true;
            }

            @Override
            public boolean onMouseMove(double x, double y, boolean hasConsumed) {
                // TODO Auto-generated method stub
                // getMouseOnButton(x, y);

                return true;
            }

            @Override
            public void onKeyDown(KeyCode key) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onKeyUp(KeyCode key) {
                // TODO Auto-generated method stub

            }

            @Override
            public void draw(GraphicsContext gc, long elapsed) {
                // gc.setFill(new Color(.15, .1, .05, 1));
                gc.setFill(new Color(.05, .05, .05, 1));
                gc.fillRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

                UIHelper.drawImageRelativeXX(gc, IMAGE_GAME_OVER, .5, .2, .5);

                UIHelper.drawImageRelativeXX(gc, IMAGE_SCORE, .4, .45, .15);

                gc.setFill(Color.WHITE);
                gc.setFont(new Font("Arial", Main.WINDOW_HEIGHT * .08));
                String score = String.format("%04d", getPlayerScore());
                gc.fillText(score, Main.WINDOW_WIDTH * .65, Main.WINDOW_HEIGHT * .49);

                UIHelper.drawImageRelativeXX(gc, IMAGE_RETURN_MENU, .5, .8, .3);
            }
        }, 1);




        cc.addLayer(cl);
    }

    public int getPlayerScore() {
        return game.getPlayerScoreForGameOverScreen();
    }

    /**
     * Returns true if mouse is on the lower play button, used for opening up a profile selector
     * @param mouseX x-position of the mouse
     * @param mouseY y-position of the mouse
     * @return if the mouse is on the play button
     */
    private boolean isMouseOnExit(double mouseX, double mouseY) {
        //check for play button
        return UIHelper.checkIsXYInBoxRelativeXX(mouseX, mouseY, IMAGE_RETURN_MENU, .5, .8, .3);
    }

    /**
     * Hides the game over screen from the canvas
     *
     */
    public void hide() {
        cc.removeLayer(cl);
    }

    /**
     * Shows the game over screen on the canvas
     *
     */
    public void show() {
        cc.addLayer(cl);
    }

}

