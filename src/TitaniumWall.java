public class TitaniumWall extends Wall {

    public TitaniumWall(GameSession gameSession, int x, int y, TileType TileType, long operationInterval) {
        super(gameSession, x, y, TileType, operationInterval);
        this.amoebaCanSpreadToThisTile = false;
    }

    public void interact(Tile inputTileObject) {
        System.out.println("Titanium walls cannot be destroyed or interacted with.");
    }

    public void updateTile(long currentTimeInMilliseconds) {
        System.out.println("TitaniumWall update logic here."); // Update logic if needed
    }
}
