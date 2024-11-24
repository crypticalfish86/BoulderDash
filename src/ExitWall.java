public class ExitWall extends Wall {

    private boolean isActive;

    public ExitWall(GameSession gameSession, int x, int y, TileType TileType, long operationInterval) {
        super(gameSession, x, y, TileType, operationInterval);
        this.isActive = false;
        this.amoebaCanSpreadToThisTile = false;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void activate() {
        this.isActive = true;
        System.out.println("Exit wall activated!");
    }

    public void interact(Tile inputTileObject) {
        if (this.isActive && inputTileObject instanceof Player) {
            System.out.println("Player exits through the exit wall!");
            changeLevel();
        } else {
            System.out.println("Exit wall is not active.");
        }
    }

    public void updateTile(long currentTimeInMilliseconds) {
        System.out.println("ExitWall update logic here."); // Optional update behavior
    }

    public void changeLevel() {
        System.out.println("Level changed!");
        // Add logic to load the next level
    }
}
