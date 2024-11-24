import java.net.URL;

import javafx.scene.image.Image;

public class Player extends Tile {
    String inputPending;    
        //TODO possibly should be bool
    String input;


    
    public static final Image img = new Image("file:Assets/Images/Ameoba.png");//TODO: add the image here

    
    


    public void killPlayer() {
        


    }


    public Player(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.PLAYER, operationInterval);
    }


    public void interact(Tile Tile) {

    }


    public void updateTile(long currentTimeInMilliseconds) {
        draw(img, 0, 0);
    }



    public void onKeyPressed(String key) {
        System.out.println("Key " + key + " pressed.");
    }

    public void onKeyReleased(String key) {
        
        System.out.println("Key " + key + " released.");
    }
}
