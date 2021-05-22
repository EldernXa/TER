package mainTER.CharacterGameplay;

import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Characteristics {

    private double speed;
    private final double initSpeed;
    private final double weight;
    private final double jumpStrength;
    private final double fallingSpeed;
    private boolean canJump;
    private final ArrayList<ArrayList<ImageView>> listOfPictureOfTheCharacter;

    /**
     * Constructor to create characteristics for a character.
     * @param speed the speed of the character.
     * @param weight the weight of the character.
     * @param jumpStrength the jump strength of the character.
     * @param fallingSpeed the falling speed of the character.
     * @param canJump if the character can jump or not.
     */
    public Characteristics(double speed, double weight, double jumpStrength, double fallingSpeed, boolean canJump){
        listOfPictureOfTheCharacter = new ArrayList<>();
        this.speed = speed;
        this.initSpeed = speed;
        this.weight = weight;
        this.jumpStrength = jumpStrength;
        this.fallingSpeed = fallingSpeed;
        this.canJump = canJump;
    }

    /**
     * get a list containing image for the animation purpose.
     * @return a list containing image for the animation purpose.
     */
    protected ArrayList<ArrayList<ImageView>> getListOfPictureOfTheCharacter(){
        return listOfPictureOfTheCharacter;
    }

    /**
     * get speed of the character.
     * @return speed of the character.
     */
    public double getSpeed(){
        return speed;
    }

    /**
     * get weight of the character.
     * @return weight of the character.
     */
    public double getWeight(){
        return weight;
    }

    /**
     * get jump strength of the character.
     * @return jump strength of the character.
     */
    public double getJumpStrength(){
        return jumpStrength;
    }

    /**
     * get falling speed of the character.
     * @return falling speed of the character.
     */
    public double getFallingSpeed(){
        return fallingSpeed;
    }

    /**
     * get if the character can jump or not.
     * @return if the character can jump or not.
     */
    public boolean canJump(){
        return canJump;
    }

    /**
     * set if the character can jump or not.
     * @param canJump if the character can jump or not.
     */
    public void setCanJump(boolean canJump){
        this.canJump = canJump;
    }

    /**
     * set new speed.
     * @param speed the new values of the speed for the character.
     */
    public void setSpeed(double speed){
        this.speed = speed;
    }

    /**
     * reset the initial speed.
     */
    public void resetSpeed(){
        speed = initSpeed;
    }

    /**
     * get the greatest height of motionless animation.
     * @return the greatest height of motionless animation.
     */
    public double getHeightMotionless(){
        double returnValue = 0.0;
        for(ImageView imgView : listOfPictureOfTheCharacter.get(Position.MOTIONLESS.ordinal())){
            if(imgView.getImage().getHeight()>= returnValue)
                returnValue = imgView.getImage().getHeight();
        }
        return returnValue;
    }

    /**
     * get the greatest height of a position.
     * @param position the position we want the greatest height.
     * @return the greatest height of a position.
     */
    public double getBestHeightOfAPosition(Position position){
        double returnValue = 0.0;
        for(ImageView imgView : listOfPictureOfTheCharacter.get(position.ordinal())){
            if(imgView.getImage().getHeight()>=returnValue)
                returnValue = imgView.getImage().getHeight();
        }

        return returnValue;
    }

}
