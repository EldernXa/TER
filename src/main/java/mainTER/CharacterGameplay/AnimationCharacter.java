package mainTER.CharacterGameplay;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public class AnimationCharacter {

    private final ArrayList<ArrayList<ImageView>> listOfImageViewForTheAnimation;
    private final Timeline timeline = new Timeline();
    private int indImgToAnimate;
    private int posToAnimate = 0;
    private boolean walkToRight = false;

    public AnimationCharacter(Character characterToAnimate){
        listOfImageViewForTheAnimation = characterToAnimate.getListOfPictureOfTheCharacter();
    }

    public ImageView nextImage(){
        int ind = (indImgToAnimate)%listOfImageViewForTheAnimation.get(posToAnimate).size();
        indImgToAnimate++;
        return listOfImageViewForTheAnimation.get(posToAnimate).get(ind);
    }

    public Timeline getTimeline(){
        return timeline;
    }

}
