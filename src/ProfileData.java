import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class represents all the data for a profile.
 * @author Jace Weerawardena
 * @version 1.0
 */

public class ProfileData {
    private boolean profileNotInstantiated;
    private File profileFile;
    private String levelNumber;
    private String score;
    private String timeRemaining;
    private String diamondCount;

    /**
     * Constructs the ProfileData class which stores all displayable data of a profile.
     * @param profileID
     * The profileID number.
     * @throws IOException
     * If somehow the file is not found then an IOException is thrown.
     */
    public ProfileData(String profileID) throws IOException {
        String filepath = "..\\Profiles\\" + profileID;
        File profileFile = new File(filepath);

        if(!profileFile.exists()){
            this.profileNotInstantiated = true;
        }
        else{
            this.profileNotInstantiated = false;
            this.profileFile = profileFile;
            readProfileData();
        }
    }

    /**
     * Reads the data from the profile and assigns that data to relevant attributes.
     */
    private void readProfileData() throws IOException {

        try{
            Path filePath = profileFile.toPath();
            String entireProfileString = Files.readString(filePath);

            if (entireProfileString.equals("NEW")){
                this.profileNotInstantiated = true;
                return;
            }

            String[] dataArr = entireProfileString.split(";");
            this.levelNumber = dataArr[0];
            this.score = dataArr[3];
            this.timeRemaining = dataArr[4];
            this.diamondCount = dataArr[6];


        }
        catch (IOException error){
            System.err.println(error);
        }
    }

    /**
     * Returns a boolean stating whether the profile exists or not.
     * @return
     * The true of false value for if the profile exists.
     */
    public boolean getProfileNotInstantiated(){
        return this.profileNotInstantiated;
    }

    /**
     * Returns the level number the profile is on.
     * @return
     * The level number the profile is on.
     */
    public String getLevelNumber() {
        return levelNumber;
    }

    /**
     * Returns the diamond count of the profile has on the current level they're on.
     * @return
     * The diamond count of the current level the profile is on.
     */
    public String getDiamondCount() {
        return diamondCount;
    }

    /**
     * Returns the profiles current score.
     * @return
     * The profiles current score.
     */
    public String getScore() {
        return score;
    }

    /**
     * Returns the time remaining the profile has to complete their current level.
     * @return
     * The time remaining the profile has to complete their current level.
     */
    public String getTimeRemaining() {
        return timeRemaining;
    }
}
