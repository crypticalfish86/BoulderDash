public abstract class Enemy extends Tile{
    public Enemy(GameSession gameSession, int x, int y, TileType TileType, long operationInterval){
        super(gameSession, x, y, TileType, operationInterval);
    }

    protected void moveTo(){};


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

            }
        }
    }

    /**
     * Checks whether a tile can explode based on its type
     * @param tile the tile to check
     * @return true if the tile can explode, false otherwise
     */
    private boolean isExplodable(Tile tile){
        if (tile.getTileType() == TileType.FALLING_OBJECT){
            return true;
        }else if (tile.getTileType() == TileType.DOOR){
            return true;
        }else if (tile.getTileType() == TileType.KEY){
            return true;
        }else if (tile.getTileType() == TileType.MOVING_ENEMY){
            return true;
        }if (tile.getTileType() == TileType.AMOEBA){
            return true;
        }else if (tile instanceof DirtWall){
            return true;
        }else if (tile instanceof MagicWall) {
            return true;
        }else if (tile instanceof NormalWall) {
            return true;
        }else{
            return false;
        }

    }
}


