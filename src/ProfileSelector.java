import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class ProfileSelector {
    CanvasCompositor cc;
    CanvasLayer cl;

    Game game;

    public static final Image IMAGE_LEVEL1 = new Image("file:Assets/Buttons/LevelOne.png");
    public static final Image IMAGE_LEVEL2 = new Image("file:Assets/Buttons/LevelTwo.png");
    public static final Image IMAGE_LEVEL3 = new Image("file:Assets/Buttons/LevelThree.png");
    public static final Image IMAGE_LEVEL4 = new Image("file:Assets/Buttons/LevelFour.png");
    public static final Image IMAGE_LEVEL5 = new Image("file:Assets/Buttons/LevelFive.png");
    public static final Image IMAGE_BACK = new Image("file:Assets/Buttons/BackButton.png");
    public static final Image IMAGE_NEW_SAVE = new Image("file:Assets/Buttons/NewSaveButton.png");
    public static final Image IMAGE_PROFILE_BOX = new Image("file:Assets/Buttons/ProfileBox.png");
    public static final Image IMAGE_PROFILE1 = new Image("file:Assets/Buttons/ProfileOne.png");
    public static final Image IMAGE_PROFILE2 = new Image("file:Assets/Buttons/ProfileTwo.png");
    public static final Image IMAGE_PROFILE3 = new Image("file:Assets/Buttons/ProfileThree.png");

    public ProfileSelector(Game game, CanvasCompositor cc) {
        this.game = game;
        this.cc = cc;

        boolean[] mouseDownOnBackButton = {false};
        boolean[] mouseDownOnProfileSelector1 = {false};
        boolean[] mouseDownOnProfileSelector2 = {false};
        boolean[] mouseDownOnProfileSelector3 = {false};

        this.cl = new CanvasLayer(new CanvasLayer.CanvasLayerI() {
            @Override
            public boolean onMouseDown(double x, double y, boolean hasConsumed) {
                // mouseDownOnPlay[0] = isMouseOnPlay(x, y);
                return true;
            }

            @Override
            public boolean onMouseUp(double x, double y, boolean hasConsumed) {
                // if (mouseDownOnPlay[0] && isMouseOnPlay(x, y)) {
                //     game.onPlayButtonClicked();
                // }
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
                gc.setFill(new Color(.05, .05, .05, 1));
                gc.fillRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);


                //draw the title
                UIHelper.drawImageRelativeXX(gc, IMAGE_BACK, .5, .2, .1);
                UIHelper.drawImageRelativeXX(gc, IMAGE_PROFILE_BOX, .5, .8, .1);


            }
        }, 1);

        cc.addLayer(cl);
    }


    private boolean isMouseOnBack(double mouseX, double mouseY) {

        //check for play button
        return UIHelper.checkIsXYInBox(mouseX, mouseY, IMAGE_BACK, .5, .8, .1);
    }




    public void hide() {
        cc.removeLayer(cl);
    }

    public void show() {
        cc.addLayer(cl);
    }
}
