import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;


public class Game {
    private String loadedPlayerProfileID;
    private GameSession currentGamesession;

    
    


    private CanvasCompositor cc;
    private MainMenu mainMenu;

    //currentGameSession and loadedPlayerProfileID do not get instantiated in the constructor as they are variable (debatable whether anything gets instantiated in the constructor
    public Game(CanvasCompositor cc) {





        this.mainMenu = new MainMenu(this, cc);



        this.cc = cc;



        // Hides the menu
        // mainMenu.hide();



        // //TODO: add menu buttons here
        Image playImg = new Image ("file:Assets/Buttons/PlayButton.png");
        Button playButton = new Button("Play");
        playButton.setMaxWidth(Double.MAX_VALUE);

        // //TODO: implement profile loading


        // //TODO: Shows the profiles that this player has


        // //TODO: remove this part that loads the game
        // FlowPane formatterPane = new FlowPane();

        // TextField gameDirectoryInputField = new TextField();
        // Button startGameButton = new Button("Load Game");

        // formatterPane.getChildren().addAll(gameDirectoryInputField, startGameButton);


        // startGameButton.setOnAction(E -> {


        //     boolean success = loadGame(gameDirectoryInputField.getText());

        //     if (success) {
        //         formatterPane.setVisible(false);
        //     };
        // });


        // scene.setOnKeyPressed(E -> {
        //     String key = E.getCharacter();


        //     if (currentGamesession != null) {
        //         currentGamesession.onKeyPressed(key);
        //     }
        // });

        // scene.setOnKeyReleased(E -> {
        //     String key = E.getCharacter();


        //     if (currentGamesession != null) {
        //         currentGamesession.onKeyReleased(key);
        //     }
        // });


        // this.gamePane = pane;




        // pane.getChildren().add(formatterPane);
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








        // this.currentGamesession = new GameSession(this, gameData, gamePane);
        return true;
    }










    public void setPlayerProfile(String playerProfileID) {
        this.loadedPlayerProfileID = playerProfileID;
    }

    
}
