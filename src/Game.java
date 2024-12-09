import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/**
 * Represents the one single game that handles loading and saving the game.
 * @author Jace Weerawardena
 * @author Alex (Tsz Tung Yee)
 * @author Armaan Ghadiali
 * @author Cameron McDonald
 * @version 3.0
 */


/**
 * Constructs a game.
 */
public class Game {
    private String loadedPlayerProfileID;

    private static final int MAX_LEVEL = 5;
    private static final int SCORE_PER_SECOND_LEFT = 3;

    private MusicPlayer musicPlayer;

    private final CanvasCompositor cc;

    private MainMenu mainMenu;
    private ProfileSelector profileSelector;
    private GameSession currentGamesession;
    private DisplayLayer gameOver;
    private LeaderboardShowcase leaderboardShowcase;
    private GameWin gameWin;

    //currentGameSession and loadedPlayerProfileID do not get instantiated in the constructor as they are variable (debatable whether anything gets instantiated in the constructor
    public Game(CanvasCompositor cc) {

        this.mainMenu = new MainMenu(this, cc);
        this.cc = cc;

        initialiseMusicPlayer();

    }

    /**
     * Initialises the music player by setting the path to the music file
     * and creates a new instance of the MusicPlayer class.
     * If initialisation fails, an error message is printed to the console.
     */
    private void initialiseMusicPlayer() {
        try {
            // Set the path to your music file
            String musicFilePath = "./Assets/level_music.mp3";
            musicPlayer = new MusicPlayer(musicFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts playing the music using the MusicPlayer instance.
     * If the music player has not been initialised, this method does nothing.
     */
    private void startMusic() {
        if (musicPlayer != null) {
            musicPlayer.play();
        }
    }

    /**
     * Stops the music playback using the MusicPlayer instance.
     * If the music player has not been initialised, this method does nothing.
     */
    private void stopMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
    }

    /**
     * Save the current game state.
     * @return True if the game saves properly, false otherwise.
     */
    private boolean saveGame() {
        if(this.currentGamesession == null){
            return false;
        }
        //Call buildSaveString in GameSession and then write the file to the correct profile
        String saveString = this.currentGamesession.buildSaveString();

        String filepath = "./Profiles/profile" + this.loadedPlayerProfileID + ".txt";

        try {
            FileWriter fileToSaveTo = new FileWriter(filepath);
            fileToSaveTo.write(saveString);
            fileToSaveTo.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        
    }

    /**
     * When a profile is clicked, reads a profile file and initialises a GameSession with that file data passed to it.
     * @param profileID
     * The profile number.
     * @return A boolean value that determines if the loading of the GameSession was successful.
     */
    public boolean loadGame(String profileID) {
        if (profileID.equals("1") || profileID.equals("2") || profileID.equals("3")) {
            this.loadedPlayerProfileID = profileID;
        } else {
            return false;
        }

        String filepath = "./Profiles/profile" + profileID + ".txt";

        try {
            File file = new File(filepath);
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\\A");

            String fileContent;
            if (scanner.hasNext()) {
                fileContent = scanner.next();
                scanner.close();
            } else {
                scanner.close();
                return false;
            }

            if (fileContent.equals("NEW;")) {
                startGameWithLevel(1, 0);
            } else {
                this.currentGamesession = new GameSession(this, fileContent, cc, 0);
            }

            startMusic(); // Start music when game is loaded
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Starts a game with a specified level
     * @param level Level number to indicate which level file to load.
     * @param accumulatedScore Score up to the point where the player is about
     *                         to enter the next level.
     */
    private void startGameWithLevel(int level, int accumulatedScore) {


        File levelOneFile = new File(String.format("./Levels/level%d.txt", level));

        try {
            Scanner levelFileScanner = new Scanner(levelOneFile);

            levelFileScanner.useDelimiter("\\A");

            String fileContent = levelFileScanner.next();
            levelFileScanner.close();



            this.currentGamesession = new GameSession(this,
                    fileContent, cc, accumulatedScore);
            startMusic(); // Start music when the level starts

        } catch (FileNotFoundException e) {
            System.err.printf("level%d.txt not found.\n", level);
        }


    }

    /**
     * When clicked, the player is taken to the profile selector from the main
     * menu to select a profile.
     */
    public void onPlayButtonClicked() {
        mainMenu.hide();
        this.profileSelector = new ProfileSelector(this, cc);
    }

    /**
     * When clicked, the player is taken back to the previous page (in this
     * case the main menu from the profile selector).
     */
    public void onBackButtonClicked() {
        profileSelector.hide();
        mainMenu.show();
    }


    /**
     * Should be fired when the main menu button is clicked
     */
    public void onExitToMainMenuButtonClicked() {
        leaderboardShowcase.hide();
        mainMenu.show();
        stopMusic(); // Stop the music when exiting the game

    }

    /**
     * Deletes profile 1 when clicked in the profile selector.
     */
    public void onDeleteButtonClicked1() {
        profileSelector.deleteProfile(profileSelector.getProfile1());
        this.profileSelector = new ProfileSelector(this, cc);
    }

    /**
     * Deletes profile 2 when clicked in the profile selector.
     */
    public void onDeleteButtonClicked2() {
        profileSelector.deleteProfile(profileSelector.getProfile2());
        this.profileSelector = new ProfileSelector(this, cc);
    }

    /**
     * Deletes profile 3 when clicked in the profile selector.
     */
    public void onDeleteButtonClicked3() {
        profileSelector.deleteProfile(profileSelector.getProfile3());
        this.profileSelector = new ProfileSelector(this, cc);
    }

    /**
     * Starts profile 1 when clicked in the profile selector.
     */
    public void onContinueButtonClicked1() {
        this.loadedPlayerProfileID = "1";
        profileSelector.hide();
        loadGame("1");
    }

    /**
     * Starts profile 2 when clicked in the profile selector.
     */
    public void onContinueButtonClicked2() {
        this.loadedPlayerProfileID = "2";
        profileSelector.hide();
        loadGame("2");
    }

    /**
     * Starts profile 3 when clicked in the profile selector.
     */
    public void onContinueButtonClicked3() {
        this.loadedPlayerProfileID = "3";
        profileSelector.hide();
        loadGame("3");
    }


    /**
     * Should be called when the game has won or lost not due to the leave menus
     * @param hasWon if this action is count as winning
     * @param gameSessionData the internal data of the game to be read
     * @param timeLeft time in milliseconds left
     */
    public void onGameOver(boolean hasWon, GameSessionData gameSessionData, long timeLeft) {
        stopMusic(); // Stop music on game over

        if (hasWon) {
            new Leaderboard(Integer.toString(gameSessionData.getLevel()))
                    .writeNewNameToLeaderboard(loadedPlayerProfileID, gameSessionData.getScore());

            if (gameSessionData.getLevel() == -1 || gameSessionData.getLevel() >= MAX_LEVEL) {
                gameSessionData.updateScore((int) (timeLeft * SCORE_PER_SECOND_LEFT / 1000));
                this.gameOver = new GameWin(this, cc, gameSessionData);
            } else {
                int finalScore = gameSessionData.getScore() + (int) (timeLeft * SCORE_PER_SECOND_LEFT / 1000);
                startGameWithLevel(gameSessionData.getLevel() + 1, finalScore);
            }
        } else {
            this.gameOver = new GameOver(this, cc, gameSessionData);
            stopMusic(); // Stop music on game over
        }
    }

    /**
     * When clicked, the game will end and the player is returned to the main
     * menu.
     */
    public void onExitButtonClicked() {
        gameOver.hide();
        mainMenu.show();
        stopMusic();
    }

    /**
     * When clicked, the game will send you to the leaderboard screen to see
     * the top 10 scores.
     */
    public void onLeaderboardButtonClicked() {
        mainMenu.hide();
        this.leaderboardShowcase = new LeaderboardShowcase(this, cc);
        leaderboardShowcase.show();
    }

    /**
     * Should be called when the player decides to leave the game
     * (through pausing menu).
     */
    public void showMenu() {
        mainMenu.show();
        stopMusic();
    }

    public void loadGameButton() {
        profileSelector.show();
    }

    public void saveGameButton() {
        boolean saved = saveGame();
        System.out.println(saved);
    }
}
