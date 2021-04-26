package mainTER.CharacterGameplay;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
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
    private double jumpStrength;
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

    }

    public void startDisplay(){
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
        double height = animationForTheCharacter.actualImg().getImage().getHeight();
        animationForTheCharacter.changeCharacter(characterToSwitch);

        ImageView imgView = animationForTheCharacter.nextImage();
        double newHeight = height-imgView.getImage().getHeight();
        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY()+newHeight);
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
                        adaptYToHeight(height);
                    }else{
                        timelineForMotionlessCharacter();
                    }
                }
        ));

        animationForTheCharacter.getTimeline().setCycleCount(Animation.INDEFINITE);
        animationForTheCharacter.getTimeline().play();
    }

    private double adaptYToHeight(double height) {
        ImageView imgView = animationForTheCharacter.nextImage();
        double newHeight = height - imgView.getImage().getHeight();
        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY()+newHeight);
        imgView.setX(currentCoordinateOfTheCharacter.getX());
        imgView.setY(currentCoordinateOfTheCharacter.getY());
        pane.getChildren().add(imgView);
        return newHeight;
    }

    private void doJump(double height) {
        removeAllImgViewOfThePane();
        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY()-jumpStrength);
        jumpStrength-= character.getWeight()*0.2;
        System.out.println(jumpStrength);
        if(jumpStrength<=0) {
            isJumping = false;
            jumpStrength = 0;
        }
        ImageView imgView = animationForTheCharacter.nextImage();
        double newHeight = height-imgView.getImage().getHeight();
        System.out.println(newHeight);
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

                        adaptYToHeight(height);
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
                    double newHeight = adaptYToHeight(height);
                    if(isJumping && verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY()-1)){
                        if(walkToRight)
                            animationForTheCharacter.setJump();
                        else
                            animationForTheCharacter.setReverseJump();

                        removeAllImgViewOfThePane();
                        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY()-jumpStrength-newHeight);
                        jumpStrength-= character.getWeight()*0.2;
                        if(jumpStrength<=0) {
                            isJumping = false;
                            jumpStrength = 0;
                        }
                        adaptYToHeight(height);
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
        lvlOfTheGame.addEventHandler(KeyEvent.KEY_PRESSED, this::eventForLeftMovement);
        lvlOfTheGame.addEventHandler(KeyEvent.KEY_PRESSED, this::eventForRightMovement);
        lvlOfTheGame.addEventHandler(KeyEvent.KEY_PRESSED, this::eventForJumpMovement);

        lvlOfTheGame.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if(event.getCode() == currentKeyCode) {
                currentKeyCode = null;
                timelineForMotionlessCharacter();
            }
        });
    }

    private void eventForRightMovement(KeyEvent eventForPressedKey){
        if(eventForPressedKey.getCode() == KeyCode.D && KeyCode.D != currentKeyCode){
            walkToRight = true;
            currentKeyCode = eventForPressedKey.getCode();
            timelineForWalk();
        }
    }

    private void eventForLeftMovement(KeyEvent eventForPressedKey){
        if(eventForPressedKey.getCode() == KeyCode.Q && KeyCode.Q != currentKeyCode){
            walkToRight = false;
            currentKeyCode = KeyCode.Q;
            timelineForReverseWalk();
        }
    }

    private void eventForJumpMovement(KeyEvent eventForPressedKey){
        if(eventForPressedKey.getCode() == KeyCode.SPACE && !verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY()+1) && eventForPressedKey.getCode() != currentKeyCode && character.canJump()){
            currentKeyCode = eventForPressedKey.getCode();
            isJumping = true;
            this.jumpStrength = character.getJumpStrength();
        }
    }

    @Override
    public Node getAppropriateNode() {
        return animationForTheCharacter.actualImg();
    }

    @Override
    public Node clone() {
        return new ImageView(animationForTheCharacter.actualImg().getImage());
    }

    @Override
    public double getHMouvementSpan() {
        return this.getCharacter().getSpeed();
    }

    @Override
    public double getVMouvementSpan() {
        return this.jumpStrength;
    }

    @Override
    public Coordinate getCoordinate() {
        return getCurrentCoordinateOfTheCharacter();
    }

    @Override
    public double getX() {
        return this.currentCoordinateOfTheCharacter.getX();
    }

    @Override
    public void setX(double x) { //Ne devrait pas foncitonner
        this.currentCoordinateOfTheCharacter.setX(x);
    }

    @Override
    public double getY() {
        return this.currentCoordinateOfTheCharacter.getY();
    }

    @Override
    public void setY(double y) { //Ne devrait pas foncitonner
        this.currentCoordinateOfTheCharacter.setY(y);
    }

    @Override
    public double getWidth() {
        return this.animationForTheCharacter.actualImg().getImage().getWidth();
    }

    @Override
    public double getHeight() {
        return this.animationForTheCharacter.actualImg().getImage().getHeight();
    }

    @Override
    public void setCoordinate(Coordinate coordinate) { //Ne devrait pas foncitonner
        this.getCoordinate().setX(coordinate.getX());
        this.getCoordinate().setY(coordinate.getY());
    }
}
