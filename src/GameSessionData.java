public class GameSessionData {
    private GameSession gameSession;
    private int timeAllowed;
    private int startingTime;
    private int diamondsRequired; // Diamonds required to complete the level
    private int redKeys;
    private int blueKeys;
    private int yellowKeys;
    private int greenKeys;
    private int diamondCount;
    private int score;

    GameSession currentGame;

    public GameSessionData(GameSession gameSession, int timeAllowed, int diamondsRequired, int redKeys, int blueKeys, int yellowKeys, int greenKeys, int diamondCount, int score) {
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
     * Gets the total diamonds required to activate the exit.
     * @return The number of diamonds required.
     */
    public int getDiamondsRequired() {
        return this.diamondsRequired;
    }

    /**
     * Return all game session data of the current session.
     * @return All the data of the current session.
     */
    public int[] returnAllGameSessionData() {
        return new int[]{
                this.score, this.timeAllowed - this.startingTime, this.timeAllowed,
                this.diamondCount, this.diamondsRequired,
                this.redKeys, this.blueKeys, this.yellowKeys, this.greenKeys
        };
    }

    /**
     * Checks whether the game session data has logged that the user has a key in their inventory.
     * @param key The key color.
     * @return Returns true if the user has picked up at least one key of that color; otherwise, returns false.
     */
    public boolean tryConsumeKey(char key) {
        switch (key) {
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
     * @param key The key color they are updating.
     */
    public void giveKey(char key) {
        switch (key) {
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
     * Gets the current score of the game.
     * @return The current score of the game session.
     */
    public int getScore() {
        return score;
    }

    /**
     * Updates the current score of the game.
     * @param scoreToAdd The amount to add to the score.
     */
    public void updateScore(int scoreToAdd) {
        score += scoreToAdd;
    }

    /**
     * Gets how many diamonds the user has picked up.
     * @return How many diamonds the user has picked up.
     */
    public int getDiamondCount() {
        return this.diamondCount;
    }

    /**
     * Increments the diamond count.
     */
    public void incrementDiamondCount() {
        diamondCount++;
    }
}
