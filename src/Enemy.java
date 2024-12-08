/**
 * This class represents a blueprint for enemies in the game, which move around the grid,
 * kill the player and cause an explosion when hit by falling objects.
 * @author Isaac Atkinson
 * @author Alex (Tsz Tung Yee)
 * @author Jace Weerawardena
 * @version 1.1
 */




public abstract class Enemy extends Tile {

    /**
     * Constructor blueprint for Enemies.
     * @param gameSession The current game session.
     * @param x the x position of the enemy.
     * @param y the y position of the enemy.
     * @param operationInterval The time in ms between operations.
     */
    public Enemy(GameSession gameSession, int x, int y, long operationInterval) {
        super(gameSession, x, y, TileType.MOVING_ENEMY, operationInterval);
    }




    /**
     * Converts all explodable tiles in a certain radius to an explosion,
     * centered around the enemy that is exploding.
     * @param xPosition the x position of the enemy.
     * @param yPosition the y position of the enemy.
     * @param replaceWithDiamond represents whether the explosion should leave behind
     *                           a diamond once it disappears.
     */
    protected void triggerExplosion(int xPosition, int yPosition, Boolean replaceWithDiamond){
        int startX;
        int endX;
        int startY;
        int endY;

        //Check if enemy at bottom layer of grid
        if(yPosition == gameSession.getGridHeight() - 1) {
            startY = yPosition;
        }else{
            startY = yPosition - 1;
        }

        //Check if enemy at top layer of grid
        if(yPosition == 0) {
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
        for(int i = startX; i < endX + 1; i++){
            for(int j = startY; j < endY + 1; j++){
                if ((isExplodable(gameSession.getTileFromGrid(i,j)))){ //Check if tile can explode
                    Explosion explosion = new Explosion(gameSession, i, j, operationInterval, replaceWithDiamond);
                    gameSession.setTile(j, i, explosion);
                }

                if((gameSession.getTileFromGrid(i,j).getTileType() == TileType.PLAYER)){
                    Explosion explosion = new Explosion(gameSession, i, j, operationInterval, replaceWithDiamond);
                    gameSession.setTile(j, i, explosion);

                    gameSession.callKillPlayer();
                }
            }
        }
    }



    /**
     * moves this item to a target x and y.
     * @param x
     * @param y
     * @return
     */
    protected boolean moveTo(int x, int y) {
        if (!isXYInBounds(x, y)) { return false; }
        Tile targetTile = gameSession.getTileFromGrid(x, y);


        if (targetTile.tileType == TileType.PATH || targetTile.tileType == TileType.PLAYER) {
            targetTile.interact(this);
            return true;
        } else {
            return false;
        }
    }



    /**
     * Checks whether a tile can explode based on its type.
     * @param tile the tile to check.
     * @return true if the tile can be affected by explosion, false otherwise.
     */

    private boolean isExplodable(Tile tile) {
        switch (tile.getTileType()) {
            case BOULDER:
            case DIAMOND:
            case DOOR:
            case KEY:
            case MOVING_ENEMY:
            case AMOEBA:
            case DIRT_WALL:
            case MAGIC_WALL:
            case NORMAL_WALL:
            case PATH:
                return true;
                
            default:
                return false;
        }
    }



    /**
     * checks if a given x and y is in bounds.
     * @param x The x position to check.
     * @param y The y position to check.
     * @return True if the position is in bounds, false otherwise.
     */
    protected boolean isXYInBounds(int x, int y) {
        int gridHeight = gameSession.getGridHeight();
        int gridWidth = gameSession.getGridWidth();

        if (x >= 0 && x < gridWidth && y >= 0 && y < gridHeight) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines if an enemy is next to an amoeba in any of the four cardinal directions.
     * @return true if there is an amoeba next to the enemy, false otherwise.
     */
    protected boolean isNextToAmoeba(int x, int y){
        if(x != 0){
            if(gameSession.getTileFromGrid(this.x - 1, this.y).getTileType() == TileType.AMOEBA){
                return true;
            }
        }
        if(this.x != gameSession.getGridWidth() - 1){
            if(gameSession.getTileFromGrid(this.x + 1, this.y).getTileType() == TileType.AMOEBA){
                return true;
            }
        }
        if(this.y != 0){
            if(gameSession.getTileFromGrid(this.x, this.y - 1).getTileType() == TileType.AMOEBA){
                return true;
            }
        }
        if(this.y != gameSession.getGridHeight() - 1){
            if(gameSession.getTileFromGrid(this.x, this.y + 1).getTileType() == TileType.AMOEBA){
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the player is one tile away in any of the four cardinal directions and if so kills the player.
     */
    protected void checkForPlayer(){
        if(this.gameSession.getTileFromGrid(this.x - 1, this.y).getTileType() == TileType.PLAYER){
            this.gameSession.onGameOver(false);
        } else if(this.gameSession.getTileFromGrid(this.x + 1, this.y).getTileType() == TileType.PLAYER){
            this.gameSession.onGameOver(false);
        } else if(this.gameSession.getTileFromGrid(this.x,this.y - 1).getTileType() == TileType.PLAYER){
            this.gameSession.onGameOver(false);
        } else if(this.gameSession.getTileFromGrid(this.x,this.y + 1).getTileType() == TileType.PLAYER){
            this.gameSession.onGameOver(false);
        }
    }
}


