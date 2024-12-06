import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;




public class GameOver {
    CanvasCompositor cc;
    CanvasLayer cl;


    Game game;

    public static final Image IMAGE_GAME_OVER = new Image("file:Assets/Buttons/GameOverButton.png");
    public static final Image IMAGE_SCORE = new Image("file:Assets/Buttons/ScoreButton.png");


    public GameOver(Game game, CanvasCompositor cc) {
        this.cc = cc;
        this.game = game;

        this.cl = new CanvasLayer(new CanvasLayer.CanvasLayerI() {
            @Override
            public boolean onMouseDown(double x, double y, boolean hasConsumed) {
                return true;
            }

            @Override
            public boolean onMouseUp(double x, double y, boolean hasConsumed) {
                /*
                if (mouseDownOnPlay[0] && isMouseOnPlay(x, y)) {
                    game.onPlayButtonClicked();
                }
                 */
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

                UIHelper.drawImageRelativeXX(gc, IMAGE_SCORE, .3, .6, .1);

            }
        }, 1);




        cc.addLayer(cl);
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

