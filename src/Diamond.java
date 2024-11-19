public class Diamond extends FallingObject{
    private final static int SCORE_VALUE = 50;
    public Diamond(GameSession gameSession, int x, int y, TileType TileType, long operationInterval){
        super(gameSession, x, y, TileType, operationInterval);
    }
}
