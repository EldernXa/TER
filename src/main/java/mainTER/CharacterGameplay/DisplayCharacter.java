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
import mainTER.DBManage.ControlsDBManager;
import mainTER.MapPackage.Collide;
import mainTER.MapPackage.CollideObject;
import mainTER.Tools.CharacterMovementAndDisplayManagement;
import mainTER.Tools.Coordinate;
import mainTER.exception.ControlsDataGetException;

import java.util.ArrayList;


/**
 * Class For the display of one character (with animation).
 */
public class DisplayCharacter extends CollideObject {

    private final Coordinate currentCoordinateOfTheCharacter;
    private final AnimationCharacter animationForTheCharacter;
    private Character character;
    private final Scene lvlOfTheGame;
    private final CharacterMovementAndDisplayManagement characterMovementAndDisplayManagement;
    private boolean walkToRight = true;
    private final Collide collide;
    private final ArrayList<KeyCode> listCurrentKeyCode;
    private double fallingStep = 1;
    private boolean isJumping = false;
    private double jumpStrength;
    private static final int TPS_DURATION_TIMELINE = 100;
    private  String right;
    private  String left;
    private  String jump;
    /**
     *
     * @param scene scene of the game.
     * @param pane is the level of the game.
     * @param character is the character we will display.
     */
    public DisplayCharacter(Scene scene, Pane pane, Character character, Collide collide){
        this.lvlOfTheGame = scene;
        listCurrentKeyCode = new ArrayList<>();
        characterMovementAndDisplayManagement = new CharacterMovementAndDisplayManagement(pane);
        this.character = character;
        this.collide = collide;
        currentCoordinateOfTheCharacter = new Coordinate(character.getInitialCoordinate().getX(), character.getInitialCoordinate().getY());
        animationForTheCharacter = new AnimationCharacter(character);
        ControlsDBManager controlsDBManager = new ControlsDBManager();

        left = "";
        right = "";
        jump = "";
        try {
            left = controlsDBManager.getLeft().toUpperCase();
            right = controlsDBManager.getRight().toUpperCase();
            jump = controlsDBManager.getJump();
        } catch (ControlsDataGetException e) {
            e.printStackTrace();
        }

    }

    public void startDisplay(){
        characterMovementAndDisplayManagement.displayNode(animationForTheCharacter.nextImage(), currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY());
        enableEvent();
        enableEventForSkill();
        timelineForMotionlessCharacter();
    }

    public void startDisplayFriend(){
        characterMovementAndDisplayManagement.displayNode(animationForTheCharacter.nextImage(), currentCoordinateOfTheCharacter.getX(),
                currentCoordinateOfTheCharacter.getY());
        timelineForMotionlessCharacter();
    }

