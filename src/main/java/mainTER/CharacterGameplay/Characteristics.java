package mainTER.CharacterGameplay;

public class Characteristics {

    private final double speed;
    private final double weight;
    private final double jumpStrength;
    private final double fallingSpeed;
    private boolean canJump;

    public Characteristics(double speed, double weight, double jumpStrength, double fallingSpeed, boolean canJump){
        this.speed = speed;
        this.weight = weight;
        this.jumpStrength = jumpStrength;
        this.fallingSpeed = fallingSpeed;
        this.canJump = canJump;
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
