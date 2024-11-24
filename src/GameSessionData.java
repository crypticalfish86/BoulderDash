public class GameSessionData {
    private GameSession gameSession;
    private int redKeys;
    private int blueKeys;
    private int yellowKeys;
    private int greenKeys;
    private int diamondCount;
    private int score;


    GameSession currentGame;

    public GameSessionData(GameSession gameSession, int redKeys, int blueKeys, int yellowKeys, int greenKeys, int diamondCount, int score) {
        this.gameSession = gameSession;
        this.redKeys = redKeys;
        this.blueKeys = blueKeys;
        this.yellowKeys = yellowKeys;
        this.greenKeys = greenKeys;
        this.diamondCount = diamondCount;
        this.score = score;

    }

    
    public boolean getInventoryItem(String item) {
        switch (item){
            case "rk":
                if(this.redKeys > 0){
                    return true;
                }
                return false;
            case "bk":
                if(this.blueKeys > 0){
                    return true;
                }
                return false;
            case "yk":
                if(this.yellowKeys > 0){
                    return true;
                }
                return false;
            case "gk":
                if(this.greenKeys > 0){
                    return true;
                }
                return false;
            default:
                return false;
        }
    }
    public void updateInventory(String item, boolean increment) {
        int valueChange = increment ? 1 : 0;
        switch (item){
            case "rk":
                this.redKeys += valueChange;
                return;
            case "bk":
                this.blueKeys += valueChange;
                return;
            case "yk":
                this.yellowKeys += valueChange;
                return;
            case "gk":
                this.greenKeys += valueChange;
                return;
            default:
                return;
        }
    }
    public int getScore() {
        return score;
    }
    public void updateScore(int scoreToAdd) {
        score += scoreToAdd;
    }
    public int getDiamondCount() {return this.diamondCount;}
    public void incrementDiamondCount() {
        diamondCount++;
    }



}
