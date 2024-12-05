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

    //currentGameSession and loadedPlayerProfileID do not get instantiated in the constructor as they are variable (debatable whether anything gets instantiated in the constructor
    public Game(CanvasCompositor cc) {

        this.mainMenu = new MainMenu(this, cc);
        this.cc = cc;
    }





    /**
     * @return assigns the profileID and returns the appropiate profile file to read
     */
    private File profileDeterminer() {
        if (loadedPlayerProfileID.equals("1")) {
            return profileSelector.getProfile1();
        } else if (loadedPlayerProfileID.equals("2")) {
            return profileSelector.getProfile2();
        } else {
            return profileSelector.getProfile3();
        }
    }

    //TODO Add proper exceptions and errors like in loadgame
    private boolean saveGame() throws IOException { //TODO add proper exception throwing
        if(this.currentGamesession == null){
            return false;
        }
        //Call "buildSaveString in GameSession and then write the file to the correct profile
        String saveString = this.currentGamesession.buildSaveString();

        String filepath = "..\\Levels" + this.loadedPlayerProfileID;


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

        String filepath = "..\\Profiles\\profile" + profileID + ".txt";

        try {
            File file = new File(filepath);
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\\Z"); // Read entire file as one string

            if (scanner.hasNext()) {
                this.currentGamesession = new GameSession(this, scanner.next(), cc);
                scanner.close();
                return true;
            } else {
                scanner.close();
                return false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
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
        profileSelector.hide();
        loadGame("1");
    }

    public void onProfileBoxClicked2() {
        System.out.println("Profile box 2 has been clicked");
        profileSelector.hide();
        loadGame("2");
    }

    public void onProfileBoxClicked3() {
        System.out.println("Profile box 3 has been clicked");
        profileSelector.hide();
        loadGame("3");
    }


    public void setPlayerProfile(String playerProfileID) {
        this.loadedPlayerProfileID = playerProfileID;
    }


    public void startSampleGame() {
        
        System.out.println("starting a game");
        mainMenu.hide();

        if (this.currentGamesession != null) { return; }
        this.currentGamesession = new GameSession(this, loadedPlayerProfileID, cc);
        
    }
    

    public void endGame() {
        //TODO: implement returning to main menu
        mainMenu.show();
    }
}
