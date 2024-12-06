import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;




public class Game {
    private String loadedPlayerProfileID;

    
    


    private final CanvasCompositor cc;

    private MainMenu mainMenu;
    private ProfileSelector profileSelector;
    private GameSession currentGamesession;
    private GameOver gameOver;

    //currentGameSession and loadedPlayerProfileID do not get instantiated in the constructor as they are variable (debatable whether anything gets instantiated in the constructor
    public Game(CanvasCompositor cc) {

        this.mainMenu = new MainMenu(this, cc);
        this.cc = cc;
    }



    //TODO Add proper exceptions and errors like in loadgame
    private boolean saveGame() throws IOException { //TODO add proper exception throwing
        if(this.currentGamesession == null){
            return false;
        }
        //Call "buildSaveString in GameSession and then write the file to the correct profile
        String saveString = this.currentGamesession.buildSaveString();

        String filepath = "./Levels/profile" + this.loadedPlayerProfileID + ".txt";


        FileWriter fileToSaveTo = new FileWriter(filepath);
        fileToSaveTo.write(saveString);
        return true;
    }

    /**
     * When a profile is clicked, reads a profile file and initialises a GameSession with that file data passed to it.
     * @param profileID
     * The profile number.
     * @return
     * A boolean value that determines if the loading of the GameSession was successful.
     */
    public boolean loadGame(String profileID) {
        if(profileID.equals("1") || profileID.equals("2") || profileID.equals("3")){
            this.loadedPlayerProfileID = profileID;
        }
        else {
            return false;
        }

        String filepath = "./Profiles/profile" + profileID + ".txt";

        try {
            File file = new File(filepath);
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\\A"); // Read entire file as one string

            String fileContent;
            if(scanner.hasNext()){
                System.out.println("file read");
                fileContent = scanner.next();
            }
            else{
                return false;
            }
            System.out.println(fileContent);
            //If the content of the file is new then load in the default file
            if(fileContent.equals("new;")){
                //TODO read default level package
                System.out.println("reading level file");
                File levelOneFile = new File("./Levels/level1.txt");
                Scanner levelOneFileScanner = new Scanner(levelOneFile);
                levelOneFileScanner.useDelimiter("\\A");
                fileContent = levelOneFileScanner.next();
                this.currentGamesession = new GameSession(this, fileContent, cc);
            } else {
                this.currentGamesession = new GameSession(this, fileContent, cc);
            }
            scanner.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public void onPlayButtonClicked() {
        System.out.println("play is clicked");
        mainMenu.hide();
        if (this.profileSelector == null) {
            this.profileSelector = new ProfileSelector(this, cc);
        } else {
            this.profileSelector.show();
        }

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

    public void onPlayerDeath() {
        this.gameOver = new GameOver(this, cc);
    }

    public void onExitButtonClicked() {
        System.out.println("Exit button has been clicked");
        gameOver.hide();
        mainMenu.show();
    }


    public void setPlayerProfile(String playerProfileID) {
        this.loadedPlayerProfileID = playerProfileID;
    }

    public int getPlayerScoreForGameOverScreen() {
        return currentGamesession.getScoreForGameOverScreen();
    }

    public String getPlayerProfileID() {
        return this.loadedPlayerProfileID;
    }
    
    /**
     * Shows the losing screen.
     */
    public void endGame() {
        //TODO: implement returning to main menu
        // mainMenu.show();
    }
}
