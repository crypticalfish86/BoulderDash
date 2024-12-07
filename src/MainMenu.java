import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;



public class MainMenu {
    



    CanvasCompositor cc;
    CanvasLayer cl;


    Game game;


    public static final Image IMAGE_TITLE = new Image("file:Assets/Buttons/MenuTitle.png");
    public static final Image IMAGE_PLAY = new Image("file:Assets/Buttons/PlayButton.png");
    
    private final String leaderboardDisplay;

    public MainMenu(Game game, CanvasCompositor cc) {
        this.cc = cc;
        this.game = game;

        leaderboardDisplay = new LeaderBoards().getLeaderBoardDisplay();

        boolean[] mouseDownOnPlay = {false};

        this.cl = new CanvasLayer(new CanvasLayer.CanvasLayerI() {
            @Override
            public boolean onMouseDown(double x, double y, boolean hasConsumed) {
                mouseDownOnPlay[0] = isMouseOnPlay(x, y);
                return true;
            }

            @Override
            public boolean onMouseUp(double x, double y, boolean hasConsumed) {
                if (mouseDownOnPlay[0] && isMouseOnPlay(x, y)) {
                    game.onPlayButtonClicked();
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
                UIHelper.drawImageRelativeXX(gc, IMAGE_TITLE, .5, .2, .5);

                
                //draws the lower play button
                UIHelper.drawImageRelativeXX(gc, IMAGE_PLAY, .5, .6, .15);

                gc.fillText(null, elapsed, elapsed);

                drawLeaderboard(gc);
            }
        }, 1);




        cc.addLayer(cl);

        // Create function to check what button you are on
        // This will use the three functions above (mouseUp, mouseDown, mouseMove)


        // System.out.println(leaderboardDisplay);
    }






    /**
     * Returns true if mouse is on the lower play button, used for opening up a profile selector
     * @param mouseX x-position of the mouse
     * @param mouseY y-position of the mouse
     * @return if the mouse is on the play button
     */
    private boolean isMouseOnPlay(double mouseX, double mouseY) {
        //check for play button
        return UIHelper.checkIsXYInBoxRelativeXX(mouseX, mouseY, IMAGE_PLAY, .5, .6, .15);
    }


    /**
     * Hides the main menu from the canvas
     * 
     */
    public void hide() {
        cc.removeLayer(cl);
    }

    /**
     * Shows the main menu
     * 
     */
    public void show() {
        cc.addLayer(cl);
    }










    private void drawLeaderboard(GraphicsContext gc) {
        gc.setTextAlign(TextAlignment.CENTER);

        gc.setFill(new Color(1, 1, 1, 1));
        gc.setFont(new Font(Main.WINDOW_WIDTH * .04));
        gc.fillText(leaderboardDisplay, Main.WINDOW_WIDTH * .8, Main.WINDOW_HEIGHT * .7, Main.WINDOW_WIDTH * .2);
    }
}
