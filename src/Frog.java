import java.lang.annotation.ElementType;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Frog extends Enemy {




    public static final Image img = new Image("file:Assets/Images/Frog.png"); // Placeholder for the image

    private ArrayList<int[]> path = new ArrayList<>();
    private int cooldown = 0;
    private int pathRefresh = 0;
    

    private static final int COOLDOWN_MOVE = 15;
    private static final int PATH_REFRESH_RATE = 120;

    public Frog(GameSession gameSession, int x, int y){
        super(gameSession, x, y, TileType.MOVING_ENEMY, 0);
        this.amoebaCanSpreadToThisTile = true;
    }


    public void interact(Tile tile) {

        if (tile.getTileType() == TileType.FALLING_OBJECT && tile.getYPosition() == this.y - 1) {
            this.triggerExplosion(this.x, this.y, false);
        }
    }

    public void updateTile(long currentTimeInMilliseconds) {
        
        //checks if the frog should move
        if (pathRefresh > 0) {
            pathRefresh--;
        }

        if (cooldown > 0) { cooldown--; return; }
        cooldown = COOLDOWN_MOVE;



        //moves the frog if there is a path, if not, generate one
        if (pathRefresh == 0 || path.size() == 0) { refreshPath(); }

        if (path.size() == 0) { return; }


        int[] pathTarget = path.remove(0);

        if (!moveTo(pathTarget[0], pathTarget[1])) {
            refreshPath();
        }
    }





    /**
     * Refreshes the path of the frog
     */
    private void refreshPath() {
        pathRefresh = PATH_REFRESH_RATE;
        
        PathFinder<Tile> pf = new PathFinder<>(gameSession.getGridTileMap(), gameSession.getGridWidth(), gameSession.getGridHeight());
        System.out.println("recalculating path");
        this.path = pf.computeGridFill(new int[] {this.x, this.y}, new int[] {gameSession.getPlayerX(), gameSession.getPlayerY()}, Tile -> {
            return Tile.tileType == TileType.PATH || Tile.tileType == TileType.PLAYER;
        });
    }






    @Override
    public void drawTile(GraphicsContext gc) {
        draw(gc, img, 0, 0);
    }

    



    public String returnStringTileRepresentation(){
        return "F";
    }
>>>>>>> c4586193427f1233401d7211f6194de20b4b532a
}


