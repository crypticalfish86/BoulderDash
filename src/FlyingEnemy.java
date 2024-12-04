import javafx.scene.shape.MoveTo;
//TODO resolve any commented out code lines
public abstract class FlyingEnemy extends Enemy {
    protected boolean prioritiseLeft;
    //protected int ticksAlive;
    protected String direction;

    public FlyingEnemy(GameSession gameSession, int x, int y, TileType TileType, long operationInterval, boolean prioritiseDirection) {
        super(gameSession, x, y, TileType, operationInterval);
        this.prioritiseLeft = prioritiseLeft; //either true meaning left or false meaning right
        this.x = x;
        this.y = y;
        //ticksAlive = 0;
        direction = "Up";
    }




    protected void move(int x, int y){
        int[] xDir = new int[4];
        int[] yDir = new int[4];

        System.out.println(x + "" + y + "" + this.direction);

        switch (this.direction) {
            case "Up":
                xDir = new int[]{-1, +1, 0, 0};
                yDir = new int[]{0, 0, +1, -1};
                break;
            case "Down":
                xDir = new int[]{+1, -1, 0, 0};
                yDir = new int[]{0, 0, -1, +1};
                break;
            case "Left":
                xDir = new int[]{0, 0, +1, -1};
                yDir = new int[]{+1, -1, 0, 0};
                break;
            case "Right":
                xDir = new int[]{0, 0, -1, +1};
                yDir = new int[]{-1, +1, 0, 0};
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }


        Tile tileToLeft = gameSession.getTileFromGrid(x + xDir[0], y + yDir[0]);
        Tile tileToRight = gameSession.getTileFromGrid(x + xDir[1], y + yDir[1]);
        Tile tileBehind = gameSession.getTileFromGrid(x + xDir[2], y + yDir[2]);
        Tile tileInFront = gameSession.getTileFromGrid(x + xDir[3], y + yDir[3]);


        int newX = x;
        int newY = y;
        if(tileToLeft.getTileType() == TileType.PATH){
            tileToLeft.interact(this);

            newX = x + xDir[0];
            newY = y + yDir[0];

        }else if(tileInFront.getTileType() == TileType.PATH){
            tileInFront.interact(this);

            newX = x + xDir[3];
            newY = y + yDir[3];

        }else if(tileToRight.getTileType() == TileType.PATH){
            tileToRight.interact(this);

            newX = x + xDir[1];
            newY = y + yDir[1];

        }else if(tileBehind.getTileType() == TileType.PATH){
            tileBehind.interact(this);

            newX = x + xDir[2];
            newY = y + yDir[2];

        }

        if(newX < x){
            this.direction = "Left";
        }else if(newX > x){
            this.direction = "Right";
        }else if(newY < y){
            this.direction = "Up";
        }else if(newY > y){
            this.direction = "Down";
        }


    }


}
