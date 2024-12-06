public class GameSessionData {
    private GameSession gameSession;
    private int timeAllowed;
    private int startingTime;
    private int diamondsRequired;
    private int redKeys;
    private int blueKeys;
    private int yellowKeys;
    private int greenKeys;
    private int diamondCount;
    private int score;

    private int level;

    // TimeAllowed, DiamondsRequired, AmeobaSpreadRate, AmeobaSizeLimit
    // Line 3: TimeLeft, Score, DiamondCount
    // Line 4: RedKey, BlueKey, YellowKey, GreenKey
    // Line 5+: Actual level


    GameSession currentGame;

    public GameSessionData(GameSession gameSession, int timeAllowed, int diamondsRequired ,int redKeys, int blueKeys, int yellowKeys, int greenKeys, int diamondCount, int score) {
        this.gameSession = gameSession;
        this.timeAllowed = timeAllowed;
        this.diamondsRequired = diamondsRequired;
        this.redKeys = redKeys;
        this.blueKeys = blueKeys;
        this.yellowKeys = yellowKeys;
        this.greenKeys = greenKeys;
        this.diamondCount = diamondCount;
        this.score = score;

    }

    /**
     * Return all game session data of the current session.
     * @return
     * All the data of the current session.
     */
    public int[] returnAllGameSessionData(){
        return new int[]{
                this.score, this.timeAllowed - this.startingTime, this.timeAllowed,
                this.diamondCount, this.diamondsRequired,
                this.redKeys, this.blueKeys, this.yellowKeys, this.greenKeys
        };
    }

    /**
     * Set the values of game session data when loading in a save
     * @param score
     * @param timeAllowed
     * @param startingTime
     * @param diamondCount
     * @param diamondsRequired
     * @param redKeys
     * @param blueKeys
     * @param yellowKeys
     * @param greenKeys
     */
    public void setAllGameSessionData(int score, int timeAllowed, int startingTime,
                                      int diamondCount, int diamondsRequired,
                                      int redKeys, int blueKeys, int yellowKeys, int greenKeys, int level) {
        this.score = score;
        this.timeAllowed = timeAllowed;
        this.startingTime = startingTime;
        this.diamondCount = diamondCount;
        this.diamondsRequired = diamondsRequired;
        this.redKeys = redKeys;
        this.blueKeys = blueKeys;
        this.yellowKeys = yellowKeys;
        this.greenKeys = greenKeys;
        this.level = level;
    }

    /**
     * checks whether the gamesession data has logged that the user has a key in their inventory.
     * @param item
     * The string that represents the key colour.
     * @return
     * returns true if the user has picked up at least one key of that colour, otherwise returns false.
     */
    public boolean tryConsumeKey(String key) {
        switch (key) {
            case "RK": // Red Key
            case "RD": // Red Door requires Red Key
                if (this.redKeys <= 0) { return false; }
                this.redKeys--;
                return true;

            case "BK": // Blue Key
            case "BD": // Blue Door requires Blue Key
                if (this.blueKeys <= 0) { return false; }
                this.blueKeys--;
                return true;

            case "YK": // Yellow Key
            case "YD": // Yellow Door requires Yellow Key
                if (this.yellowKeys <= 0) { return false; }
                this.yellowKeys--;
                return true;

            case "GK": // Green Key
            case "GD": // Green Door requires Green Key
                if (this.greenKeys <= 0) { return false; }
                this.greenKeys--;
                return true;

            default:
                System.out.printf("Error: unknown key type: %s\n", key);
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
    public void giveKey(String key) {
        switch (key){
            case "RK":
                this.redKeys++;
                break;

            case "BK":
                this.blueKeys++;
                break;

            case "YK":
                this.yellowKeys++;
                break;

            case "GK":
                this.greenKeys++;
                break;

            default:
                System.out.printf("Error: unknown key type: %c\n", key);
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

    public int getDiamondsRequired() {
        return this.diamondsRequired;
    }


    /**
     * Reads the level that this staage represents
     * @return int of level, can be -1 to indicate if level is non-conventional
     */
    public int getLevel() {
        return this.level;
    }
}
