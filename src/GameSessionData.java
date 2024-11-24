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

    /**
     * checks whether the gamesession data has logged that the user has a key in their inventory.
     * @param item
     * The string that represents the key colour.
     * @return
     * returns true if the user has picked up at least one key of that colour, otherwise returns false.
     */
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

    /**
     * Changes how many of a particular item the user has in their inventory.
     * @param item
     * A string that represents the key colour they are updating.
     * @param increment
     * increments if true, otherwise decrements.
     */
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

    /**
     * gets the current score of the game.
     * @return
     * The current score of the gamesession.
     */
    public int getScore() {
        return score;
    }

    /**
     * Updates the current score of the game.
     * @param scoreToAdd
     * The amount we are adding to the score.
     */
    public void updateScore(int scoreToAdd) {
        score += scoreToAdd;
    }

    /**
     * gets how many diamonds the user has picked up.
     * @return
     * how many diamonds the user has picked up.
     */
    public int getDiamondCount() {return this.diamondCount;}

    /**
     * Increments the diamond count.
     */
    public void incrementDiamondCount() {
        diamondCount++;
    }



}
