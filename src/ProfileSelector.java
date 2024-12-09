import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class represents the canvas display for the profile selector.
 * Once a new class of this type is created, it is initialised inside
 * the game class and is displayed onto the screen. This is where the
 * player can select one of three profiles to either create a new save
 * or start an already saved game.
 * @author Armaan Ghadiali
 * @version 2.3
 */
public class ProfileSelector extends DisplayLayer {
    // Attributes to link the drawing of images and manipulation of layers
    CanvasCompositor cc;
    CanvasLayer cl;
    Game game;

    // Image files for interactable buttons and non-interactable images
    private static final Image IMAGE_LEVEL1 =
            new Image("file:Assets/Buttons/LevelOne.png");
    private static final Image IMAGE_LEVEL2 =
            new Image("file:Assets/Buttons/LevelTwo.png");
    private static final Image IMAGE_LEVEL3 =
            new Image("file:Assets/Buttons/LevelThree.png");
    private static final Image IMAGE_LEVEL4 =
            new Image("file:Assets/Buttons/LevelThree.png");
    private static final Image IMAGE_LEVEL5 =
            new Image("file:Assets/Buttons/LevelThree.png");
    private static final Image IMAGE_BACK =
            new Image("file:Assets/Buttons/BackButton.png");
    private static final Image IMAGE_NEW_SAVE =
            new Image("file:Assets/Buttons/NewSaveButton.png");
    private static final Image IMAGE_PROFILE_BOX =
            new Image("file:Assets/Buttons/ProfileBox.png");
    private static final Image IMAGE_PROFILE1 =
            new Image("file:Assets/Buttons/ProfileOne.png");
    private static final Image IMAGE_PROFILE2 =
            new Image("file:Assets/Buttons/ProfileTwo.png");
    private static final Image IMAGE_PROFILE3 =
            new Image("file:Assets/Buttons/ProfileThree.png");
    private static final Image IMAGE_CREATE =
            new Image("file:Assets/Buttons/CreateButton.png");
    private static final Image IMAGE_DELETE =
            new Image("file:Assets/Buttons/DeleteButton.png");

    // Text files which import the current state of the profiles
    private final File profile1 =
            new File("Profiles/profile1.txt");
    private final File profile2 =
            new File("Profiles/profile2.txt");
    private final File profile3 =
            new File("Profiles/profile3.txt");

    // Checks for whether the mouse has been clicked on within the button's
    // dimensions
    private final boolean[] mouseDownOnBackButton;
    private final boolean[] mouseDownOnProfileBox1;
    private final boolean[] mouseDownOnProfileBox2;
    private final boolean[] mouseDownOnProfileBox3;
    private final boolean[] mouseDownOnCreateButton1;
    private final boolean[] mouseDownOnCreateButton2;
    private final boolean[] mouseDownOnCreateButton3;
    private final boolean[] mouseDownOnDeleteButton1;
    private final boolean[] mouseDownOnDeleteButton2;
    private final boolean[] mouseDownOnDeleteButton3;


