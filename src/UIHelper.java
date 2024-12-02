import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class UIHelper {
    

    public static void drawImageRelativeXX(GraphicsContext gc, Image img, double xPos, double yPos, double width) {
        double xyScale = img.getHeight() / img.getWidth();
        gc.drawImage(img,
            Main.WINDOW_WIDTH * (xPos - width / 2),
            Main.WINDOW_HEIGHT * (yPos - width / 2 * xyScale),
            Main.WINDOW_WIDTH * width,
            Main.WINDOW_WIDTH * width * xyScale
        );
    }

    public static void drawImageRelativeYY(GraphicsContext gc, Image img, double xPos, double yPos, double height) {
        double yxScale = img.getWidth() / img.getHeight();
        gc.drawImage(img,
            Main.WINDOW_WIDTH * (xPos - height / 2 * yxScale),
            Main.WINDOW_HEIGHT * (yPos - height / 2),
            Main.WINDOW_WIDTH * height * yxScale,
            Main.WINDOW_WIDTH * height
        );
    }




    public static boolean checkIsXYInBoxRelativeXX(double mouseX, double mouseY, Image img, double xPos, double yPos, double width) {
        double xyScale = img.getHeight() / img.getWidth();
        
        return (
            mouseX >= (xPos - width / 2) * Main.WINDOW_WIDTH &&
            mouseX <= (xPos + width / 2) * Main.WINDOW_WIDTH &&
            mouseY >= (yPos * Main.WINDOW_HEIGHT - (width / 2 * xyScale) * Main.WINDOW_WIDTH) &&
            mouseY <= (yPos * Main.WINDOW_HEIGHT + (width / 2 * xyScale) * Main.WINDOW_WIDTH)
        );
    }

    public static boolean checkIsXYInBoxRelativeYY(double mouseX, double mouseY, Image img, double xPos, double yPos, double height) {
        double yxScale = img.getWidth() / img.getHeight();
        
        return (
            mouseX >= (xPos * Main.WINDOW_WIDTH - (height / 2 * yxScale) * Main.WINDOW_HEIGHT) &&
            mouseX <= (xPos * Main.WINDOW_WIDTH + (height / 2 * yxScale) * Main.WINDOW_HEIGHT) &&
            mouseY >= (yPos - height / 2) * Main.WINDOW_HEIGHT &&
            mouseY <= (yPos + height / 2) * Main.WINDOW_HEIGHT

        );
    }


}
