import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;



public class MainMenu {
    



    CanvasCompositor cc;
    CanvasLayer cl;


    Game game;


    public static final Image IMAGE_TITLE = new Image("file:Assets/Buttons/MenuTitle.png");
    public static final Image IMAGE_PLAY = new Image("file:Assets/Buttons/PlayButton.png");
    


    public MainMenu(Game game, CanvasCompositor cc) {
        this.cc = cc;
        this.game = game;









        boolean[] mouseDownOnPlay = {false};
        boolean[] mouseDownOnPlay2 = {false};

        this.cl = new CanvasLayer(new CanvasLayer.CanvasLayerI() {
            @Override
            public boolean onMouseDown(double x, double y, boolean hasConsumed) {
                mouseDownOnPlay[0] = isMouseOnPlay(x, y);
                mouseDownOnPlay2[0] = isMouseOnPlay2(x, y);
                return true;
            }

            @Override
            public boolean onMouseUp(double x, double y, boolean hasConsumed) {
                if (mouseDownOnPlay[0] && isMouseOnPlay(x, y)) {
                    game.onPlayButtonClicked();
                }

                if (mouseDownOnPlay2[0] && isMouseOnPlay2(x, y)) {
                    game.startSampleGame();
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
                UIHelper.drawImageRelativeXX(gc, IMAGE_TITLE, .5, .2, .25);

                
                //
                UIHelper.drawImageRelativeXX(gc, IMAGE_PLAY, .5, .8, .1);

                
                //
                UIHelper.drawImageRelativeXX(gc, IMAGE_PLAY, .5, .5, .1);

                gc.fillText(null, elapsed, elapsed);


            }
        }, 1);




        cc.addLayer(cl);

        // Create function to check what button you are on
        // This will use the three functions above (mouseUp, mouseDown, mouseMove)

    }







    private boolean isMouseOnPlay(double mouseX, double mouseY) {
        //check for play button
        return UIHelper.checkIsXYInBox(mouseX, mouseY, IMAGE_PLAY, .5, .8, .1);
    }

    private boolean isMouseOnPlay2(double mouseX, double mouseY) {
        //check for play button
        return UIHelper.checkIsXYInBox(mouseX, mouseY, IMAGE_PLAY, .5, .5, .1);
    }

    public void hide() {
        cc.removeLayer(cl);
    }

    public void show() {
        cc.addLayer(cl);
    }
}
