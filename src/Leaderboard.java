import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
public class Leaderboard {
    private final String filepath = "./LeaderBoards/leaderboard.txt";
    private ArrayList<String> leaderBoardDisplay;

    /**
     * Instantiates a leaderboard object which can be used to add new names/scores to the leaderboard and
     * get a display of the entire leaderboard.
     */
    public Leaderboard() {
        leaderBoardDisplay = new ArrayList<String>();

        File file = new File(filepath);

        Scanner scanner = null;
        try{
            scanner = new Scanner(file);
        } catch (Exception error){
            System.err.println(error);
        }

        scanner.useDelimiter("\\A"); // Read entire file as one string
        String fileContent = null;
        if(scanner.hasNext()){
            fileContent = scanner.next();
        }
        scanner.close();

        if(fileContent != null){

            String[] leaderBoardArray = fileContent.split(System.lineSeparator());
            for(String entry :leaderBoardArray){
                leaderBoardDisplay.add(entry);
            }
        }
    }


    public void writeNewNameToLeaderboard(String name, int newScore) {

        String newLeaderBoardEntry = name + ": " + Integer.toString(newScore);

        //sort the leaderboard entry into the leaderboards at the right place
        
        boolean hasAdded = false;
        for(int i = leaderBoardDisplay.size() - 1; i >= 0; i++){
            
            int leaderboardScore = Integer.parseInt(leaderBoardDisplay.get(i).split(" ")[1]);

            System.out.printf("score: current: %d, list: %d\n", newScore, leaderboardScore);
            if (newScore <= leaderboardScore) {
                leaderBoardDisplay.add(i, newLeaderBoardEntry);
                hasAdded = true;
                break;
            }
        }

        if (!hasAdded) {
            leaderBoardDisplay.add(newLeaderBoardEntry);
        }

        writeLeaderBoardArrayToFile();
    }

    public void deleteNameFromLeaderboard(int entryLineNumber) {
        this.leaderBoardDisplay.remove(entryLineNumber);
        writeLeaderBoardArrayToFile();
    }

    /**
     * Rewrite the leaderBoardArray to the file.
     */
    private void writeLeaderBoardArrayToFile() {
        String newLeaderboards = "";
        for (int i = 0; i < leaderBoardDisplay.size(); i++) {
            String leaderBoardEntry = leaderBoardDisplay.get(i) + System.lineSeparator();
            newLeaderboards += leaderBoardEntry;
        }

        try {
            FileWriter fileWriter = new FileWriter(this.filepath);
            fileWriter.write(newLeaderboards);
            fileWriter.close();
        }
        catch (Exception error) {
            System.err.println(error);
        }
    }

    /**
     * Get a string representation of the entire leaderboard
     * @return
     * A string representation of the entire leaderboard
     */
    public String getLeaderBoardDisplay() {
        StringBuilder leaderBoard = new StringBuilder();

        int linesToPrint = leaderBoardDisplay.size();

        if (leaderBoardDisplay.size() > 10) {
            linesToPrint = 9;
        }
        for(int i = 0; i <= linesToPrint; i++){
            leaderBoard.append(leaderBoardDisplay.get(i) + System.lineSeparator());
        }
        return leaderBoard.toString();
    }

    public ArrayList<String> getLeaderBoardArray() {
        return this.leaderBoardDisplay;
    }
}
