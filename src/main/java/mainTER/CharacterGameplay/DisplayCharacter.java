package mainTER.CharacterGameplay;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import mainTER.MapPackage.Collision;
import mainTER.Tools.Coordinate;

import java.util.List;


/**
 * Class For the display of one character (with animation).
 */
public class DisplayCharacter {

    private final Coordinate currentCoordinateOfTheCharacter;
    private final AnimationCharacter animationForTheCharacter;
    private final Character character;
    private final Scene lvlOfTheGame;
    private final Pane pane;
    private boolean walkToRight = true;
    private final Collision collision;
    private KeyCode currentKeyCode;

    /**
     *
     * @param scene scene of the game.
     * @param pane is the level of the game.
     * @param character is the character we will display.
     */
    public DisplayCharacter(Scene scene, Pane pane, Character character, Collision collision){
        this.lvlOfTheGame = scene;
        this.pane = pane;
        this.character = character;
        this.collision = collision;
        currentCoordinateOfTheCharacter = new Coordinate(character.getInitialCoordinate().getX(), character.getInitialCoordinate().getY());
        animationForTheCharacter = new AnimationCharacter(character);
        ImageView initImgView = animationForTheCharacter.nextImage();
        initImgView.setX(currentCoordinateOfTheCharacter.getX());
        initImgView.setY(currentCoordinateOfTheCharacter.getY());
        pane.getChildren().add(initImgView);
        enableEvent();
        timelineForMotionlessCharacter();

    }

    private void timelineForWalk(){
        animationForTheCharacter.getTimeline().stop();
        animationForTheCharacter.getTimeline().getKeyFrames().clear();
        animationForTheCharacter.getTimeline().getKeyFrames().add(new KeyFrame(
                Duration.millis(100),
                tps->{
                    if(collision.verify(animationForTheCharacter.actualImg().getImage(), new Coordinate(currentCoordinateOfTheCharacter.getX()+character.getSpeed(), currentCoordinateOfTheCharacter.getY()))){
                        removeAllImgViewOfThePane();
                        animationForTheCharacter.setWalk();
                        currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX()+character.getSpeed());
                        ImageView imgView = animationForTheCharacter.nextImage();
                        imgView.setX(currentCoordinateOfTheCharacter.getX());
                        imgView.setY(currentCoordinateOfTheCharacter.getY());
                        pane.getChildren().add(imgView);
                    }else{
                        timelineForMotionlessCharacter();
                    }
                }
        ));

        animationForTheCharacter.getTimeline().setCycleCount(Animation.INDEFINITE);
        animationForTheCharacter.getTimeline().play();
    }

    private void timelineForReverseWalk(){
        animationForTheCharacter.getTimeline().stop();
        animationForTheCharacter.getTimeline().getKeyFrames().clear();
        animationForTheCharacter.getTimeline().getKeyFrames().add(new KeyFrame(
                Duration.millis(100),
                tps->{
                    if(collision.verify(animationForTheCharacter.actualImg().getImage(), new Coordinate(currentCoordinateOfTheCharacter.getX()-character.getSpeed(), currentCoordinateOfTheCharacter.getY()))){
                        removeAllImgViewOfThePane();
                        animationForTheCharacter.setReverseWalk();
                        currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX()-character.getSpeed());
                        ImageView imgView = animationForTheCharacter.nextImage();
                        imgView.setX(currentCoordinateOfTheCharacter.getX());
                        imgView.setY(currentCoordinateOfTheCharacter.getY());
                        pane.getChildren().add(imgView);
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
                Duration.millis(100),
                tps->{
                    removeAllImgViewOfThePane();
                    if(walkToRight)
                        animationForTheCharacter.setMotionless();
                    else
                        animationForTheCharacter.setReverseMotionLess();
                    ImageView imgView = animationForTheCharacter.nextImage();
                    imgView.setX(currentCoordinateOfTheCharacter.getX());
                    imgView.setY(currentCoordinateOfTheCharacter.getY());
                    pane.getChildren().add(imgView);
                }
        ));
        animationForTheCharacter.getTimeline().setCycleCount(Animation.INDEFINITE);
        animationForTheCharacter.getTimeline().play();
    }

    public void removeAllImgViewOfThePane(){
        for(List<ImageView> listImg : character.getListOfPictureOfTheCharacter()) {
            for (ImageView imageView : listImg) {
                pane.getChildren().remove(imageView);
            }
        }
    }

    /**
     * Enable event for the key on the level of the game
     */
    private void enableEvent(){

        lvlOfTheGame.setOnKeyPressed(this::eventForMovement);

        lvlOfTheGame.setOnKeyReleased(event -> {
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
        }
    }

}
