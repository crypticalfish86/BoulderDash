import java.io.File;

public class Game {
    private String loadedPlayerProfileID;
    private GameSession currentGamesession;


    //currentGameSession and loadedPlayerProfileID do not get instantiated in the constructor as they are variable (debatable whether anything gets instantiated in the constructor
    Game() {
    }

    public void setPlayerProfile(String playerProfileID) {
        this.loadedPlayerProfileID = playerProfileID;
    }

    public void loadGame(File levelFileData) {
        currentGamesession = new GameSession(this, levelFileData.toString());//This is not how we will pass the level file data, it is just a placeholder for now
    }
}
