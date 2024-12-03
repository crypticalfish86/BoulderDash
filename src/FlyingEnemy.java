import javafx.scene.shape.MoveTo;

public abstract class FlyingEnemy extends Enemy {
    protected boolean prioritiseLeft;
    protected int ticksAlive;

    public FlyingEnemy(GameSession gameSession, int x, int y, TileType TileType, long operationInterval, boolean prioritiseDirection) {
        super(gameSession, x, y, TileType, operationInterval);
        this.prioritiseLeft = prioritiseLeft; //either true meaning left or false meaning right
        this.x = x;
        this.y = y;
        ticksAlive = 0;
    }

    protected void move(Tile FlyingEnemy, int x, int y) {
        int[][] directions; // Define direction changes for 'left' and 'right' wall-following
        if (prioritiseLeft) {
            directions = new int[][]{{0, -1}, {-1, 0}, {0, 1}, {1, 0}}; // left, up, right, down
        } else {
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

    protected void newMove(Tile FlyingEnemy, int x, int y){
        Tile tileToLeft = gameSession.getTileFromGrid(x - 1, y);
        Tile tileLeftAbove = gameSession.getTileFromGrid(x - 1, y - 1);
        Tile tileLeftBelow = gameSession.getTileFromGrid(x - 1, y + 1);
        Tile tileToRight = gameSession.getTileFromGrid(x + 1, y);
        Tile tileRightAbove = gameSession.getTileFromGrid(x + 1, y - 1);
        Tile tileRightBelow = gameSession.getTileFromGrid(x + 1, y + 1);
        Tile tileBelow = gameSession.getTileFromGrid(x, y + 1);
        Tile tileAbove = gameSession.getTileFromGrid(x, y - 1);

        if(followEdgeOfThisTile(tileToLeft) && tileAbove.getTileType() == TileType.PATH){
            tileAbove.interact(this);
        }else if(followEdgeOfThisTile(tileToRight) && tileBelow.getTileType() == TileType.PATH){
            tileBelow.interact(this);
        }else if(followEdgeOfThisTile(tileBelow) && tileToLeft.getTileType() == TileType.PATH){
            tileToLeft.interact(this);
        }else if (followEdgeOfThisTile(tileAbove) && tileToRight.getTileType() == TileType.PATH){
            tileToRight.interact(this);
        }else if(followEdgeOfThisTile(tileLeftBelow) && tileToLeft.getTileType() == TileType.PATH){
            tileToLeft.interact(this);
        }else if(followEdgeOfThisTile(tileRightBelow) && tileBelow.getTileType() == TileType.PATH){
            tileBelow.interact(this);
        }else if (followEdgeOfThisTile(tileRightAbove) && tileToRight.getTileType() == TileType.PATH){
            tileToRight.interact(this);
        }else if (followEdgeOfThisTile(tileLeftAbove) && tileAbove.getTileType() == TileType.PATH){
            tileAbove.interact(this);
        }

    }

    private boolean followEdgeOfThisTile(Tile tile) {
        switch (tile.getTileType()) {
            case DIRT_WALL:
            case TITANIUM_WALL:
            case MAGIC_WALL:
            case NORMAL_WALL:
            case EXIT_WALL:
            case FALLING_OBJECT:
                return true;

            default:
                return false;
        }
    }
}
