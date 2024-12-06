import javafx.scene.shape.MoveTo;
//TODO resolve any commented out code lines
public abstract class FlyingEnemy extends Enemy {

    protected static final int DELAY_FACTOR = 20;

    protected boolean prioritiseLeft;
    protected int ticksAlive;
    protected String direction;


    public FlyingEnemy(GameSession gameSession, int x, int y, TileType TileType, long operationInterval, boolean prioritiseLeft) {
        super(gameSession, x, y, TileType, operationInterval);
        this.prioritiseLeft = prioritiseLeft; //either true meaning left or false meaning right
        this.x = x;
        this.y = y;
        ticksAlive = 0;
        direction = "Up";
    }




    protected void moveLeft(int x, int y){
        int[] xDir = new int[4];
        int[] yDir = new int[4];

        boolean hasFoundDirection = false;

        //Set the direction enemy is facing
        if(y != 0){
            if(followEdgeOfThisTile(gameSession.getTileFromGrid(x, y - 1))){
                direction = "Right";
                hasFoundDirection = true;
            }
        }
        if(y != gameSession.getGridHeight() - 1 && !hasFoundDirection){
            if(followEdgeOfThisTile(gameSession.getTileFromGrid(x, y + 1))){
                direction = "Left";
                hasFoundDirection = true;
            }
        }
        if(x != 0 && !hasFoundDirection){
            if(followEdgeOfThisTile(gameSession.getTileFromGrid(x - 1, y))){
                direction = "Up";
            }
        }
        if(x != gameSession.getGridWidth() - 1 && !hasFoundDirection){
            if(followEdgeOfThisTile(gameSession.getTileFromGrid(x + 1, y))){
                direction = "Down";
            }
        }


        System.out.println(x + "" + y + "" + this.direction);

        //Find which tiles are to enemies' left, right, forward and behind based on direction
        switch (this.direction) {
            case "Up":
                xDir = new int[]{-1, +1, 0, 0};
                yDir = new int[]{0, 0, +1, -1};

                //Check for out of bounds
                if(y == 0){
                    yDir[3] = 0;
                }
                if(y == gameSession.getGridHeight() - 1){
                    yDir[2] = 0;
                }
                if(x == 0){
                    xDir[0] = 0;
                }
                if(x == gameSession.getGridWidth() - 1){
                    xDir[1] = 0;
                }
                break;
            case "Down":
                xDir = new int[]{+1, -1, 0, 0};
                yDir = new int[]{0, 0, -1, +1};

                //Check for out of bounds
                if(y == 0){
                    yDir[2] = 0;
                }
                if(y == gameSession.getGridHeight() - 1){
                    yDir[3] = 0;
                }
                if(x == 0){
                    xDir[1] = 0;
                }
                if(x == gameSession.getGridWidth() - 1){
                    xDir[0] = 0;
                }
                break;
            case "Left":
                xDir = new int[]{0, 0, +1, -1};
                yDir = new int[]{+1, -1, 0, 0};

                //Check for out of bounds
                if(y == 0){
                    yDir[1] = 0;
                }
                if(y == gameSession.getGridHeight() - 1){
                    yDir[0] = 0;
                }
                if(x == 0){
                    xDir[3] = 0;
                }
                if(x == gameSession.getGridWidth() - 1){
                    xDir[2] = 0;
                }
                break;
            case "Right":
                xDir = new int[]{0, 0, -1, +1};
                yDir = new int[]{-1, +1, 0, 0};

                //Check for out of bounds
                if(y == 0){
                    yDir[0] = 0;
                }
                if(y == gameSession.getGridHeight() - 1){
                    yDir[1] = 0;
                }
                if(x == 0){
                    xDir[2] = 0;
                }
                if(x == gameSession.getGridWidth() - 1){
                    xDir[3] = 0;
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }


        Tile tileToLeft = gameSession.getTileFromGrid(x + xDir[0], y + yDir[0]); //Tile to enemies' left
        Tile tileToRight = gameSession.getTileFromGrid(x + xDir[1], y + yDir[1]); //Tile to enemies' right
        Tile tileBehind = gameSession.getTileFromGrid(x + xDir[2], y + yDir[2]); //Tile behind enemy
        Tile tileInFront = gameSession.getTileFromGrid(x + xDir[3], y + yDir[3]); //Tile in front of enemy


        int newX = x;
        int newY = y;

        //First try to move left, then forward, then right, then behind
        if(tileToLeft.getTileType() == TileType.PATH || tileToLeft.getTileType() == TileType.PLAYER){
            tileToLeft.interact(this);

            newX = x + xDir[0];
            newY = y + yDir[0];

        }else if(tileInFront.getTileType() == TileType.PATH || tileInFront.getTileType() == TileType.PLAYER){
            tileInFront.interact(this);

            newX = x + xDir[3];
            newY = y + yDir[3];

        }else if(tileToRight.getTileType() == TileType.PATH || tileToRight.getTileType() == TileType.PLAYER){
            tileToRight.interact(this);

            newX = x + xDir[1];
            newY = y + yDir[1];

        }else if(tileBehind.getTileType() == TileType.PATH || tileBehind.getTileType() == TileType.PLAYER){
            tileBehind.interact(this);

            newX = x + xDir[2];
            newY = y + yDir[2];

        }

        //Set new direction
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

    protected void moveRight(int x, int y){
        int[] xDir = new int[4];
        int[] yDir = new int[4];

        boolean hasFoundDirection = false;

        //Set the direction enemy is facing
        if(y != 0){
            if(followEdgeOfThisTile(gameSession.getTileFromGrid(x, y - 1))){
                direction = "Left";
                hasFoundDirection = true;
            }
        }
        if(y != gameSession.getGridHeight() - 1 && !hasFoundDirection){
            if(followEdgeOfThisTile(gameSession.getTileFromGrid(x, y + 1))){
                direction = "Right";
                hasFoundDirection = true;
            }
        }
        if(x != 0 && !hasFoundDirection){
            if(followEdgeOfThisTile(gameSession.getTileFromGrid(x - 1, y))){
                direction = "Down";
            }
        }
        if(x != gameSession.getGridWidth() - 1 && !hasFoundDirection){
            if(followEdgeOfThisTile(gameSession.getTileFromGrid(x + 1, y))){
                direction = "Up";
            }
        }

        //Find which tiles are to enemies' left, right, forward and behind based on direction
        switch (this.direction) {
            case "Up":
                xDir = new int[]{-1, +1, 0, 0};
                yDir = new int[]{0, 0, +1, -1};

                //Check for out of bounds
                if(y == 0){
                    yDir[3] = 0;
                }
                if(y == gameSession.getGridHeight() - 1){
                    yDir[2] = 0;
                }
                if(x == 0){
                    xDir[0] = 0;
                }
                if(x == gameSession.getGridWidth() - 1){
                    xDir[1] = 0;
                }
                break;
            case "Down":
                xDir = new int[]{+1, -1, 0, 0};
                yDir = new int[]{0, 0, -1, +1};

                //Check for out of bounds
                if(y == 0){
                    yDir[2] = 0;
                }
                if(y == gameSession.getGridHeight() - 1){
                    yDir[3] = 0;
                }
                if(x == 0){
                    xDir[1] = 0;
                }
                if(x == gameSession.getGridWidth() - 1){
                    xDir[0] = 0;
                }
                break;
            case "Left":
                xDir = new int[]{0, 0, +1, -1};
                yDir = new int[]{+1, -1, 0, 0};

                //Check for out of bounds
                if(y == 0){
                    yDir[1] = 0;
                }
                if(y == gameSession.getGridHeight() - 1){
                    yDir[0] = 0;
                }
                if(x == 0){
                    xDir[3] = 0;
                }
                if(x == gameSession.getGridWidth() - 1){
                    xDir[2] = 0;
                }
                break;
            case "Right":
                xDir = new int[]{0, 0, -1, +1};
                yDir = new int[]{-1, +1, 0, 0};

                //Check for out of bounds
                if(y == 0){
                    yDir[0] = 0;
                }
                if(y == gameSession.getGridHeight() - 1){
                    yDir[1] = 0;
                }
                if(x == 0){
                    xDir[2] = 0;
                }
                if(x == gameSession.getGridWidth() - 1){
                    xDir[3] = 0;
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }


        Tile tileToLeft = gameSession.getTileFromGrid(x + xDir[0], y + yDir[0]); //Tile to enemies' left
        Tile tileToRight = gameSession.getTileFromGrid(x + xDir[1], y + yDir[1]); //Tile to enemies' right
        Tile tileBehind = gameSession.getTileFromGrid(x + xDir[2], y + yDir[2]); //Tile behind enemy
        Tile tileInFront = gameSession.getTileFromGrid(x + xDir[3], y + yDir[3]); //Tile in front of enemy


        int newX = x;
        int newY = y;

        //First try to move right, then forward, then left, then behind
        if(tileToRight.getTileType() == TileType.PATH || tileToRight.getTileType() == TileType.PLAYER){
            tileToRight.interact(this);

            newX = x + xDir[1];
            newY = y + yDir[1];

        }else if(tileInFront.getTileType() == TileType.PATH || tileInFront.getTileType() == TileType.PLAYER){
            tileInFront.interact(this);

            newX = x + xDir[3];
            newY = y + yDir[3];

        }else if(tileToLeft.getTileType() == TileType.PATH || tileToLeft.getTileType() == TileType.PLAYER){
            tileToLeft.interact(this);

            newX = x + xDir[0];
            newY = y + yDir[0];

        }else if(tileBehind.getTileType() == TileType.PATH || tileBehind.getTileType() == TileType.PLAYER){
            tileBehind.interact(this);

            newX = x + xDir[2];
            newY = y + yDir[2];

        }

        //Set new direction
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
