public abstract class Enemy extends Tile {
    public Enemy(GameSession gameSession, int x, int y, TileType TileType, long operationInterval) {
        super(gameSession, x, y, TileType, operationInterval);
    }




    /**
     * Converts all explodable tiles in a certain radius to an explosion,
     * centered around the enemy that is exploding
     * @param xPosition the x position of the enemy
     * @param yPosition the y position of the enemy
     * @param replaceWithDiamond represents whether the explosion should leave behind
     *                           a diamond once it disappears
     */
    protected void triggerExplosion(int xPosition, int yPosition, Boolean replaceWithDiamond){
        int startX;
        int endX;
        int startY;
        int endY;

        //Check if enemy at bottom layer of grid
        if(yPosition == 0) {
            startY = yPosition;
        }else{
            startY = yPosition - 1;
        }

        //Check if enemy at top layer of grid
        if(yPosition == gameSession.getGridHeight() - 1) {
            endY = yPosition;
        }else{
            endY = yPosition + 1;
        }

        //Check if enemy on left-most row of grid
        if(xPosition == 0) {
            startX = xPosition;
        }else{
            startX = xPosition - 1;
        }

        //Check if enemy on right-most row of grid
        if(xPosition == gameSession.getGridWidth() - 1) {
            endX = xPosition;
        }else{
            endX = xPosition + 1;
        }

        //Convert each tile in explosion radius to an explosion if it can be exploded
        for(int i = startX; i <= endX; i++){
            for(int j = startY; j <= endY; j++){
                if ((isExplodable(gameSession.getTileFromGrid(i,j)))){ //Check if tile can explode
                    Explosion explosion = new Explosion(gameSession, i, j, operationInterval, replaceWithDiamond);
                    gameSession.setTile(i, j, explosion);
                }

                if((gameSession.getTileFromGrid(i,j).getTileType() == TileType.PLAYER)){
                    Explosion explosion = new Explosion(gameSession, i, j, operationInterval, replaceWithDiamond);
                    gameSession.setTile(i, j, explosion);
                    //gameSession.callKillPlayer();
                }
            }
        }
    }



    /**
     * moves this item to a target x and y
     * @param x
     * @param y
     * @return
     */
    protected boolean moveTo(int x, int y) {
        if (!isXYInBounds(x, y)) { return false; }
        Tile targetTile = gameSession.getTileFromGrid(x, y);


        if (targetTile.tileType == TileType.PATH) {
            targetTile.interact(this);
            return true;
        } else {
            return false;
        }
    }



    /**
     * Checks whether a tile can explode based on its type
     * @param tile the tile to check
     * @return true if the tile can be affected by explosion, false otherwise
     */
    private boolean isExplodable(Tile tile) {
        switch (tile.getTileType()) {
            case FALLING_OBJECT:
            case DOOR:
            case KEY:
            case MOVING_ENEMY:
            case AMOEBA:
            case DIRT_WALL:
            case MAGIC_WALL:
            case NORMAL_WALL:
                return true;
                
            default:
                return false;
        }
    }



    /**
     * checks if a given x and y is in bounds
     * @param x
     * @param y
     * @return
     */
    private boolean isXYInBounds(int x, int y) {
        int gridHeight = gameSession.getGridHeight();
        int gridWidth = gameSession.getGridWidth();

        if (x >= 0 && x < gridWidth && y >= 0 && y < gridHeight) {
            return true;
        } else {
            return false;
        }
    }
}


