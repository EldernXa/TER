package mainTER.Music;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Music {

    String path;
    public static MediaPlayer mediaPlayer;
    static boolean  playing = false;


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
    public static void launchMenuSound(String name)
    {

        new Thread(()->{
            stopMusic();
            try {
                Media media = new Media(new File("src/main/resources/mainTER/Sound/music"+name+".wav").toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                if(!playing) {
                    playing = true;
                    mediaPlayer.play();
                    mediaPlayer.setVolume(0.2);
                    mediaPlayer.setOnEndOfMedia(() -> {
                        mediaPlayer.stop();
                        mediaPlayer.play();

                    });
                }
            }catch (Exception e){

            }
        }).start();


    }

    /**
     * allows to switch of media
     * @param nomMedia the new media played
     */
    public void modifMusic(Media nomMedia){
        mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(nomMedia);
    }

    /**
     * allows to stop the music
     */
    public static void stopMusic(){
        if(playing){
            mediaPlayer.stop();
            playing=false;
            mediaPlayer.setMute(false);
        }else {
            System.out.println("aucune musique n'est lancée");
        }

    }

    /**
     *
     * @param mute value yes/no il we want to mute or demute
     */
    public void mute(boolean mute){
        mediaPlayer.setMute(mute);
    }
}