    /**
     * Constructor for the profile selector.
     * Takes in the current game instance and canvas compositor for layering.
     * @param game the current instance of the Game class which holds the
     *             current game session.
     * @param cc used for managing the different layers of the canvas.
     */
    public ProfileSelector(Game game, CanvasCompositor cc) {
        this.game = game;
        this.cc = cc;

        // Automatically sets these to false since we assume the mouse has not
        // clicked on any of the buttons yet
        mouseDownOnBackButton = new boolean[]{false};

        mouseDownOnProfileBox1 = new boolean[]{false};
        mouseDownOnProfileBox2 = new boolean[]{false};
        mouseDownOnProfileBox3 = new boolean[]{false};

        mouseDownOnCreateButton1 = new boolean[]{false};
        mouseDownOnCreateButton2 = new boolean[]{false};
        mouseDownOnCreateButton3 = new boolean[]{false};

        mouseDownOnDeleteButton1 = new boolean[]{false};
        mouseDownOnDeleteButton2 = new boolean[]{false};
        mouseDownOnDeleteButton3 = new boolean[]{false};

        // Creates a new canvas layer to manage interaction between the player
        // and the screen
        this.cl = new CanvasLayer(new CanvasLayer.CanvasLayerI() {
            @Override
            public boolean onMouseDown(
                    double x, double y, boolean hasConsumed) {
                if (hasConsumed) {
                    return true;
                }
                mouseDownOnBackButton[0] = isMouseOnBackButton(x, y);

                mouseDownOnProfileBox1[0] = isMouseOnProfileBox1(x, y);
                mouseDownOnProfileBox2[0] = isMouseOnProfileBox2(x, y);
                mouseDownOnProfileBox3[0] = isMouseOnProfileBox3(x, y);

                mouseDownOnCreateButton1[0] = isMouseOnCreateButton1(x, y);
                mouseDownOnCreateButton2[0] = isMouseOnCreateButton2(x, y);
                mouseDownOnCreateButton3[0] = isMouseOnCreateButton3(x, y);

                mouseDownOnDeleteButton1[0] = isMouseOnDeleteButton1(x, y);
                mouseDownOnDeleteButton2[0] = isMouseOnDeleteButton2(x, y);
                mouseDownOnDeleteButton3[0] = isMouseOnDeleteButton3(x, y);
                return true;
            }

            @Override
            public boolean onMouseUp(double x, double y, boolean hasConsumed) {
                if (hasConsumed) { return true; }
                if (mouseDownOnBackButton[0] && isMouseOnBackButton(x, y)) {
                    game.onBackButtonClicked();
                    hide();
                }
                if (mouseDownOnProfileBox1[0] && isMouseOnProfileBox1(x, y)) {
                    game.onProfileBoxClicked1();
                    hide();
                }
                if (mouseDownOnProfileBox2[0] && isMouseOnProfileBox2(x, y)) {
                    game.onProfileBoxClicked2();
                    hide();
                }
                if (mouseDownOnProfileBox3[0] && isMouseOnProfileBox3(x, y)) {
                    game.onProfileBoxClicked3();
                    hide();
                }
                if (mouseDownOnCreateButton1[0] && isMouseOnCreateButton1(x, y)) {
                    game.onCreateButtonClicked1();
                    hide();
                }
                if (mouseDownOnCreateButton2[0] && isMouseOnCreateButton2(x, y)) {
                    game.onCreateButtonClicked2();
                    hide();
                }
                if (mouseDownOnCreateButton3[0] && isMouseOnCreateButton3(x, y)) {
                    game.onCreateButtonClicked3();
                    hide();
                }
                if (mouseDownOnDeleteButton1[0] && isMouseOnDeleteButton1(x, y)) {
                    game.onDeleteButtonClicked1();
                    hide();
                }
                if (mouseDownOnDeleteButton2[0] && isMouseOnDeleteButton2(x, y)) {
                    game.onDeleteButtonClicked2();
                    hide();
                }
                if (mouseDownOnDeleteButton3[0] && isMouseOnDeleteButton3(x, y)) {
                    game.onDeleteButtonClicked3();
                    hide();
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

                // Draws the back button
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_BACK, .15, .1, .15);

                // Draws the profile boxes
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_PROFILE_BOX, .5, .2, .4);
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_PROFILE_BOX, .5, .5, .4);
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_PROFILE_BOX, .5, .8, .4);

                // Draws the images to show which profile is which
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_PROFILE1, .4, .15, .17);
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_PROFILE2, .4, .45, .17);
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_PROFILE3, .4, .75, .17);

                // Level format for profiles/levels
                // line 1: Current Level, Height, Width
                // Line 2: Score, TimeLeft, TimeAllowed
                // Line 3: DiamondCount, DiamondsRequired
                // Line 4: AmoebaSpreadRate, AmoebaSizeLimit
                // Line 5: RedKey, BlueKey, YellowKey, GreenKey
                // Line 6+: Actual level
                displaySaveStatus(gc, profile1, .625, .15, .12);
                displaySaveStatus(gc, profile2, .625, .45, .12);
                displaySaveStatus(gc, profile3, .625, .75, .12);

                // Draws the create button images
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_CREATE, .625, .225, .11);
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_CREATE, .625, .525, .11);
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_CREATE, .625, .825, .11);

                // Draws the delete button images
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_DELETE, .625, .3, .11);
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_DELETE, .625, .6, .11);
                UIHelper.drawImageRelativeXX(
                        gc, IMAGE_DELETE, .625, .9, .11);
            }
        }, 1);

        // Adds the layer to the canvas for display
        cc.addLayer(cl);
    }

    /**
     * Draws the relevant level image to the screen for each profile displayed
     * onto the screen depending on the value inside the profile file (new save
     * if there is no save).
     * xPos, yPos and width are included as parameters in case these values want
     * to be varied for future use if the case arises.
     * @param gc Used to issue draw calls to the canvas using a buffer.
     * @param profile profile file to read from.
     * @param xPos x-position to print the image in the correct horizontal
     *             plane.
     * @param yPos y-position to print the image in the correct vertical
     *      *             plane.
     * @param width recalculates the size of the image given a double.
     */
    private void displaySaveStatus(GraphicsContext gc, File profile,
                                   double xPos, double yPos, double width) {
        int currentLine = 0;
        try { // Checks the first line of the profile (specifically the level)
            Scanner input = new Scanner(profile);
            while (input.hasNextLine() && currentLine < 1) {
                String[] saveCheck = input.nextLine().split(";");
                // If a new save then draw the new save button
                if (saveCheck[0].equals("NEW")) {
                    UIHelper.drawImageRelativeXX(
                            gc, IMAGE_NEW_SAVE, xPos, yPos, width);
                // Otherwise print the relevant level number
                } else {
                    switch (saveCheck[0]) {
                        case "1":
                            UIHelper.drawImageRelativeXX(
                                    gc, IMAGE_LEVEL1, xPos, yPos, width);
                            break;
                        case "2":
                            UIHelper.drawImageRelativeXX(
                                    gc, IMAGE_LEVEL2, xPos, yPos, width);
                            break;
                        case "3":
                            UIHelper.drawImageRelativeXX(
                                    gc, IMAGE_LEVEL3, xPos, yPos, width);
                            break;
                        case "4":
                            UIHelper.drawImageRelativeXX(
                                    gc, IMAGE_LEVEL4, xPos, yPos, width);
                            break;
                        case "5":
                            UIHelper.drawImageRelativeXX(
                                    gc, IMAGE_LEVEL5, xPos, yPos, width);
                            break;
                    }
                }
                currentLine++;
            }
            input.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Deletes a profile's save and replaces it with NEW; to signify a new
     * profile.
     * @param profile Profile file to be overwritten.
     */
    public void deleteProfile(File profile) {
        try { // Overwrites entire profile file with NEW; keyword
            FileWriter output = new FileWriter(profile);
            output.write("NEW;");
            output.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Returns true if mouse is on the back button, used for returning back to
     * the main menu.
     * @param mouseX x-position of the mouse.
     * @param mouseY y-position of the mouse.
     * @return if the mouse is on the back button.
     */
    private boolean isMouseOnBackButton(double mouseX, double mouseY) {
        //check for back button
        return UIHelper.checkIsXYInBoxRelativeXX(
                mouseX, mouseY, IMAGE_BACK, .15, .1, .15);
    }

    /**
     * Returns true if mouse is on the first profile, used for selecting the
     * first profile.
     * @param mouseX x-position of the mouse.
     * @param mouseY y-position of the mouse.
     * @return if the mouse is on the first profile box.
     */
    private boolean isMouseOnProfileBox1(double mouseX, double mouseY) {
        // Check for profile box one
        return UIHelper.checkIsXYInBoxRelativeXX(
                mouseX, mouseY, IMAGE_PROFILE_BOX, .5, .2, .4);
    }

    /**
     * Returns true if mouse is on the second profile, used for selecting the
     * second profile.
     * @param mouseX x-position of the mouse.
     * @param mouseY y-position of the mouse.
     * @return if the mouse is on the second profile box.
     */
    private boolean isMouseOnProfileBox2(double mouseX, double mouseY) {
        // Check for profile box two
        return UIHelper.checkIsXYInBoxRelativeXX(
                mouseX, mouseY, IMAGE_PROFILE_BOX, .5, .5, .4);
    }

    /**
     * Returns true if mouse is on the third profile, used for selecting the
     * third profile.
     * @param mouseX x-position of the mouse.
     * @param mouseY y-position of the mouse.
     * @return if the mouse is on the third profile box.
     */
    private boolean isMouseOnProfileBox3(double mouseX, double mouseY) {
        // Check for profile box three
        return UIHelper.checkIsXYInBoxRelativeXX(
                mouseX, mouseY, IMAGE_PROFILE_BOX, .5, .8, .4);
    }

    /**
     * Returns true if mouse is on the first create button, used for making a
     * new profile.
     * @param mouseX x-position of the mouse.
     * @param mouseY y-position of the mouse.
     * @return if the mouse is on the first create button.
     */
    private boolean isMouseOnCreateButton1(double mouseX, double mouseY) {
        // Check for create button 1
        return UIHelper.checkIsXYInBoxRelativeXX(
                mouseX, mouseY, IMAGE_CREATE, .625, .225, .11);
    }

    /**
     * Returns true if mouse is on the second create button, used for making a
     * new profile.
     * @param mouseX x-position of the mouse.
     * @param mouseY y-position of the mouse.
     * @return if the mouse is on the second create button.
     */
    private boolean isMouseOnCreateButton2(double mouseX, double mouseY) {
        // Check for create button 2
        return UIHelper.checkIsXYInBoxRelativeXX(
                mouseX, mouseY, IMAGE_CREATE, .625, .525, .11);
    }

    /**
     * Returns true if mouse is on the third create button, used for making a
     * new profile.
     * @param mouseX x-position of the mouse.
     * @param mouseY y-position of the mouse.
     * @return if the mouse is on the third create button.
     */
    private boolean isMouseOnCreateButton3(double mouseX, double mouseY) {
        // Check for create button 3
        return UIHelper.checkIsXYInBoxRelativeXX(
                mouseX, mouseY, IMAGE_CREATE, .625, .825, .11);
    }

    /**
     * Returns true if mouse is on the first delete button, used for removing
     * an already established save.
     * @param mouseX x-position of the mouse.
     * @param mouseY y-position of the mouse.
     * @return if the mouse is on the first create button.
     */
    private boolean isMouseOnDeleteButton1(double mouseX, double mouseY) {
        // Check for delete button 1
        return UIHelper.checkIsXYInBoxRelativeXX(
                mouseX, mouseY, IMAGE_DELETE, .625, .3, .11);
    }

    /**
     * Returns true if mouse is on the second delete button, used for removing
     * an already established save.
     * @param mouseX x-position of the mouse.
     * @param mouseY y-position of the mouse.
     * @return if the mouse is on the second create button.
     */
    private boolean isMouseOnDeleteButton2(double mouseX, double mouseY) {
        // Check for delete button 2
        return UIHelper.checkIsXYInBoxRelativeXX(
                mouseX, mouseY, IMAGE_DELETE, .625, .6, .11);
    }

    /**
     * Returns true if mouse is on the third delete button, used for removing
     * an already established save.
     * @param mouseX x-position of the mouse.
     * @param mouseY y-position of the mouse.
     * @return if the mouse is on the third create button.
     */
    private boolean isMouseOnDeleteButton3(double mouseX, double mouseY) {
        // Check for delete button 3
        return UIHelper.checkIsXYInBoxRelativeXX(
                mouseX, mouseY, IMAGE_DELETE, .625, .9, .11);
    }

    /**
     * Hides the profile selector from the canvas.
     */
    public void hide() {
        cc.removeLayer(cl);
    }

    /**
     * Shows the profile selector canvas layer onto the screen.
     */
    public void show() {
        cc.addLayer(cl);
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
}
