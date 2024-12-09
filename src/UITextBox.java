import java.util.Objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class UITextBox {
    
    private double x;
    private double y;

    private double width;
    private double height;

    private boolean isShiftDown = false;
    
    private Color textColor; 


    private boolean isFocused = false;
    private boolean isMouseDownOnBox = false;
    private final StringBuffer text = new StringBuffer();
    
    
    private final int WINDOW_SIZE_X = Main.WINDOW_WIDTH;
    private final int WINDOW_SIZE_Y = Main.WINDOW_HEIGHT;

    private final int TEXT_LENGTH_LIMIT = 20;


    public UITextBox(double x, double y, double width, double height, Color textColor) {
        Objects.requireNonNull(textColor);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.textColor = textColor;
    }



    public void draw(GraphicsContext gc) {
        //
        gc.setFill(this.textColor);
        gc.setFont(new Font(WINDOW_SIZE_Y * height * .8));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(getText(), WINDOW_SIZE_X * x, WINDOW_SIZE_Y * y, WINDOW_SIZE_X * width);
    }

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

    public void onKeyUp(KeyCode key) {
        
        if (key.equals(KeyCode.SHIFT)) { isShiftDown = false; }

    }

    public boolean onMouseDown(double mouseX, double mouseY) {
        isMouseDownOnBox = isMouseInArea(mouseX, mouseY);
        return isMouseDownOnBox;
    }

    public boolean onMouseUp(double mouseX, double mouseY) {
        boolean isMouseUpOnBox = isMouseInArea(mouseX, mouseY);

        if (isMouseUpOnBox && isMouseDownOnBox) {
            
            if (isFocused) {
                clear();
            }
            isFocused = true;
        }
        isMouseDownOnBox = false;

        return isMouseUpOnBox;
    }


    private boolean isMouseInArea(double mouseX, double mouseY) {
        return (
            mouseX >= (x - width / 2) * WINDOW_SIZE_X &&
            mouseX >= (x - width / 2) * WINDOW_SIZE_X &&
            mouseY >= (y - height / 2) * WINDOW_SIZE_Y &&
            mouseY >= (y - height / 2) * WINDOW_SIZE_Y
        );
    }


    public String getText() {
        return text.toString();
    }

    public void setText(String text) {
        clear();
        this.text.insert(0, text);
    }

    public void clear() {
        this.text.delete(0, this.text.length());
    }





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
