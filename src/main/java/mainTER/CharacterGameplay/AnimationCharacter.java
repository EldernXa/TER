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
    private boolean canMove;

    public AnimationCharacter(Character characterToAnimate){
        canMove = true;
        setMotionless();
        listOfImageViewForTheAnimation = characterToAnimate.getListOfPictureOfTheCharacter();
    }

    public int getIndImgToAnimate() {
        return indImgToAnimate;
    }

    public void changeCharacter(Character character){
        listOfImageViewForTheAnimation = character.getListOfPictureOfTheCharacter();
        indImgToAnimate = 0;
    }

    public void setCanMove(boolean canMove){
        this.canMove = canMove;
    }

    public boolean getCanMove(){
        return canMove;
    }

    public double getHeightMotionless(){
        return listOfImageViewForTheAnimation.get(Position.MOTIONLESS.ordinal()).get(0).getImage().getHeight();
    }

    public Position getCurrentPosition(){
        return Position.values()[posToAnimate];
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
        ImageView imgView = listOfImageViewForTheAnimation.get(posToAnimate).get(ind);
        if(ind == listOfImageViewForTheAnimation.get(posToAnimate).size()-1){
            if(posToAnimate == Position.JUMP.ordinal()){
                setMotionless();
            }else if(posToAnimate == Position.REVERSE_JUMP.ordinal()){
                setReverseMotionLess();
            }
        }
        indImgToAnimate++;
        return imgView;
    }

    public ImageView actualImg(){
        return listOfImageViewForTheAnimation.get(posToAnimate).get((indImgToAnimate-1)%listOfImageViewForTheAnimation.get(posToAnimate).size());
    }

    public Timeline getTimeline(){
        return timeline;
    }

}
