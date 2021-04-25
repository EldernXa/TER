package mainTER.CharacterGameplay;

import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Characteristics {

    private final double speed;
    private final double weight;
    private final double jumpStrength;
    private final double fallingSpeed;
    private boolean canJump;
    private final ArrayList<ArrayList<ImageView>> listOfPictureOfTheCharacter;

    public Characteristics(double speed, double weight, double jumpStrength, double fallingSpeed, boolean canJump){
        listOfPictureOfTheCharacter = new ArrayList<>();
        this.speed = speed;
        this.weight = weight;
        this.jumpStrength = jumpStrength;
        this.fallingSpeed = fallingSpeed;
        this.canJump = canJump;
    }

    protected ArrayList<ArrayList<ImageView>> getListOfPictureOfTheCharacter(){
        return listOfPictureOfTheCharacter;
    }

    public double getSpeed(){
        return speed;
    }

    public double getWeight(){
        return weight;
    }

    public double getJumpStrength(){
        return jumpStrength;
    }

    public boolean canJump(){
        return canJump;
    }

    public void setCanJump(boolean canJump){
        this.canJump = canJump;
    }

}
