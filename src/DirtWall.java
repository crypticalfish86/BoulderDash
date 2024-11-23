public class DirtWall extends Wall {

    public DirtWall(GameSession gameSession, int x, int y, TileType TileType, long operationInterval) {
        super(gameSession, x, y, TileType, operationInterval);
        this.amoebaCanSpreadToThisTile = true;
    }

    public void interact(Tile inputTileObject) {
        if (inputTileObject instanceof Player) {
            System.out.println("Dirt wall dug out by the player.");
            // Remove the dirt wall from the game grid
        }
    }

    public void updateTile(long currentTimeInMilliseconds) {
        System.out.println("DirtWall update logic here."); // Optional update behavior
    }
}
