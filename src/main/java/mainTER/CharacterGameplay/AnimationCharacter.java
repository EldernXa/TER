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
    private boolean canMotionless;

    public AnimationCharacter(Character characterToAnimate){
        canMove = true;
        canMotionless = true;
        setMotionless();
        listOfImageViewForTheAnimation = characterToAnimate.getListOfPictureOfTheCharacter();
    }

    public boolean canMotionless(){
        return canMotionless;
    }

    public void setCanMotionLess(boolean canMotionless){
        this.canMotionless = canMotionless;
    }

    public int getIndImgToAnimate() {
        return (indImgToAnimate)%listOfImageViewForTheAnimation.get(posToAnimate).size();
    }

    public void setIndImgToAnimate(int indImgToAnimate){
        this.indImgToAnimate = indImgToAnimate;
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
        int ind;
        try {
            ind = (indImgToAnimate) % listOfImageViewForTheAnimation.get(posToAnimate).size();
        }catch(ArithmeticException arithmeticException){
            ind = 0;
        }
        ImageView imgView = listOfImageViewForTheAnimation.get(posToAnimate).get(ind);
        if(ind == listOfImageViewForTheAnimation.get(posToAnimate).size()-1){
            if(posToAnimate == Position.JUMP.ordinal()){
                setMotionless();
            }else if(posToAnimate == Position.REVERSE_JUMP.ordinal()){
                setReverseMotionLess();
            }
        }
        indImgToAnimate = (indImgToAnimate+1) % listOfImageViewForTheAnimation.get(posToAnimate).size();
        return imgView;
    }

    public ImageView actualImg(){
        try {
            int indice = (indImgToAnimate - 1) % listOfImageViewForTheAnimation.get(posToAnimate).size();
            if(indice==-1)
                indice = 0;
            return listOfImageViewForTheAnimation.get(posToAnimate).get(indice);
        }catch(ArithmeticException arithmeticException){
            return listOfImageViewForTheAnimation.get(posToAnimate).get((indImgToAnimate));
        }
    }

    public Timeline getTimeline(){
        return timeline;
    }

}
