public class GameSessionData {
    private int redKeys;
    private int blueKeys;
    private int yellowKeys;
    private int greenKeys;
    private int diamondCount;
    private int score;

    public static GameSessionData createGameSessionData() {
        return new GameSessionData();
    }

    public int getInventoryItem() {
        return 0;
    }

    public void UpdateInventoryItem() {
    }

    public int getScore() {
        return 0;
    }

    public void updateScore() {
    }
}
