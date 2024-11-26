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




    public static boolean checkIsXYInBox(double mouseX, double mouseY, Image img, double xPos, double yPos, double width) {
        double xyScale = img.getHeight() / img.getWidth();
        
        return (
            mouseX >= (xPos - width / 2) * Main.WINDOW_WIDTH &&
            mouseX <= (xPos + width / 2) * Main.WINDOW_WIDTH &&
            mouseY >= (yPos * Main.WINDOW_HEIGHT - (width / 2 * xyScale) * Main.WINDOW_WIDTH) &&
            mouseY <= (yPos * Main.WINDOW_HEIGHT + (width / 2 * xyScale) * Main.WINDOW_WIDTH)

        );
    }
}
