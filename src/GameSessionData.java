public class GameSessionData {
    private GameSession gameSession;
    private int levelHeight;
    private int levelWidth;
    private int timeAllowed;
    private int redKeys;
    private int blueKeys;
    private int yellowKeys;
    private int greenKeys;
    private int diamondCount;
    private int score;

    // TimeAllowed, DiamondsRequired, AmeobaSpreadRate, AmeobaSizeLimit
    // Line 3: TimeLeft, Score, DiamondCount
    // Line 4: RedKey, BlueKey, YellowKey, GreenKey
    // Line 5+: Actual level


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
    public boolean tryConsumeKey(char key) {
        switch (key){
            case 'r':
                if (this.redKeys <= 0) { return false; }
                this.redKeys--;
                return true;
                
            case 'b':
                if (this.blueKeys <= 0) { return false; }
                this.blueKeys--;
                return true;

            case 'y':
                if (this.yellowKeys <= 0) { return false; }
                this.yellowKeys--;
                return true;

            case 'g':
                if (this.greenKeys <= 0) { return false; }
                this.greenKeys--;
                return true;

            default:
                System.out.printf("Error: unknown key type: %c\n", key);
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
    public void giveKey(char key) {
        switch (key){
            case 'r':
                this.redKeys++;
                break;

            case 'b':
                this.blueKeys++;
                break;

            case 'y':
                this.yellowKeys++;
                break;

            case 'g':
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



}
