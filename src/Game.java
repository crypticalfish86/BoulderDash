import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;





public class Game {
    private String loadedPlayerProfileID;

    private final int MAX_LEVEL = 5;
    private final int SCORE_PER_SECOND_LEFT = 3;

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

        initializeMusicPlayer();

    }

    private void initializeMusicPlayer() {
        try {
            // Set the path to your music file
            String musicFilePath = "./Assets/level_music.mp3";
            musicPlayer = new MusicPlayer(musicFilePath);
        } catch (Exception e) {
            System.out.println("Failed to initialize music player.");
            e.printStackTrace();
        }
    }

    private void startMusic() {
        if (musicPlayer != null) {
            musicPlayer.play();
        }
    }

    private void stopMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
    }

    //TODO Add proper exceptions and errors like in loadgame
    private boolean saveGame() { //TODO add proper exception throwing
        if(this.currentGamesession == null){
            return false;
        }
        //Call "buildSaveString in GameSession and then write the file to the correct profile
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
     * @return
     * A boolean value that determines if the loading of the GameSession was successful.
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
                startGameWithLevel(0, 0);
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
     * @param level
     */
    private void startGameWithLevel(int level, int accumulatedScore) {

        System.out.println("reading level file");
        File levelOneFile = new File(String.format("./Levels/level%d.txt", level));

        try {
            Scanner levelFileScanner = new Scanner(levelOneFile);

            levelFileScanner.useDelimiter("\\A");

            String fileContent = levelFileScanner.next();
            levelFileScanner.close();



            this.currentGamesession = new GameSession(this, fileContent, cc, accumulatedScore);
            startMusic(); // Start music when the level starts

        } catch (FileNotFoundException e) {
            System.err.printf("level%d.txt not found.\n", level);
        }


    }



    public void onPlayButtonClicked() {
        System.out.println("play is clicked");
        mainMenu.hide();
        this.profileSelector = new ProfileSelector(this, cc);

        // TODO Auto-generated method stub
        // Run load game function here or move to profileselector to manage save screen
    }

    public void onBackButtonClicked() {
        System.out.println("back button has been clicked");
        profileSelector.hide();
        mainMenu.show();
    }

    public void onProfileBoxClicked1() {
        System.out.println("Profile box 1 has been clicked");
        this.loadedPlayerProfileID = "1";
        profileSelector.hide();
        loadGame("1");
    }

    public void onProfileBoxClicked2() {
        System.out.println("Profile box 2 has been clicked");
        this.loadedPlayerProfileID = "2";
        profileSelector.hide();
        loadGame("2");
    }

    public void onProfileBoxClicked3() {
        System.out.println("Profile box 3 has been clicked");
        this.loadedPlayerProfileID = "3";
        profileSelector.hide();
        loadGame("3");
    }


    /**
     * Should be fired when the main menu button is clicked
     */
    public void onExitToMainMenuButtonClicked() {
        System.out.println("back to main menu button has been clicked");
        leaderboardShowcase.hide();
        mainMenu.show();
        stopMusic(); // Stop the music when exiting the game

    }


    /**
     * Should be called when the game has won or lost not due to the leave menus
     * @param hasWon if this action is count as winning
     * @param gameSessionData the internal data of the game to be read
     * @param timeLeft time in milliseconds left
     */

    public void onGameOver(boolean hasWon, GameSessionData gameSessionData, long timeLeft) {
        //TODO: check Game Level

        stopMusic(); // Stop music on game over

        if (hasWon) {
            if (gameSessionData.getLevel() == -1 || gameSessionData.getLevel() >= MAX_LEVEL) {
                //TODO: show winning screen with score
                new Leaderboard().writeNewNameToLeaderboard(loadedPlayerProfileID, gameSessionData.getScore());


                gameSessionData.updateScore((int) (timeLeft * SCORE_PER_SECOND_LEFT / 1000));


                this.gameWin = new GameWin(this, cc, gameSessionData);

            } else {
                //TODO: start next game with level + 1

                int finalScore = gameSessionData.getScore() + (int) (timeLeft * SCORE_PER_SECOND_LEFT / 1000);
                startGameWithLevel(gameSessionData.getLevel() + 1, finalScore);
            }
        } else {
            this.gameOver = new GameOver(this, cc, gameSessionData);
            // showMenu();
            stopMusic(); // Stop music on game over
        }
    }

    public void onExitButtonClicked() {
        System.out.println("Exit button has been clicked");
        gameOver.hide();
        mainMenu.show();
        stopMusic();
    }

    public void onLeaderboardButtonClicked() {
        System.out.println("Leaderboard button has been clicked");
        mainMenu.hide();
        this.leaderboardShowcase = new LeaderboardShowcase(this, cc);
        leaderboardShowcase.show();

    }


    public void setPlayerProfile(String playerProfileID) {
        this.loadedPlayerProfileID = playerProfileID;
    }


    /**
     * Should be called when the player decides to leave the game (through pausing menu)
     */
    public void showMenu() {
        //TODO: implement returning to main menu
        mainMenu.show();
        stopMusic();

    }


    public String getPlayerProfileID() {
        return this.loadedPlayerProfileID;
    }



    
    public void loadGameButton() {
        profileSelector.show();
    }

    public void saveGameButton() {
        boolean saved = saveGame();
        System.out.println(saved);
    }
}
