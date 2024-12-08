import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class MusicPlayer {

    private MediaPlayer mediaPlayer;

    public MusicPlayer(String musicFilePath) {
        try {
            // Load the music file
            Media media = new Media(new File(musicFilePath).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            // Set the music to loop
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        } catch (Exception e) {
            System.out.println("Error loading music file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Start playing music
    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
            System.out.println("Playing music...");
        }
    }

    // Stop the music
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            System.out.println("Music stopped.");
        }
    }
}

