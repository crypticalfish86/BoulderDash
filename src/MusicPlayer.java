import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/**
 * MusicPlayer is a class that uses JavaFX to play music during levels.
 * It supports the looping of music.
 *
 * Author: Cameron McDonald (cmcoff)
 * Version: 2.0
 */

public class MusicPlayer {

    // Handles the music playback
    private MediaPlayer mediaPlayer;

    /**
     * Creates a MusicPlayer object that plays a music file.
     *
     * @param musicFilePath The file path of the music file to play.
     */
    public MusicPlayer(String musicFilePath) {
        try {
            // Load the music file and set up the MediaPlayer
            Media media = new Media(new File(musicFilePath).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            // Loop the music forever
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        } catch (Exception e) {
            System.out.println("Error: Couldn't load the music file. Details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Starts playing the music.
     */
    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
            System.out.println("Music is playing...");
        } else {
            System.out.println("No music loaded to play.");
        }
    }

    /**
     * Stops the music playback.
     */
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
