import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
public class LeaderBoards {
    private final String filepath = "./LeaderBoards/LeaderBoard.txt";
    private ArrayList<String> leaderBoardDisplay;

    /**
     * Instantiates a leaderboard object which can be used to add new names/scores to the leaderboard and
     * get a display of the entire leaderboard.
     */
    public LeaderBoards() {
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

            String[] leaderBoardArray = fileContent.split("\n");
            for(String entry :leaderBoardArray){
                leaderBoardDisplay.add(entry);
            }
        }
    }


    public void writeNewNameToLeaderboard(String name, int score){

        String newLeaderBoardEntry = name + ": " + Integer.toString(score);

        //sort the leaderboard entry into the leaderboards at the right place
        for(int i = 0; i < leaderBoardDisplay.size(); i++){
            int leaderboardScore = Integer.parseInt(leaderBoardDisplay.get(i).split(" ")[1]);

            if (score >= leaderboardScore) {
                leaderBoardDisplay.add(i, newLeaderBoardEntry);
            } else if (i == leaderBoardDisplay.size() - 1) {
                leaderBoardDisplay.add(newLeaderBoardEntry);
            }
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
        for(int i = 0; i < leaderBoardDisplay.size(); i++){
            String leaderBoardEntry = leaderBoardDisplay.get(i) + "\n";
            newLeaderboards += leaderBoardEntry;
        }

        try{
            FileWriter fileWriter = new FileWriter(this.filepath);
            fileWriter.write(newLeaderboards);
            fileWriter.close();
        }
        catch (Exception error){
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

        for(String entry: leaderBoardDisplay){
            leaderBoard.append(entry + "\n");
        }
        return leaderBoard.toString();
    }

    public ArrayList<String> getLeaderBoardArray() {
        return this.leaderBoardDisplay;
    }
}
