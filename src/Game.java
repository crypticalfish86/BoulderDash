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
    private boolean loadGame(String filePath) {
        try {
            int[] totalArrOfGameData = new int[14]; // stores all game data values from file
            int currentLine = 1;
            // locate the file and load file content into the gamedata string
            Scanner input = new Scanner(profileDeterminer(filePath));
            while (input.hasNextLine()) {
                String[] profileCheck = input.nextLine().split(";");
                if (profileCheck[0].equals("new")) {
                    currentGamesession.setAllGameSession(1, 40, 30,
                            0, 180, 180, 180,
                            0, 11,
                            0, 0, 0,
                            0);
                } else if (currentLine >= 1 && currentLine <= 5) {
                    switch (currentLine) {
                        case 1:
                            totalArrOfGameData[0] = Integer.parseInt(profileCheck[0]); // currentlvl
                            totalArrOfGameData[1] = Integer.parseInt(profileCheck[1]); // height
                            totalArrOfGameData[2] = Integer.parseInt(profileCheck[2]); // width
                        case 2:
                            totalArrOfGameData[3] = Integer.parseInt(profileCheck[0]); // score
                            totalArrOfGameData[4] = Integer.parseInt(profileCheck[1]); // timeleft
                            totalArrOfGameData[5] = Integer.parseInt(profileCheck[2]); // timeallowed
                        case 3:
                            totalArrOfGameData[6] = Integer.parseInt(profileCheck[0]); // diamondcount
                            totalArrOfGameData[7] = Integer.parseInt(profileCheck[1]); // diamondsrequired
                        case 4:
                            totalArrOfGameData[8] = Integer.parseInt(profileCheck[0]); // ameobaspreadrate
                            totalArrOfGameData[9] = Integer.parseInt(profileCheck[1]); // ameobasizelimit
                        case 5:
                            totalArrOfGameData[10] = Integer.parseInt(profileCheck[0]); // redkey
                            totalArrOfGameData[11] = Integer.parseInt(profileCheck[1]); // bluekey
                            totalArrOfGameData[12] = Integer.parseInt(profileCheck[2]); // yellowkey
                            totalArrOfGameData[13] = Integer.parseInt(profileCheck[3]); // greenkey
                    }
                } else {

                }
                currentLine++;
            }
            input.close();

            currentGamesession.setAllGameSession(totalArrOfGameData[0], totalArrOfGameData[1], totalArrOfGameData[2],
                    totalArrOfGameData[3], totalArrOfGameData[4], totalArrOfGameData[5],
                    totalArrOfGameData[7], totalArrOfGameData[8],
                    totalArrOfGameData[9], totalArrOfGameData[10], totalArrOfGameData[11],
                    totalArrOfGameData[12], totalArrOfGameData[13]);
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
        loadedPlayerProfileID = String.valueOf(filePath.charAt(filePath.length() - 5));
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
        loadGame("Profiles/profile1.txt");
    }

    public void onProfileBoxClicked2() {
        System.out.println("Profile box 2 has been clicked");
        profileSelector.hide();
        loadGame("Profiles/profile2.txt");
    }

    public void onProfileBoxClicked3() {
        System.out.println("Profile box 3 has been clicked");
        profileSelector.hide();
        loadGame("Profiles/profile3.txt");
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
