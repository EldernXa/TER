package mainTER.CharacterGameplay;

import javafx.animation.Timeline;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class AnimationCharacter {

    private List<ArrayList<ImageView>> listOfImageViewForTheAnimation;
    private final Timeline timeline = new Timeline();
    private int indImgToAnimate = 0;
    private int posToAnimate;

    public AnimationCharacter(Character characterToAnimate){
        setMotionless();
        listOfImageViewForTheAnimation = characterToAnimate.getListOfPictureOfTheCharacter();
    }

    public void changeCharacter(Character character){
        listOfImageViewForTheAnimation = character.getListOfPictureOfTheCharacter();
        indImgToAnimate = 0;
    }

    public double getHeightMotionless(){
        return listOfImageViewForTheAnimation.get(Position.MOTIONLESS.ordinal()).get(0).getImage().getHeight();
    }

    public void setWalk(){
        posToAnimate = Position.WALK.ordinal();
    }

    public void setReverseWalk(){
        posToAnimate = Position.REVERSE_WALK.ordinal();
    }

    public void setMotionless(){
        posToAnimate = Position.MOTIONLESS.ordinal();
    }

    public void setReverseMotionLess(){
        posToAnimate = Position.REVERSE_MOTIONLESS.ordinal();
    }

    public void setJump(){
        posToAnimate = Position.JUMP.ordinal();
    }

    public void setReverseJump(){
        posToAnimate = Position.REVERSE_JUMP.ordinal();
    }

    public ImageView nextImage(){
        int ind = (indImgToAnimate)%listOfImageViewForTheAnimation.get(posToAnimate).size();
        indImgToAnimate++;
        return listOfImageViewForTheAnimation.get(posToAnimate).get(ind);
    }

    public ImageView actualImg(){
        return listOfImageViewForTheAnimation.get(posToAnimate).get((indImgToAnimate-1)%listOfImageViewForTheAnimation.get(posToAnimate).size());
    }

    public Timeline getTimeline(){
        return timeline;
    }

}
