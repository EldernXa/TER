package mainTER.CharacterGameplay;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import mainTER.MapPackage.Collide;
import mainTER.MapPackage.CollideObject;
import mainTER.Tools.Coordinate;

import java.util.List;


/**
 * Class For the display of one character (with animation).
 */
public class DisplayCharacter extends CollideObject {

    private final Coordinate currentCoordinateOfTheCharacter;
    private final AnimationCharacter animationForTheCharacter;
    private Character character;
    private final Scene lvlOfTheGame;
    private final Pane pane;
    private boolean walkToRight = true;
    private final Collide collide;
    private KeyCode currentKeyCode;
    private double fallingStep = 1;
    private boolean isJumping = false;
    private int jumpStrength;
    private static final int TPS_DURATION_TIMELINE = 100;

    /**
     *
     * @param scene scene of the game.
     * @param pane is the level of the game.
     * @param character is the character we will display.
     */
    public DisplayCharacter(Scene scene, Pane pane, Character character, Collide collide){
        this.lvlOfTheGame = scene;
        this.pane = pane;
        this.character = character;
        this.collide = collide;
        currentCoordinateOfTheCharacter = new Coordinate(character.getInitialCoordinate().getX(), character.getInitialCoordinate().getY());
        animationForTheCharacter = new AnimationCharacter(character);
        ImageView initImgView = animationForTheCharacter.nextImage();
        initImgView.setX(currentCoordinateOfTheCharacter.getX());
        initImgView.setY(currentCoordinateOfTheCharacter.getY());
        pane.getChildren().add(initImgView);
        enableEvent();
        timelineForMotionlessCharacter();

    }

