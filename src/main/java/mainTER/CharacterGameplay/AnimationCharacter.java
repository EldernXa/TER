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

    /**
     * Constructor to manage animation.
     * @param characterToAnimate the character we want to animate.
     */
    public AnimationCharacter(Character characterToAnimate){
        canMove = true;
        canMotionless = true;
        setMotionless();
        listOfImageViewForTheAnimation = characterToAnimate.getListOfPictureOfTheCharacter();
    }

    /**
     *
     * @return true if the character can do the motionless animation.
     */
    public boolean canMotionless(){
        return canMotionless;
    }

    /**
     * set if we can animate the motionless animation for a character.
     * @param canMotionless if we can animate the motionless animation for a character.
     */
    public void setCanMotionLess(boolean canMotionless){
        this.canMotionless = canMotionless;
    }

    /**
     *
     * @return get the last index of the animation.
     */
    public int getIndImgToAnimate() {
        return (indImgToAnimate)%listOfImageViewForTheAnimation.get(posToAnimate).size();
    }

    /**
     * set the last index of the animation.
     * @param indImgToAnimate the new index of the animation.
     */
    public void setIndImgToAnimate(int indImgToAnimate){
        this.indImgToAnimate = indImgToAnimate;
    }

    /**
     * change character to animate.
     * @param character new character we want to animate.
     */
    public void changeCharacter(Character character){
        listOfImageViewForTheAnimation = character.getListOfPictureOfTheCharacter();
        indImgToAnimate = 0;
    }

    /**
     * set if the character can move or not.
     * @param canMove if the character can move or not.
     */
    public void setCanMove(boolean canMove){
        this.canMove = canMove;
    }

    /**
     *
     * @return if the character can move or not.
     */
    public boolean getCanMove(){
        return canMove;
    }

    /**
     *
     * @return the height of the first image of the motionless.
     */
    public double getHeightMotionless(){
        return listOfImageViewForTheAnimation.get(Position.MOTIONLESS.ordinal()).get(0).getImage().getHeight();
    }

    /**
     *
     * @return the width of the first image of the motionless.
     */
    public double getWidthMotionless(){
        return listOfImageViewForTheAnimation.get(Position.MOTIONLESS.ordinal()).get(0).getImage().getWidth();
    }

    /**
     *
     * @return Current position of the animation.
     */
    public Position getCurrentPosition(){
        return Position.values()[posToAnimate];
    }

    /**
     * Set the animation to walk position.
     */
    public void setWalk(){
        posToAnimate = Position.WALK.ordinal();
    }

    /**
     * Set the animation to reverse walk position.
     */
    public void setReverseWalk(){
        posToAnimate = Position.REVERSE_WALK.ordinal();
    }

    /**
     * Set the animation to motionless position.
     */
    public void setMotionless(){
        posToAnimate = Position.MOTIONLESS.ordinal();
    }

    /**
     * Set the animation to reverse motionless position.
     */
    public void setReverseMotionLess(){
        posToAnimate = Position.REVERSE_MOTIONLESS.ordinal();
    }

    /**
     * Set the animation to jump position.
     */
    public void setJump(){
        posToAnimate = Position.JUMP.ordinal();
    }

    /**
     * Set the animation to reverse jump position.
     */
    public void setReverseJump(){
        posToAnimate = Position.REVERSE_JUMP.ordinal();
    }

    /**
     *
     * @return the next image for the animation purpose.
     */
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

    /**
     *
     * @return next image for the animation purpose but without jump case.
     */
    public  ImageView testnextImg(){
        int ind;
        try {
            ind = (indImgToAnimate) % listOfImageViewForTheAnimation.get(posToAnimate).size();
        }catch(ArithmeticException arithmeticException){
            ind = 0;
        }
        return listOfImageViewForTheAnimation.get(posToAnimate).get(ind);
    }

    /**
     *
     * @return current image.
     */
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

    /**
     *
     * @return timeline for animation purpose.
     */
    public Timeline getTimeline(){
        return timeline;
    }

}
