import javafx.scene.shape.MoveTo;

public abstract class FlyingEnemy extends Enemy {
    protected boolean prioritiseLeft;
    protected int ticksAlive;
    private int prevX = -1;
    private int prevY = -1;

    public FlyingEnemy(GameSession gameSession, int x, int y, TileType TileType, long operationInterval, boolean prioritiseDirection) {
        super(gameSession, x, y, TileType, operationInterval);
        this.prioritiseLeft = prioritiseLeft; //either true meaning left or false meaning right
        this.x = x;
        this.y = y;
        ticksAlive = 0;
    }



    protected void move(Tile FlyingEnemy, int x, int y){
        Tile tileToLeft = gameSession.getTileFromGrid(x - 1, y);
        Tile tileToRight = gameSession.getTileFromGrid(x + 1, y);
        Tile tileBelow = gameSession.getTileFromGrid(x, y + 1);
        Tile tileAbove = gameSession.getTileFromGrid(x, y - 1);


        if(tileToLeft.getTileType() == TileType.PATH && (x - 1 != prevX)){
            prevX = x;
            prevY = y;
            tileToLeft.interact(this);

        }else if(tileAbove.getTileType() == TileType.PATH && (y -1 != prevY)){
            prevX = x;
            prevY = y;
            tileAbove.interact(this);


        }else if(tileBelow.getTileType() == TileType.PATH && (y + 1 != prevY)){
            prevX = x;
            prevY = y;
            tileBelow.interact(this);
        }else if(tileToRight.getTileType() == TileType.PATH && (x + 1 != prevX)){
            prevX = x;
            prevY = y;
            tileToRight.interact(this);

        }

    }


}
