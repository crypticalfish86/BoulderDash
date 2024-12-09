import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

/**
 * The Leaderboards class is the back end for the writing, and display of the leaderboards,
 * which is a .txt document at: root/LeaderBoards/leaderboard.txt.
 * @author
 * Jace Weerawardena (Crypticalfish86).
 * @version 1.0
 */
public class Leaderboard {
    private final String filepath = "./LeaderBoards/leaderboard%s.txt";
    private ArrayList<String> leaderBoardDisplay = new ArrayList<>();
    private final String leaderboardName;

    /**
     * Instantiates a leaderboard object which can be used to add new names/scores to the
     * leaderboard and get a display of the entire leaderboard.
     */
    public Leaderboard(String leaderboardName) {
        this.leaderboardName = leaderboardName;

        File file = new File(String.format(filepath, leaderboardName));

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);

            scanner.useDelimiter("\\A"); // Read entire file as one string
            String fileContent = null;
            if (scanner.hasNext()) {
                fileContent = scanner.next();
            }
            scanner.close();

            if (fileContent != null) {

                String[] leaderBoardArray = fileContent.split(System.lineSeparator());
                for (String entry :leaderBoardArray) {
                    leaderBoardDisplay.add(entry);
                }
            }
        } catch (FileNotFoundException e) {
            // System.err.println(error);
            
        }

        
    }


    /**
     * Writes a new entry to the leaderboards.
     * @param name
     * The name of the person adding the entry.
     * @param newScore
     * The score that is to be posted to that entry in the leaderboards.
     */
    public void writeNewNameToLeaderboard(String name, int newScore) {

        String newLeaderBoardEntry = name + ": " + Integer.toString(newScore);

        boolean hasAdded = false;
        for (int i = 0; i < leaderBoardDisplay.size(); ++i) {
            

            int leaderboardScore;
             
            try {
                leaderboardScore = Integer.parseInt(leaderBoardDisplay.get(i).split(" ")[1]);
            } catch (NumberFormatException e) {
                leaderboardScore = 0;
            }

            System.out.printf("score: current: %d, list: %d\n", newScore, leaderboardScore);
            if (newScore > leaderboardScore) {
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

    /**
     * Delete an entry by line number in the leaderboard.txt.
     * @param entryLineNumber
     * The line number you are deleting.
     */
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
            FileWriter fileWriter = new FileWriter(String.format(filepath, leaderboardName));
            fileWriter.write(newLeaderboards);
            fileWriter.close();
        }
        catch (Exception error) {
            System.err.println(error);
        }
    }

    /**
     * Get a string representation of the entire leaderboard.
     * @return
     * A string representation of the entire leaderboard.
     */
    public String getLeaderBoardDisplay() {
        StringBuilder leaderBoard = new StringBuilder();

        int linesToPrint = leaderBoardDisplay.size();

        if (leaderBoardDisplay.size() > 10) {
            linesToPrint = 10;
        }
        for(int i = 0; i < linesToPrint; i++){
            leaderBoard.append(leaderBoardDisplay.get(i) + System.lineSeparator());
        }
        return leaderBoard.toString();
    }

    /**
     * Get an ArrayList representation of the leaderboards.
     * @return
     * The ArrayList of the leaderboards.
     */
    public ArrayList<String> getLeaderBoardArray() {
        return this.leaderBoardDisplay;
    }
}