    public void setCharacter(Character characterToSwitch){
        animationForTheCharacter.getTimeline().stop();
        removeAllImgViewOfThePane();
        animationForTheCharacter.changeCharacter(characterToSwitch);
        currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX());
        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY()-50);
        ImageView imgView = animationForTheCharacter.nextImage();
        imgView.setX(currentCoordinateOfTheCharacter.getX());
        imgView.setY(currentCoordinateOfTheCharacter.getY()-5);
        pane.getChildren().add(imgView);
        this.character = characterToSwitch;
    }

    private void timelineForWalk(){
        animationForTheCharacter.getTimeline().stop();
        animationForTheCharacter.getTimeline().getKeyFrames().clear();
        animationForTheCharacter.getTimeline().getKeyFrames().add(new KeyFrame(
                Duration.millis(TPS_DURATION_TIMELINE),
                tps->{
                    if(isJumping && verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY()-1)){
                        if(verifyCollision(currentCoordinateOfTheCharacter.getX()+character.getSpeed(), currentCoordinateOfTheCharacter.getY())){
                            double height = animationForTheCharacter.actualImg().getImage().getHeight();
                            animationForTheCharacter.setWalk();
                            currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX()+character.getSpeed());
                            doJump(height);
                        }
                    }
                    else if(verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY()+1)){
                        if(verifyCollision(currentCoordinateOfTheCharacter.getX()+character.getSpeed(), currentCoordinateOfTheCharacter.getY())){
                            animationForTheCharacter.setWalk();
                            currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX()+character.getSpeed());
                            fallingCharacter();
                        }
                    }
                    else if(verifyCollision(currentCoordinateOfTheCharacter.getX()+character.getSpeed(), currentCoordinateOfTheCharacter.getY())){
                        fallingStep = 1;
                        removeAllImgViewOfThePane();
                        double height = animationForTheCharacter.actualImg().getImage().getHeight();
                        animationForTheCharacter.setWalk();
                        currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX()+character.getSpeed());
                        adapYToHeight(height);
                    }else{
                        timelineForMotionlessCharacter();
                    }
                }
        ));

        animationForTheCharacter.getTimeline().setCycleCount(Animation.INDEFINITE);
        animationForTheCharacter.getTimeline().play();
    }

    private double adapYToHeight(double height) {
        ImageView imgView = animationForTheCharacter.nextImage();
        double newHeight = height-imgView.getImage().getHeight();
        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY()+newHeight);
        imgView.setX(currentCoordinateOfTheCharacter.getX());
        imgView.setY(currentCoordinateOfTheCharacter.getY());
        pane.getChildren().add(imgView);
        return newHeight;
    }

    private void doJump(double height) {
        removeAllImgViewOfThePane();
        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY()-jumpStrength);
        jumpStrength-= character.getWeight();
        if(jumpStrength<=0)
            isJumping = false;
        ImageView imgView = animationForTheCharacter.nextImage();
        double newHeight = height-imgView.getImage().getHeight();
        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY()-newHeight);
        imgView.setX(currentCoordinateOfTheCharacter.getX());
        imgView.setY(currentCoordinateOfTheCharacter.getY());
        pane.getChildren().add(imgView);
    }

    private void timelineForReverseWalk(){
        animationForTheCharacter.getTimeline().stop();
        animationForTheCharacter.getTimeline().getKeyFrames().clear();
        animationForTheCharacter.getTimeline().getKeyFrames().add(new KeyFrame(
                Duration.millis(TPS_DURATION_TIMELINE),
                tps->{
                    if(isJumping && verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY()-1)){
                        if(verifyCollision(currentCoordinateOfTheCharacter.getX()-character.getSpeed(), currentCoordinateOfTheCharacter.getY())){
                            double height = animationForTheCharacter.actualImg().getImage().getHeight();
                            animationForTheCharacter.setReverseWalk();
                            currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX()-character.getSpeed());
                            doJump(height);
                        }
                    }
                    else if(verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY()+1)){
                        if(verifyCollision(currentCoordinateOfTheCharacter.getX()-character.getSpeed(), currentCoordinateOfTheCharacter.getY())){
                            animationForTheCharacter.setReverseWalk();
                            currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX()-character.getSpeed());
                            fallingCharacter();
                        }
                    }
                    else if(verifyCollision(currentCoordinateOfTheCharacter.getX()-character.getSpeed(), currentCoordinateOfTheCharacter.getY())){
                        fallingStep = 1;
                        removeAllImgViewOfThePane();
                        double height = animationForTheCharacter.actualImg().getImage().getHeight();
                        animationForTheCharacter.setReverseWalk();
                        currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX()-character.getSpeed());

                        adapYToHeight(height);
                    }
                    else{
                        timelineForMotionlessCharacter();
                    }
                }
        ));
        animationForTheCharacter.getTimeline().setCycleCount(Animation.INDEFINITE);
        animationForTheCharacter.getTimeline().play();
    }

    private void timelineForMotionlessCharacter(){
        animationForTheCharacter.getTimeline().stop();
        animationForTheCharacter.getTimeline().getKeyFrames().clear();
        animationForTheCharacter.getTimeline().getKeyFrames().add(new KeyFrame(
                Duration.millis(TPS_DURATION_TIMELINE),
                tps->{
                    removeAllImgViewOfThePane();
                    double height = animationForTheCharacter.actualImg().getImage().getHeight();
                    if(walkToRight)
                        animationForTheCharacter.setMotionless();
                    else
                        animationForTheCharacter.setReverseMotionLess();
                    double newHeight = adapYToHeight(height);
                    if(isJumping && verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY()-1)){
                        if(walkToRight)
                            animationForTheCharacter.setJump();
                        else
                            animationForTheCharacter.setReverseJump();
                        removeAllImgViewOfThePane();
                        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY()-jumpStrength-newHeight);
                        jumpStrength-= character.getWeight();
                        if(jumpStrength<=0)
                            isJumping = false;
                        ImageView imgView = animationForTheCharacter.nextImage();
                        imgView.setX(currentCoordinateOfTheCharacter.getX());
                        imgView.setY(currentCoordinateOfTheCharacter.getY());
                        pane.getChildren().add(imgView);
                    }
                    else if(verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY()+1)){
                        fallingCharacter();
                    }
                    else{
                        fallingStep = 1;
                    }
                }
        ));
        animationForTheCharacter.getTimeline().setCycleCount(Animation.INDEFINITE);
        animationForTheCharacter.getTimeline().play();
    }
    public Character getCharacter(){
        return character;
    }

    private void fallingCharacter(){
        removeAllImgViewOfThePane();
        ImageView imgView = animationForTheCharacter.nextImage();
        double newHeight = animationForTheCharacter.getHeightMotionless()-imgView.getImage().getHeight();
        int pas = (int)fallingStep;
        boolean verif = false;
        while(!verif){
            if(verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY()+pas)){
                verif = true;
            }else{
                pas--;
            }
        }

        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY()+pas);
        if(pas==0){
            pas = 1;
            currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY()+newHeight);
            System.out.println(newHeight);
        }
        imgView.setX(currentCoordinateOfTheCharacter.getX());
        imgView.setY(currentCoordinateOfTheCharacter.getY());
        pane.getChildren().add(imgView);
        fallingStep += 0.5 * fallingStep;
        if(pas==1)
            isJumping = false;
    }

    public boolean verifyCollision(double x, double y){
        return collide.verify(animationForTheCharacter.actualImg().getImage(), new Coordinate(x, y), this);
    }

    private void removeAllImgViewOfThePane(){
        for(List<ImageView> listImg : character.getListOfPictureOfTheCharacter()) {
            for (ImageView imageView : listImg) {
                pane.getChildren().remove(imageView);
            }
        }
    }

    public Coordinate getCurrentCoordinateOfTheCharacter(){
        return currentCoordinateOfTheCharacter;
    }

    /**
     * Enable event for the key on the level of the game
     */
    private void enableEvent(){
        lvlOfTheGame.addEventHandler(KeyEvent.KEY_PRESSED, this::eventForMovement);

        lvlOfTheGame.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            currentKeyCode = null;
            timelineForMotionlessCharacter();
        });
    }

    /**
     * Function who will decide how character will move.
     * @param eventForPressedKey the event who can say what key is pressed.
     */
    private void eventForMovement(KeyEvent eventForPressedKey){
        if(eventForPressedKey.getCode() == KeyCode.D && eventForPressedKey.getCode()!=currentKeyCode){
            walkToRight = true;
            currentKeyCode = eventForPressedKey.getCode();
            timelineForWalk();
        }
        else if(eventForPressedKey.getCode() == KeyCode.Q && eventForPressedKey.getCode() != currentKeyCode){
            walkToRight = false;
            currentKeyCode = eventForPressedKey.getCode();
            timelineForReverseWalk();
        }else if(eventForPressedKey.getCode() == KeyCode.SPACE && !verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY()+1) && eventForPressedKey.getCode() != currentKeyCode && character.canJump()){
            currentKeyCode=eventForPressedKey.getCode();
            isJumping = true;
            this.jumpStrength = character.getJumpStrength();
        }
    }

    @Override
    public Node getAppropriateNode() {
        return animationForTheCharacter.actualImg();
    }

    @Override
    public CollideObject clone() {
        return null;
    }
}
