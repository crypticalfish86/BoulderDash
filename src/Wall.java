/**
 * Represents a wall tile in the game.
 * Author: Cameron McDonald (cmcoff) & Jace Weerawardena(crypticalfish86).
 * Version: 1.0
 * Walls are immovable tiles that act as barriers. This is an abstract
 * base class for other types of walls like titanium walls.
 *
 */
public abstract class Wall extends Tile {

    /**
     * Creates a new Wall object.
     *
     * @param gameSession       the game session this wall will go in.
     * @param x                 the x-coordinate of the wall in the 2Dgrid.
     * @param y                 the y-coordinate of the wall in the 2Dgrid.
     * @param TileType          the type of wall.
     * @param operationInterval the time interval for any updates.
     */
    public Wall(GameSession gameSession, int x, int y, TileType TileType, long operationInterval) {
        super(gameSession, x, y, TileType, operationInterval);
    }
}
