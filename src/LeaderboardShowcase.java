import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class LeaderboardShowcase {
    CanvasCompositor cc;
    CanvasLayer cl;


    Game game;

    public static final Image IMAGE_EXIT_TO_MAIN_MENU =
            new Image("file:Assets/Buttons/ExitToMainMenuButton.png");
    public static final Image IMAGE_LEADERBOARD =
            new Image("file:Assets/Buttons/LeaderboardButton.png");

    private String leaderboardDisplay;

    public LeaderboardShowcase(Game game, CanvasCompositor cc) {
        this.cc = cc;
        this.game = game;

        leaderboardDisplay = new LeaderBoards().getLeaderBoardDisplay();

        boolean[] mouseDownOnExitToMainMenu = {false};

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


                //draw the title
                UIHelper.drawImageRelativeXX(gc, IMAGE_LEADERBOARD, .5, .15, .5);


                //draws the lower play button
                UIHelper.drawImageRelativeXX(gc, IMAGE_EXIT_TO_MAIN_MENU, .5, .85, .25);

                gc.fillText(null, elapsed, elapsed);

                drawLeaderboard(gc);
            }
        }, 1);

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
        //check for play button
        return UIHelper.checkIsXYInBoxRelativeXX(mouseX, mouseY, IMAGE_EXIT_TO_MAIN_MENU, .5, .85, .25);
    }


    /**
     * Hides the leaderboard display from the canvas
     *
     */
    public void hide() {
        cc.removeLayer(cl);
    }

    /**
     * Shows the leaderboard display on the canvas
     *
     */
    public void show() {
        cc.addLayer(cl);
    }

    private void drawLeaderboard(GraphicsContext gc) {
        gc.setTextAlign(TextAlignment.CENTER);

        gc.setFill(new Color(1, 1, 1, 1));
        gc.setFont(new Font(Main.WINDOW_WIDTH * .03));
        gc.fillText(leaderboardDisplay, Main.WINDOW_WIDTH * .5, Main.WINDOW_HEIGHT * .315,
                Main.WINDOW_WIDTH * .4);
    }
}
