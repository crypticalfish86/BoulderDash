import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;


public class Game {
    private String loadedPlayerProfileID;
    private GameSession currentGamesession;


    //currentGameSession and loadedPlayerProfileID do not get instantiated in the constructor as they are variable (debatable whether anything gets instantiated in the constructor
    Game(Scene scene, Pane pane) {

        //TODO: add menu buttons here


        //TODO: implement profile loading


        //TODO: Shows the profiles that this player has


        //TODO: remove this part that loads the game
        FlowPane formatterPane = new FlowPane();

        TextField gameDirectoryInputField = new TextField();
        Button startGameButton = new Button("Load Game");

        formatterPane.getChildren().addAll(gameDirectoryInputField, startGameButton);


        startGameButton.setOnAction(E -> {


            boolean success = loadGame(gameDirectoryInputField.getText());

            if (success) {
                formatterPane.setVisible(false);
            };
        });










        pane.getChildren().add(formatterPane);
    }


    /**
     * @param filePath file
     * @return if the file exists, and the game loads
    */
    private boolean loadGame(String filePath) {
        //locate the file
        String gameData;

        try {

            //load file content into the gamedata string
            Scanner s = new Scanner(new File(filePath)).useDelimiter("\\Z");
            gameData = s.next();
            s.close();

        } catch (FileNotFoundException e) {

            System.err.println("File " + filePath + " not found.");
            return false;
        } catch (Exception e) {
            
            System.err.println("Unknown Error.");
            return false;
        }








        this.currentGamesession = new GameSession(this, gameData.toString());
        return true;
    }










    public void setPlayerProfile(String playerProfileID) {
        this.loadedPlayerProfileID = playerProfileID;
    }

    public void loadGame(File levelFileData) {
        currentGamesession = new GameSession(this, levelFileData.toString());//This is not how we will pass the level file data, it is just a placeholder for now
    }

    
}
