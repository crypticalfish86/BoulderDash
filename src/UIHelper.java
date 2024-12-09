import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class helps draw and interpret hitboxes.
 * @author Alex (Tsz Tung Yee)
 * @version 1.0
 */

public class UIHelper {
    
    /**
     * draws an image centered around the given coordinates,
     * with a specified width (size relative to x-axis)
     * @param gc graphics context to draw on
     * @param img image to be drawn, also used for determining height
     * @param xPos x-position of the center
     * @param yPos y-position of the center
     * @param width width of the image, in ratio to the width
     */
    public static void drawImageRelativeXX(
        GraphicsContext gc, Image img, double xPos, double yPos, double width
    ) {

        double xyScale = img.getHeight() / img.getWidth();
        gc.drawImage(img,
            Main.WINDOW_WIDTH * (xPos - width / 2),
            Main.WINDOW_HEIGHT * (yPos - width / 2 * xyScale),
            Main.WINDOW_WIDTH * width,
            Main.WINDOW_WIDTH * width * xyScale
        );
    }

    /**
     * draws an image centered around the given coordinates,
     * with a specified height (size relative to y-axis)
     * @param gc graphics context to draw on
     * @param img image to be drawn, also used for determining width
     * @param xPos x-position of the center
     * @param yPos y-position of the center
     * @param height height of the image, in ratio to the height
     */
    public static void drawImageRelativeYY(
        GraphicsContext gc, Image img, double xPos, double yPos, double height
    ) {

        double yxScale = img.getWidth() / img.getHeight();
        gc.drawImage(img,
            Main.WINDOW_WIDTH * (xPos - height / 2 * yxScale),
            Main.WINDOW_HEIGHT * (yPos - height / 2),
            Main.WINDOW_WIDTH * height * yxScale,
            Main.WINDOW_WIDTH * height
        );
    }



    /**
     * Checks if the given mouse x and y (in pixels) is in the given rectangular box defined by
     * the image aspect ratio and the given width
     * 
     * @param mouseX x-coordinate of the mouse
     * @param mouseY y-coordinate of the mouse
     * @param img image to evaluate aspect ratio
     * @param xPos x-position of the box
     * @param yPos y-position of the box
     * @param width width of the box in ratio fo the width
     * @return if the position is in the given box
     */
    public static boolean checkIsXYInBoxRelativeXX(
        double mouseX, double mouseY, Image img, double xPos, double yPos, double width
    ) {

        double xyScale = img.getHeight() / img.getWidth();
        
        return (
            mouseX >= (xPos - width / 2) * Main.WINDOW_WIDTH &&
            mouseX <= (xPos + width / 2) * Main.WINDOW_WIDTH &&
            mouseY >= (yPos * Main.WINDOW_HEIGHT - (width / 2 * xyScale) * Main.WINDOW_WIDTH) &&
            mouseY <= (yPos * Main.WINDOW_HEIGHT + (width / 2 * xyScale) * Main.WINDOW_WIDTH)
        );
    }



    /**
     * Checks if the given mouse x and y (in pixels) is in the given rectangular box defined by
     * the image aspect ratio and the given height
     * 
     * @param mouseX x-coordinate of the mouse
     * @param mouseY y-coordinate of the mouse
     * @param img image to evaluate aspect ratio
     * @param xPos x-position of the box
     * @param yPos y-position of the box
     * @param height height of the box in ratio fo the height
     * @return if the position is in the given box
     */
    public static boolean checkIsXYInBoxRelativeYY(
        double mouseX, double mouseY, Image img, double xPos, double yPos, double height
    ) {
        
        double yxScale = img.getWidth() / img.getHeight();
        
        return (
            mouseX >= (xPos * Main.WINDOW_WIDTH - (height / 2 * yxScale) * Main.WINDOW_HEIGHT) &&
            mouseX <= (xPos * Main.WINDOW_WIDTH + (height / 2 * yxScale) * Main.WINDOW_HEIGHT) &&
            mouseY >= (yPos - height / 2) * Main.WINDOW_HEIGHT &&
            mouseY <= (yPos + height / 2) * Main.WINDOW_HEIGHT

        );
    }


}
