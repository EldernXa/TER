package mainTER.CharacterGameplay;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import mainTER.DBManage.ControlsDBManager;
import mainTER.MapPackage.CollideObject;
import mainTER.MapPackage.CommingFrom;
import mainTER.MapPackage.SwitchCharacter;
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
    private final ArrayList<KeyCode> listCurrentKeyCode;
    private double fallingStep = 1;
    private boolean isJumping = false;
    private double jumpStrength;
    private static final int TPS_DURATION_TIMELINE = 100;
    private  String right;
    private  String left;
    private  String jump;
    private Camera camera;
    /**
     *
     * @param scene scene of the game.
     * @param pane is the level of the game.
     * @param character is the character we will display.
     */
    public DisplayCharacter(Scene scene, Pane pane, Character character, ArrayList<Character> listCharacter, StackPane stackPane, ImageView background, Stage stage){
        this(scene, pane, character);
        SwitchCharacter switchCharacter = new SwitchCharacter(listCharacter,this);
        double height = Screen.getPrimary().getBounds().getHeight();
        double h = 1;
        if(height>background.getImage().getHeight()){
            h = height/background.getImage().getHeight();
            Scale scale = new Scale(h, h, 0, 0);
            scene.getRoot().getTransforms().add(scale);
        }
        stackPane.getChildren().add(switchCharacter);
        camera = new Camera(scene, this, switchCharacter, listCharacter, h, background);
        characterMovementAndDisplayManagement.setCamera(camera);
    }

    public DisplayCharacter(Scene scene, Pane pane, Character character){
        this.lvlOfTheGame = scene;
        listCurrentKeyCode = new ArrayList<>();
        characterMovementAndDisplayManagement = new CharacterMovementAndDisplayManagement(pane, null);
        this.character = character;
        currentCoordinateOfTheCharacter = new Coordinate(character.getInitialCoordinate().getX(), character.getInitialCoordinate().getY());
        animationForTheCharacter = new AnimationCharacter(character);
        ControlsDBManager controlsDBManager = new ControlsDBManager();
        camera = null;

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
    public int getCurrentPosition(){
        return animationForTheCharacter.getCurrentPosition().ordinal();
    }
    public int getCurrentImage(){
        return animationForTheCharacter.getIndImgToAnimate();
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
    public void setCharacterFriend(Character characterToSwitch,int pos,int im){
        if(character.canChangeCharacter()) {
            animationForTheCharacter.getTimeline().stop();
            //double height = animationForTheCharacter.actualImg().getImage().getHeight();
            //animationForTheCharacter.changeCharacter(characterToSwitch);

            ImageView imgView = characterToSwitch.getListOfPictureOfTheCharacter().get(pos).get(im);
            currentCoordinateOfTheCharacter.setY(characterToSwitch.getInitialCoordinate().getY());
            currentCoordinateOfTheCharacter.setX(characterToSwitch.getInitialCoordinate().getX());
            //double newHeight = height - imgView.getImage().getHeight();
            //urrentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY() + newHeight);
            characterMovementAndDisplayManagement.displayNode(imgView, currentCoordinateOfTheCharacter.getX(),
                    currentCoordinateOfTheCharacter.getY());
            this.character = characterToSwitch;

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
                        if (isJumping) {
                            moveWalkJumping();
                        } else if (calcMvt(CommingFrom.UP) > 0) {
                            // TODO change gravity limit.
                            moveWalkFalling();
                        } else if (calcMvt(CommingFrom.LEFT) > 0) {
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
                        if (isJumping) {
                            moveReverseWalkJumping();
                        } else if (calcMvt(CommingFrom.UP) > 0) {
                            // TODO change gravity limit.
                            moveReverseWalkFalling();
                        } else if (calcMvt(CommingFrom.RIGHT)  > 0) {
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

                        if (isJumping) {
                            moveMotionlessJumping(newHeight);
                        }
                        else if (calcMvt(CommingFrom.UP) >= 0) {
                            // TODO change gravity limit.

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
        animationForTheCharacter.setWalk();
        currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX()+calcMvt(CommingFrom.LEFT));
        doJump();
    }

    private void moveWalkFalling(){
        if(calcMvt(CommingFrom.LEFT) > 0){
            animationForTheCharacter.setWalk();
            currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX()+calcMvt(CommingFrom.LEFT));
            fallingCharacter();
        }
        else{
            timelineForMotionlessCharacter();
        }
    }

    private void moveWalkNormally(){
        fallingStep = 1;
        double height = animationForTheCharacter.actualImg().getImage().getHeight();
        animationForTheCharacter.setWalk();
        currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX() + calcMvt(CommingFrom.LEFT));
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
        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY() - calcMvt(CommingFrom.DOWN));
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
            animationForTheCharacter.setReverseWalk();
            currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX() - calcMvt(CommingFrom.RIGHT));
            doJump();
    }

    private void moveReverseWalkFalling(){
        if (calcMvt(CommingFrom.RIGHT) > 0) {
            animationForTheCharacter.setReverseWalk();
            currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX() - calcMvt(CommingFrom.RIGHT));
            fallingCharacter();
        }else{
            timelineForMotionlessCharacter();
        }
    }

    private void moveReverseWalkNormally(){
        fallingStep = 1;
        double height = animationForTheCharacter.actualImg().getImage().getHeight();
        animationForTheCharacter.setReverseWalk();
        currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX() - calcMvt(CommingFrom.RIGHT));
        adaptYToHeight(height);
    }

    private void moveMotionlessJumping(double newHeight){

        if (walkToRight)
            animationForTheCharacter.setJump();
        else
            animationForTheCharacter.setReverseJump();

        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY() - calcMvt(CommingFrom.DOWN) - newHeight);
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


        double pasToDo = calcMvt(CommingFrom.UP);//moved it here
        ImageView imgView = animationForTheCharacter.nextImage();
        double newHeight = animationForTheCharacter.getHeightMotionless()-imgView.getImage().getHeight();
        //instead of here

        currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY() + pasToDo);
        if(pasToDo<=0){
            pasToDo = 1;
            currentCoordinateOfTheCharacter.setY(currentCoordinateOfTheCharacter.getY() + newHeight);
        }
        characterMovementAndDisplayManagement.displayNode(imgView, currentCoordinateOfTheCharacter.getX(),
                currentCoordinateOfTheCharacter.getY());
        if(fallingStep < 40) {
            fallingStep += 0.5 * fallingStep;
        }else{
            fallingStep = 40;
        }
        if(pasToDo == 1)
            isJumping = false;
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
        if(eventForPressedKey.getCode() == keyCode && (calcMvt(CommingFrom.UP)  <= 0) && !listCurrentKeyCode.contains(keyCode) && character.canJump()){
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
    public double getFallMouvementSpan() {
        return this.fallingStep;
    }

    @Override
    public double getJumpMouvementSpan() {
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
