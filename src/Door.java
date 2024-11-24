public class Door extends Wall {

    private char doorColour;

    public Door(GameSession gameSession, int x, int y, TileType TileType, long operationInterval, char doorColour) {
        super(gameSession, x, y, TileType, operationInterval);
        this.doorColour = doorColour;
        this.amoebaCanSpreadToThisTile = false;
    }

    public char getDoorColour() {
        return this.doorColour;
    }

    public void interact(Tile inputTileObject) {
        if (inputTileObject instanceof Key && ((Key) inputTileObject).getKeyColour() == this.doorColour) {
            System.out.println("Door unlocked with the correct key!");
            // Logic to open the door or remove it
        } else {
            System.out.println("Door cannot be opened without the correct key.");
        }
    }

    public void updateTile(long currentTimeInMilliseconds) {
        System.out.println("Door update logic here."); // Optional update behavior
    }
}
