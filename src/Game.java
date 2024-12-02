import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;




public class Game {
    private String loadedPlayerProfileID;

    
    


    private CanvasCompositor cc;

    private MainMenu mainMenu;
    private ProfileSelector profileSelector;
    private GameSession currentGamesession;

    //currentGameSession and loadedPlayerProfileID do not get instantiated in the constructor as they are variable (debatable whether anything gets instantiated in the constructor
    public Game(CanvasCompositor cc) {





        this.mainMenu = new MainMenu(this, cc);




        this.cc = cc;


        
    }


    /**
     * @param filePath file
     * @return if the file exists, and the game loads
    */
    private boolean loadGame(CanvasCompositor cc, String filePath) {
        try {
            // locate the file and load file content into the gamedata string
            Scanner input = new Scanner(profileDeterminer(filePath));
            while (input.hasNextLine()) {

            }
            input.close();
        } catch (FileNotFoundException e) {
            System.err.println("File " + filePath + " not found.");
            return false;
        } catch (Exception e) {
            System.err.println("Unknown Error.");
            return false;
        }


        // this.currentGamesession = new GameSession(this, gameData, gamePane);
        return true;
    }

    /**
     * @param filePath file
     * @return assigns the profileID and returns the appropiate profile file to read
     */
    private File profileDeterminer(String filePath) {
        loadedPlayerProfileID = String.valueOf(filePath.charAt(filePath.length() - 1));
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
    }

    public void onProfileBoxClicked2() {
        System.out.println("Profile box 2 has been clicked");
        profileSelector.hide();
    }

    public void onProfileBoxClicked3() {
        System.out.println("Profile box 3 has been clicked");
        profileSelector.hide();
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
