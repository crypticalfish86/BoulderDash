import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ProfileSelector extends DisplayLayer {
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

    public File profile1 = new File("Profiles/profile1.txt");
    public File profile2 = new File("Profiles/profile2.txt");
    public File profile3 = new File("Profiles/profile3.txt");
    public boolean profileOneSelected = false;
    public boolean profileTwoSelected = false;
    public boolean profileThreeSelected = false;

    public ProfileSelector(Game game, CanvasCompositor cc) {
        this.game = game;
        this.cc = cc;

        boolean[] mouseDownOnBackButton = {false};
        boolean[] mouseDownOnProfileBox1 = {false};
        boolean[] mouseDownOnProfileBox2 = {false};
        boolean[] mouseDownOnProfileBox3 = {false};

        this.cl = new CanvasLayer(new CanvasLayer.CanvasLayerI() {
            @Override
            public boolean onMouseDown(double x, double y, boolean hasConsumed) {
                mouseDownOnBackButton[0] = isMouseOnBackButton(x, y);
                mouseDownOnProfileBox1[0] = isMouseOnProfileBox1(x, y);
                mouseDownOnProfileBox2[0] = isMouseOnProfileBox2(x, y);
                mouseDownOnProfileBox3[0] = isMouseOnProfileBox3(x, y);
                return true;
            }

            @Override
            public boolean onMouseUp(double x, double y, boolean hasConsumed) {
                if (mouseDownOnBackButton[0] && isMouseOnBackButton(x, y)) {
                    game.onBackButtonClicked();
                }
                if (mouseDownOnProfileBox1[0] && isMouseOnProfileBox1(x, y)) {
                    game.onProfileBoxClicked1();
                    profileOneSelected = true;
                }
                if (mouseDownOnProfileBox2[0] && isMouseOnProfileBox2(x, y)) {
                    game.onProfileBoxClicked2();
                    profileTwoSelected = true;
                }
                if (mouseDownOnProfileBox3[0] && isMouseOnProfileBox3(x, y)) {
                    game.onProfileBoxClicked3();
                    profileThreeSelected = true;
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
                gc.setFill(new Color(.05, .05, .05, 1));
                gc.fillRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);


                // draws the back button
                UIHelper.drawImageRelativeXX(gc, IMAGE_BACK, .15, .1, .15);

                UIHelper.drawImageRelativeXX(gc, IMAGE_PROFILE_BOX, .5, .2, .4);
                UIHelper.drawImageRelativeXX(gc, IMAGE_PROFILE_BOX, .5, .5, .4);
                UIHelper.drawImageRelativeXX(gc, IMAGE_PROFILE_BOX, .5, .8, .4);

                UIHelper.drawImageRelativeXX(gc, IMAGE_PROFILE1, .4, .15, .17);
                UIHelper.drawImageRelativeXX(gc, IMAGE_PROFILE2, .4, .45, .17);
                UIHelper.drawImageRelativeXX(gc, IMAGE_PROFILE3, .4, .75, .17);

                UIHelper.drawImageRelativeXX(gc, IMAGE_PROFILE1, .4, .15, .17);
                UIHelper.drawImageRelativeXX(gc, IMAGE_PROFILE2, .4, .45, .17);
                UIHelper.drawImageRelativeXX(gc, IMAGE_PROFILE3, .4, .75, .17);

                // TODO Auto-generated method stub
                // If statement for determining what is outputted onto the screen (level or new save image)

                // Level format for profiles/levels
                // line 1: Current Level, Height, Width
                // Line 2: Score, TimeLeft, TimeAllowed
                // Line 3: DiamondCount, DiamondsRequired
                // Line 4: AmeobaSpreadRate, AmeobaSizeLimit
                // Line 5: RedKey, BlueKey, YellowKey, GreenKey
                // Line 6+: Actual level
                displaySaveStatus(gc, profile1, .6, .29, .17);
                displaySaveStatus(gc, profile2, .6, .59, .17);
                displaySaveStatus(gc, profile3, .6, .89, .17);
            }
        }, 1);

        cc.addLayer(cl);
    }

    private void displaySaveStatus(GraphicsContext gc, File profile, double xPos, double yPos, double width) {
        int currentLine = 0;
        try {
            Scanner input = new Scanner(profile);
            while (input.hasNextLine() && currentLine < 1) {
                String[] saveCheck = input.nextLine().split(";");
                if (saveCheck[0].equals("new")) {
                    UIHelper.drawImageRelativeXX(gc, IMAGE_NEW_SAVE, xPos, yPos, width);
                } else {
                    if (saveCheck[0].equals("1")) {
                        UIHelper.drawImageRelativeXX(gc, IMAGE_LEVEL1, xPos, yPos, width);
                    } else if (saveCheck[0].equals("2")) {
                        UIHelper.drawImageRelativeXX(gc, IMAGE_LEVEL2, xPos, yPos, width);
                    } else if (saveCheck[0].equals("3")) {
                        UIHelper.drawImageRelativeXX(gc, IMAGE_LEVEL3, xPos, yPos, width);
                    }
                }
                currentLine++;
            }
            input.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    private boolean isMouseOnBackButton(double mouseX, double mouseY) {
        //check for back button
        return UIHelper.checkIsXYInBoxRelativeXX(mouseX, mouseY, IMAGE_BACK, .15, .1, .15);
    }

    private boolean isMouseOnProfileBox1(double mouseX, double mouseY) {
        //check for profile button
        return UIHelper.checkIsXYInBoxRelativeXX(mouseX, mouseY, IMAGE_PROFILE_BOX, .5, .2, .4);
    }

    private boolean isMouseOnProfileBox2(double mouseX, double mouseY) {
        //check for profile button
        return UIHelper.checkIsXYInBoxRelativeXX(mouseX, mouseY, IMAGE_PROFILE_BOX, .5, .5, .4);
    }

    private boolean isMouseOnProfileBox3(double mouseX, double mouseY) {
        //check for profile button
        return UIHelper.checkIsXYInBoxRelativeXX(mouseX, mouseY, IMAGE_PROFILE_BOX, .5, .8, .4);
    }

    public File getProfile1() {
        return profile1;
    }

    public File getProfile2() {
        return profile2;
    }

    public File getProfile3() {
        return profile3;
    }

    public void hide() {
        cc.removeLayer(cl);
    }

    public void show() {
        cc.addLayer(cl);
    }
}
