import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class GamePauseMenu {
    

    private final CanvasLayer cl;
    private final CanvasCompositor cc;
    private final GameSession gameSession;


    private static final double WINDOW_SIZE_X = .3; // the width of the background of this window
    private static final double WINDOW_SIZE_Y = .5; // the height of the background of this window



    public static final Image IMAGE_RESUME = new Image("file:Assets/Buttons/ResumeButton.png");
    public static final Image IMAGE_SAVE = new Image("file:Assets/Buttons/SaveButton.png");
    public static final Image IMAGE_LOAD = new Image("file:Assets/Buttons/LoadButton.png");
    public static final Image IMAGE_EXIT = new Image("file:Assets/Buttons/ExitButton.png");



    public GamePauseMenu(GameSession gameSession, CanvasCompositor cc) {



        this.gameSession = gameSession;




        boolean[] mouseDownOnResume = {false};
        boolean[] mouseDownOnSave = {false};
        boolean[] mouseDownOnLoad = {false};
        boolean[] mouseDownOnExit = {false};

        this.cl = new CanvasLayer(new CanvasLayer.CanvasLayerI() {




            @Override
            public boolean onMouseDown(double x, double y, boolean hasConsumed) {
                
                //indicates if the mouse has been clicked in any of the given buttons
                mouseDownOnResume[0] = UIHelper.checkIsXYInBoxRelativeYY(x, y, IMAGE_RESUME, .5, .32, .05);
                mouseDownOnSave[0] = UIHelper.checkIsXYInBoxRelativeYY(x, y, IMAGE_SAVE, .5, .44, .05);
                mouseDownOnLoad[0] = UIHelper.checkIsXYInBoxRelativeYY(x, y, IMAGE_LOAD, .5, .56, .05);
                mouseDownOnExit[0] = UIHelper.checkIsXYInBoxRelativeYY(x, y, IMAGE_EXIT, .5, .68, .05);



                return isMouseAtBackground(x, y);
            }





            @Override
            public boolean onMouseUp(double x, double y, boolean hasConsumed) {
                
                //checks for the 4 buttons to determine if any buttons has been clicked
                if (mouseDownOnResume[0] && UIHelper.checkIsXYInBoxRelativeYY(x, y, IMAGE_RESUME, .5, .32, .05)) {
                    resume();
                }
                if (mouseDownOnSave[0] && UIHelper.checkIsXYInBoxRelativeYY(x, y, IMAGE_SAVE, .5, .44, .05)) {
                    save();
                }
                if (mouseDownOnLoad[0] && UIHelper.checkIsXYInBoxRelativeYY(x, y, IMAGE_LOAD, .5, .56, .05)) {
                    load();
                }
                if (mouseDownOnExit[0] && UIHelper.checkIsXYInBoxRelativeYY(x, y, IMAGE_EXIT, .5, .68, .05)) {
                    exit();
                }

                
                return isMouseAtBackground(x, y);
            }




            @Override
            public boolean onMouseMove(double x, double y, boolean hasConsumed) {
                return isMouseAtBackground(x, y);
            }





            @Override
            public void draw(GraphicsContext gc, long elapsed) {
                //draw a shade

                gc.setFill(new Color(.3, .3, .3, .3));
                gc.fillRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);


                //draw the background
                gc.setFill(new Color(.6, .6, .6, .6));
                gc.fillRect(
                    (.5 - WINDOW_SIZE_X / 2) * Main.WINDOW_WIDTH,
                    (.5 - WINDOW_SIZE_Y / 2) * Main.WINDOW_HEIGHT,
                    (WINDOW_SIZE_X) * Main.WINDOW_WIDTH,
                    (WINDOW_SIZE_Y) * Main.WINDOW_HEIGHT
                );
                
                //draws all the 4 buttons
                UIHelper.drawImageRelativeYY(gc, IMAGE_RESUME, .5, .32, .05);
                UIHelper.drawImageRelativeYY(gc, IMAGE_SAVE, .5, .44, .05);
                UIHelper.drawImageRelativeYY(gc, IMAGE_LOAD, .5, .56, .05);
                UIHelper.drawImageRelativeYY(gc, IMAGE_EXIT, .5, .68, .05);
            }



            @Override
            public void onKeyDown(KeyCode key) {
                //left empty because this window has no key inputs to listen to
            }

            @Override
            public void onKeyUp(KeyCode key) {
                //left empty because this window has no key inputs to listen to
            }
        }, 2);

        this.cc = cc;
    }


    /**
     * Checks if the mouse at x and y is on this window tray
     * @param x x-position of the mouse
     * @param y y-position of the mouse
     * @return if the mouse is in the window tray
     */
    public boolean isMouseAtBackground(double x, double y) {
        return (
            x > (.5 - WINDOW_SIZE_X / 2) * Main.WINDOW_WIDTH &&
            x < (.5 + WINDOW_SIZE_X / 2) * Main.WINDOW_WIDTH &&
            y > (.5 - WINDOW_SIZE_Y / 2) * Main.WINDOW_HEIGHT &&
            y < (.5 + WINDOW_SIZE_Y / 2) * Main.WINDOW_HEIGHT
        );
    }


    /**
     * Shows this game pause menu
     */
    public void show() {
        cc.addLayer(cl);
    }


    /**
     * Hides this game pause menu
     */
    public void hide() {
        cc.removeLayer(cl);
    }


    /** fired when the resume button is clicked */
    private void resume() {
        gameSession.resume();
    }


    /** fired when the save button is clicked */
    private void save() {
        //TODO: link saving
    }


    /** fired when the load button is clicked */
    private void load() {
        //TODO: link loading
    }
    

    /** fired when the exit button is clicked */
    private void exit() {
        hide();
        gameSession.endGame();
    }
}