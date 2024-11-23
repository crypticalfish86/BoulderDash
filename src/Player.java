import javafx.scene.image.Image;

public class Player extends Tile {
    String inputPending;
        //TODO possibly should be bool
    String input;

    public static final Image img = new Image("");//TODO: add the image here

    public void queueInput(String input){
        //TODO make the input actually input into this method/function
    }
    public void Player() {
        //TODO I might just be having a dumb moment but this is what i came up for this method.
    }
    public void killPlayer(){
        //TODO I might just be having a dumb moment but this is what i came up for this method.
    }


    public Player(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.PLAYER, operationInterval);
    }


    public void interact(Tile Tile) {

    }

    
    public void updateTile(long currentTimeInMilliseconds) {
        draw(img, 0, 0);
    }

}
