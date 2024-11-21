public class GameSessionData {
    private int redKeys;
    private int blueKeys;
    private int yellowKeys;
    private int greenKeys;
    private int diamondCount;
    private int score;

    
    private GameSession gameSession;

    public GameSessionData(GameSession gameSession) {
        this.gameSession = gameSession;
    }
    public void getInventoryItem(String item) {

    }
    public void updateInventory(String item, boolean increment) {
        
    }
    public int getScore() {
        return score;
    }


    public void updateScore(int scoreToAdd) {
        //TODO: update the display for the score


    }

    /*
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
        //TODO This is Ken's work.
        */


}
