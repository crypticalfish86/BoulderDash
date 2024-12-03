import javafx.scene.shape.MoveTo;

public abstract class FlyingEnemy extends Enemy{
    protected boolean prioritiseDirection;

    public FlyingEnemy(GameSession gameSession, int x, int y, TileType TileType, long operationInterval, boolean prioritiseDirection){
        super(gameSession, x, y, TileType, operationInterval);
        this.prioritiseDirection = prioritiseDirection; //either true meaning left or false meaning right
        this.x = x;
        this.y = y;
    }

    public void move(Tile FlyingEnemy, int x, int y) {
        int[][] directions; // Define direction changes for 'left' and 'right' wall-following
        if (prioritiseDirection) {
            directions = new int[][]{{0, -1}, {-1, 0}, {0, 1}, {1, 0}}; // left, up, right, down
        } else{
            directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // right, down, left, up
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];
                if (isXYInBounds(newX, newY) && ((gameSession.getTileFromGrid(newX, newY).getTileType()) == (TileType.PATH))) {
                    x = newX;
                    y = newY;
                    break;
                }
            }
        }
    }

}
