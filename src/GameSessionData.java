/**
 * GameSessionData holds all the more static data for the GameSession in one place.
 * @version 3.0
 * @author Jace Weerawardena (crypticalfish86).
 */
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

    /**
     * Initialises a GameSessionData object which holds all the more static data for a GameSession.
     * @param gameSession
     * The GameSession associated with this data.
     * @param timeAllowed
     * The time allowed to complete the level in seconds.
     * @param diamondsRequired
     * The diamonds required to open the ExitWall so the player can advance levels.
     * @param redKeys
     * How many red keys the player has picked up.
     * @param blueKeys
     * How many blue keys the player has picked up.
     * @param yellowKeys
     * How many yellow keys the player has picked up.
     * @param greenKeys
     * How many green keys the player has picked up.
     * @param diamondCount
     * The current number of diamonds the player has picked up in this level.
     * @param score
     * The current score of the player.
     */
    public GameSessionData(GameSession gameSession, int timeAllowed, int diamondsRequired, int redKeys, int blueKeys, int yellowKeys, int greenKeys, int diamondCount, int score, int level) {
        this.gameSession = gameSession;
        this.timeAllowed = timeAllowed;
        this.diamondsRequired = diamondsRequired;
        this.redKeys = redKeys;
        this.blueKeys = blueKeys;
        this.yellowKeys = yellowKeys;
        this.greenKeys = greenKeys;
        this.diamondCount = diamondCount;
        this.score = score;
        this.level = level;

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
                this.redKeys, this.blueKeys, this.yellowKeys, this.greenKeys,
                
        };
    }

    /**
     * Set the values of game session data when loading in a save.
     * @param score
     * The current score of the player.
     * @param timeAllowed
     * The time left that you're allowed to complete the level by.
     * @param startingTime
     * The time that has already elapsed in the level.
     * @param diamondCount
     * The current number of diamonds the player has picked up.
     * @param diamondsRequired
     * The diamonds required to complete the level.
     * @param redKeys
     * The number of red keys the player has picked up.
     * @param blueKeys
     * The number of blue keys the player has picked up.
     * @param yellowKeys
     * The number of yellow keys the player has picked up.
     * @param greenKeys
     * The number of green keys the player has picked up.
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
     * checks whether the GameSessionData has logged that the user has a key in their inventory.
     * @param key
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
     * @param key
     * A string that represents the key colour they are updating.
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
     * The current score of the GameSession.
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
    public int getDiamondCount() {
        return this.diamondCount;
    }

    /**
     * Increments the diamond count.
     */
    public void incrementDiamondCount() {

        diamondCount++;
    }

    /**
     * Get the number of diamonds required to open the exit door.
     * @return
     * The number of diamonds required to open the exit door.
     */
    public int getDiamondsRequired() {

        return this.diamondsRequired;
    }


    /**
     * Reads the level that this stage represents.
     * @return int of level, can be -1 to indicate if level is non-conventional.
     */
    public int getLevel() {

        return this.level;
    }
}
