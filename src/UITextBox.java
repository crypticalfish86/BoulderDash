import java.util.Objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;



/**
 * A class to group operations related to text box.
 * 
 */
public class UITextBox {
    
    private double x; // x-center of the text box
    private double y; // y-center of the text box

    private double width; // width of the text box relative to the screen size
    private double height; // height of the text box relative to the screen size

    private boolean isShiftDown = false; // indicates the shift mode
    
    private Color textColor; // colour of the text


    // indicates if the text box is focused
    private boolean isFocused = false;

    // indicates if the previous mouse down has happened on this text box
    private boolean isMouseDownOnBox = false;
    
    
    private final StringBuffer text = new StringBuffer(); // text stored under this text box
    
    
    private final int WINDOW_SIZE_X = Main.WINDOW_WIDTH; // width redirected from main
    private final int WINDOW_SIZE_Y = Main.WINDOW_HEIGHT; // height redirected from main

    private final int TEXT_LENGTH_LIMIT = 20; // the maximum text length of this text box


    /**
     * Creates a text box centered at the given position, with size relative to the screen.
     * TextColor cannot be null
     * @param x x-center of the box
     * @param y y-center of the box
     * @param width x-size of the box relative to screen width
     * @param height y-size of the box relative to screen height
     * @param textColor the colour of the text
     */
    public UITextBox(double x, double y, double width, double height, Color textColor) {
        Objects.requireNonNull(textColor);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.textColor = textColor;
    }


    /**
     * Allows the text box to be drawn
     * @param gc graphics context to be drawn on
     */
    public void draw(GraphicsContext gc) {
        gc.setFill(this.textColor);
        gc.setFont(new Font(WINDOW_SIZE_Y * height * .8));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(getText(), WINDOW_SIZE_X * x, WINDOW_SIZE_Y * y, WINDOW_SIZE_X * width);
    }


    /**
     * notifies the text box that a key has been pressed.
     * @param key the key that is pressed.
     */
    public void onKeyDown(KeyCode key) {
        if (key.equals(KeyCode.SHIFT)) { isShiftDown = true; }

        if (isFocused) {
            if (key.isLetterKey() || key.isDigitKey()) {
                
                if (text.length() >= TEXT_LENGTH_LIMIT) { return; }

                text.append(getCharByKey(key, isShiftDown));
            } else if (key.equals(KeyCode.ESCAPE)) {

                isFocused = false;
            } else if (key.equals(KeyCode.ENTER)) {

                isFocused = false;
            } else if (key.equals(KeyCode.BACK_SPACE)) {

                if (text.length() > 0) {
                    text.delete(text.length() - 1, text.length());
                }
            }
        }
    }


    /**
     * notifies the text box that a key has been released.
     * @param key the key that is released.
     */
    public void onKeyUp(KeyCode key) {
        if (key.equals(KeyCode.SHIFT)) { isShiftDown = false; }
    }


    /**
     * notifies the text box that a mouse down operation has happened.
     * @param mouseX x-position of the mouse.
     * @param mouseY y-position of the mouse.
     * @return if the input has been absorbed.
     */
    public boolean onMouseDown(double mouseX, double mouseY) {
        isMouseDownOnBox = isMouseInArea(mouseX, mouseY);
        if (!isMouseDownOnBox) {
            isFocused = false;
        }

        return isMouseDownOnBox;
    }


    /**
     * notifies the text box that a mouse up operation has happened.
     * @param mouseX x-position of the mouse.
     * @param mouseY y-position of the mouse.
     * @return if the input has been absorbed.
     */
    public boolean onMouseUp(double mouseX, double mouseY) {
        boolean isMouseUpOnBox = isMouseInArea(mouseX, mouseY);

        if (isMouseUpOnBox && isMouseDownOnBox) {
            
            if (isFocused) {
                clear();
            }
            isFocused = true;
        } else if (!isMouseDownOnBox) {
            isFocused = false;
        }

        isMouseDownOnBox = false;

        return isMouseUpOnBox;
    }

    /**
     * Checks if the mouse is in the area of the text box.
     * @param mouseX x-position of the mouse.
     * @param mouseY y-position of the mouse.
     * @return if the mouse is on the textbox.
     */
    private boolean isMouseInArea(double mouseX, double mouseY) {
        return (
            mouseX >= (x - width / 2) * WINDOW_SIZE_X &&
            mouseX <= (x + width / 2) * WINDOW_SIZE_X &&
            mouseY >= (y - height / 2) * WINDOW_SIZE_Y &&
            mouseY <= (y + height / 2) * WINDOW_SIZE_Y
        );
    }


    public String getText() {
        return text.toString();
    }


    /**
     * Sets the text of this textbox.
     * @param text The text to set with.
     */
    public void setText(String text) {
        clear();
        this.text.insert(0, text);
    }


    /**
     * Clears the text box.
     */
    public void clear() {
        this.text.delete(0, this.text.length());
    }


    /**
     * Reads if this text box is focused.
     * @return is text box focused.
     */
    public boolean getIsFocused() {
        return isFocused;
    }




    /**
     * Converts any given character to its character input.
     * @param keyCode keycode of the input.
     * @param isShiftDown if shift button is down, which modifies the input.
     * @return character to represent the input.
     */
    private static final char getCharByKey(KeyCode keyCode, boolean isShiftDown) {
        switch (keyCode) {
            case DIGIT0:
                return '0';
            case DIGIT1:
                return '1';
            case DIGIT2:
                return '2';
            case DIGIT3:
                return '3';
            case DIGIT4:
                return '4';
            case DIGIT5:
                return '5';
            case DIGIT6:
                return '6';
            case DIGIT7:
                return '7';
            case DIGIT8:
                return '8';
            case DIGIT9:
                return '9';
            default:
                if (isShiftDown) {

                    return keyCode.toString().charAt(0);
                } else {

                    return keyCode.toString().toLowerCase().charAt(0);
                }
        }
    }
}
