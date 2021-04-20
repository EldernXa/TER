package mainTER.Music;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Music {

    String path;
    public static MediaPlayer mediaPlayer;
    boolean playing = false;


    public Music(String path) {
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        this.path = path;
    }

    public String getPath() {
        return path;
    }


    /**
     * allows to play the music on a loop
     */
    public void lancerMusique()
    {
        if(!playing) {
            mediaPlayer.play();
            mediaPlayer.setVolume(0.2);
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
                mediaPlayer.play();
            });
        }
    }

    /**
     * allows to switch of media
     * @param nomMedia the new media played
     */
    public void modifMusique(Media nomMedia){
        mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(nomMedia);
    }

    /**
     * allows to stop the music
     */
    public void stopMusique(){
        mediaPlayer.stop();
        playing=false;
        mediaPlayer.setMute(false);
    }

    /**
     *
     * @param mute value yes/no il we want to mute or demute
     */
    public void mute(boolean mute){
        mediaPlayer.setMute(mute);
    }
}