    public void setCharacter(Character characterToSwitch){
        if(character.canChangeCharacter()) {
            disableEventForSkill();
            animationForTheCharacter.getTimeline().stop();
            double height = animationForTheCharacter.actualImg().getImage().getHeight();
            animationForTheCharacter.changeCharacter(characterToSwitch);

            ImageView imgView = animationForTheCharacter.nextImage();
            double newHeight = height - imgView.getImage().getHeight();
            currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY() + newHeight);
            characterMovementAndDisplayManagement.displayNode(imgView, currentCoordinateOfTheCharacter.getX(),
                    currentCoordinateOfTheCharacter.getY());
            this.character = characterToSwitch;
            for (Skill skill : character.getListSkill()) {
                if (skill.getClass() == ActiveSkill.class) {
                    ((ActiveSkill) skill).init();
                    lvlOfTheGame.addEventHandler(KeyEvent.KEY_PRESSED, ((ActiveSkill) skill).eventForSkill(animationForTheCharacter,
                            characterMovementAndDisplayManagement, TPS_DURATION_TIMELINE));
                }
            }
        }
    }

    private void disableEventForSkill(){
        for(Skill skill : character.getListSkill()){
            if(skill.getClass() == ActiveSkill.class){
                ((ActiveSkill) skill).init();
                lvlOfTheGame.removeEventHandler(KeyEvent.KEY_PRESSED, ((ActiveSkill)skill).eventForSkill(animationForTheCharacter,
                        characterMovementAndDisplayManagement, TPS_DURATION_TIMELINE));
            }
        }
    }

    private void enableEventForSkill(){
        for(Skill skill : character.getListSkill()){
            if(skill.getClass() == ActiveSkill.class){
                ((ActiveSkill)skill).init();
                lvlOfTheGame.addEventHandler(KeyEvent.KEY_PRESSED, ((ActiveSkill)skill).eventForSkill(animationForTheCharacter,
                        characterMovementAndDisplayManagement, TPS_DURATION_TIMELINE));
            }
        }
    }

    private void timelineForWalk(){
        if(animationForTheCharacter.getCanMove()) {
            animationForTheCharacter.getTimeline().stop();
            animationForTheCharacter.getTimeline().getKeyFrames().clear();
            animationForTheCharacter.getTimeline().getKeyFrames().add(new KeyFrame(
                    Duration.millis(TPS_DURATION_TIMELINE),
                    tps -> {
                            if (isJumping && verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY() - 1)) {
                                moveWalkJumping();
                            } else if (verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY() + 1)) {
                                moveWalkFalling();
                            } else if (verifyCollision(currentCoordinateOfTheCharacter.getX() + character.getSpeed(), currentCoordinateOfTheCharacter.getY())) {
                                moveWalkNormally();
                            } else {
                                timelineForMotionlessCharacter();
                            }
                    }
            ));

            animationForTheCharacter.getTimeline().setCycleCount(Animation.INDEFINITE);
            animationForTheCharacter.getTimeline().play();
        }
    }

    private void timelineForReverseWalk(){
        if(animationForTheCharacter.getCanMove()) {
            animationForTheCharacter.getTimeline().stop();
            animationForTheCharacter.getTimeline().getKeyFrames().clear();
            animationForTheCharacter.getTimeline().getKeyFrames().add(new KeyFrame(
                    Duration.millis(TPS_DURATION_TIMELINE),
                    tps -> {
                        if (isJumping && verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY() - 1)) {
                            moveReverseWalkJumping();
                        } else if (verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY() + 1)) {
                            moveReverseWalkFalling();
                        } else if (verifyCollision(currentCoordinateOfTheCharacter.getX() - character.getSpeed(), currentCoordinateOfTheCharacter.getY())) {
                            moveReverseWalkNormally();
                        } else {
                            timelineForMotionlessCharacter();
                        }
                    }
            ));
            animationForTheCharacter.getTimeline().setCycleCount(Animation.INDEFINITE);
            animationForTheCharacter.getTimeline().play();
        }
    }

    private void timelineForMotionlessCharacter(){
        if(animationForTheCharacter.getCanMove()) {
            animationForTheCharacter.getTimeline().stop();
            animationForTheCharacter.getTimeline().getKeyFrames().clear();
            animationForTheCharacter.getTimeline().getKeyFrames().add(new KeyFrame(
                    Duration.millis(TPS_DURATION_TIMELINE),
                    tps -> {
                        double height = animationForTheCharacter.actualImg().getImage().getHeight();
                        if (walkToRight)
                            animationForTheCharacter.setMotionless();
                        else
                            animationForTheCharacter.setReverseMotionLess();
                        double newHeight = adaptYToHeight(height);
                        if (isJumping && verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY() - 1)) {
                            moveMotionlessJumping(newHeight);
                        } else if (verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY() + 1)) {
                            moveMotionlessFalling();
                        } else {
                            moveMotionlessNormally();
                        }
                    }
            ));
            animationForTheCharacter.getTimeline().setCycleCount(Animation.INDEFINITE);
            animationForTheCharacter.getTimeline().play();
        }
    }

    private void moveWalkJumping(){
        if(verifyCollision(currentCoordinateOfTheCharacter.getX()+character.getSpeed(), currentCoordinateOfTheCharacter.getY())){
            animationForTheCharacter.setWalk();
            currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX()+character.getSpeed());
            doJump();
        }
    }

    private void moveWalkFalling(){
        if(verifyCollision(currentCoordinateOfTheCharacter.getX()+character.getSpeed(), currentCoordinateOfTheCharacter.getY())){
            animationForTheCharacter.setWalk();
            currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX()+character.getSpeed());
            fallingCharacter();
        }
    }

    private void moveWalkNormally(){
        fallingStep = 1;
        double height = animationForTheCharacter.actualImg().getImage().getHeight();
        animationForTheCharacter.setWalk();
        currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX() + character.getSpeed());
        adaptYToHeight(height);
    }

    private double adaptYToHeight(double height) {
        ImageView imgView = animationForTheCharacter.nextImage();
        double newHeight = height - imgView.getImage().getHeight();
        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY()+newHeight);
        characterMovementAndDisplayManagement.displayNode(imgView, currentCoordinateOfTheCharacter.getX(),
                currentCoordinateOfTheCharacter.getY());
        return newHeight;
    }

    private void doJump() {
        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY() - jumpStrength);
        jumpStrength -= character.getWeight() * 0.2;
        if (jumpStrength <= 0) {
            isJumping = false;
            jumpStrength = 0;
        }
        double height = character.getCharacteristics().getBestHeightOfAPosition(animationForTheCharacter.getCurrentPosition());
        ImageView imgView = animationForTheCharacter.nextImage();
        double newHeight = height - imgView.getImage().getHeight();
        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY()+newHeight);
        characterMovementAndDisplayManagement.displayNode(imgView, currentCoordinateOfTheCharacter.getX(),
                currentCoordinateOfTheCharacter.getY());

    }

    private void moveReverseWalkJumping(){
        if (verifyCollision(currentCoordinateOfTheCharacter.getX() - character.getSpeed(), currentCoordinateOfTheCharacter.getY())) {
            animationForTheCharacter.setReverseWalk();
            currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX() - character.getSpeed());
            doJump();
        }
    }

    private void moveReverseWalkFalling(){
        if (verifyCollision(currentCoordinateOfTheCharacter.getX() - character.getSpeed(), currentCoordinateOfTheCharacter.getY())) {
            animationForTheCharacter.setReverseWalk();
            currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX() - character.getSpeed());
            fallingCharacter();
        }
    }

    private void moveReverseWalkNormally(){
        fallingStep = 1;
        double height = animationForTheCharacter.actualImg().getImage().getHeight();
        animationForTheCharacter.setReverseWalk();
        currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX() - character.getSpeed());
        adaptYToHeight(height);
    }

    private void moveMotionlessJumping(double newHeight){
        if (walkToRight)
            animationForTheCharacter.setJump();
        else
            animationForTheCharacter.setReverseJump();

        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY() - jumpStrength - newHeight);
        jumpStrength -= character.getWeight() * 0.2;
        if (jumpStrength <= 0) {
            isJumping = false;
            jumpStrength = 0;
        }
        ImageView imgView = animationForTheCharacter.nextImage();
        newHeight = character.getCharacteristics().getHeightMotionless() - imgView.getImage().getHeight();
        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY()+newHeight);
        characterMovementAndDisplayManagement.displayNode(imgView, currentCoordinateOfTheCharacter.getX(),
                currentCoordinateOfTheCharacter.getY());
    }

    private void moveMotionlessFalling(){
        fallingCharacter();
    }

    private void moveMotionlessNormally(){
        fallingStep = 1;
    }

    public Character getCharacter(){
        return character;
    }

    private void fallingCharacter(){
        ImageView imgView = animationForTheCharacter.nextImage();
        double newHeight = animationForTheCharacter.getHeightMotionless()-imgView.getImage().getHeight();
        int pas = (int)fallingStep;
        boolean verif = false;
        int pasToDo = 1;
        while(!verif){
            if(verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY()+pasToDo) && pasToDo<pas){
                pasToDo++;
            }else{
                pasToDo--;
                verif = true;
            }
        }
        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY() + pasToDo);
        if(pasToDo<=0){
            pasToDo = 1;
            currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY() + newHeight);
        }
        characterMovementAndDisplayManagement.displayNode(imgView, currentCoordinateOfTheCharacter.getX(),
                currentCoordinateOfTheCharacter.getY());
        fallingStep += 0.5 * fallingStep;
        if(pasToDo == 1)
            isJumping = false;
    }

    public boolean verifyCollision(double x, double y){
        return collide.verify(animationForTheCharacter.actualImg().getImage(), new Coordinate(x, y), this);
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
            listCurrentKeyCode.remove(event.getCode());

            if(listCurrentKeyCode.isEmpty())
                timelineForMotionlessCharacter();
        });
    }

    private void eventForRightMovement(KeyEvent eventForPressedKey){
        KeyCode keyCode = KeyCode.getKeyCode(right);

        if(eventForPressedKey.getCode() == keyCode && !listCurrentKeyCode.contains(keyCode)){
            walkToRight = true;
            listCurrentKeyCode.add(keyCode);
            timelineForWalk();
        }
    }

    private void eventForLeftMovement(KeyEvent eventForPressedKey){
        KeyCode keyCode = KeyCode.getKeyCode(left);
        if(eventForPressedKey.getCode() == keyCode && !listCurrentKeyCode.contains(keyCode)){
            walkToRight = false;
            listCurrentKeyCode.add(keyCode);
            timelineForReverseWalk();
        }
    }

    private void eventForJumpMovement(KeyEvent eventForPressedKey){
        KeyCode keyCode = KeyCode.getKeyCode(jump);
        if(jump.equals(" ")){
            keyCode = KeyCode.SPACE;
        }
        if(eventForPressedKey.getCode() == keyCode && !verifyCollision(currentCoordinateOfTheCharacter.getX(), currentCoordinateOfTheCharacter.getY()+1) && !listCurrentKeyCode.contains(keyCode) && character.canJump()){
            listCurrentKeyCode.add(keyCode);
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
