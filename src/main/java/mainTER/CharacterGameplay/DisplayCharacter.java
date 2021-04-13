package mainTER.CharacterGameplay;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


/**
 * Class For the display of one character (with animation).
 */
public class DisplayCharacter {

    private Coordinate currentCoordinateOfTheCharacter;
    private AnimationCharacter animationForTheCharacter;
    private final Scene lvlOfTheGame;
    private final Pane pane;
    private boolean walkToRight = false;

    /**
     *
     * @param scene
     * @param pane is the level of the game.
     * @param character is the character we will display.
     */
    public DisplayCharacter(Scene scene, Pane pane, Character character){
        this.lvlOfTheGame = scene;
        this.pane = pane;
        currentCoordinateOfTheCharacter = new Coordinate(0, 0);
        animationForTheCharacter = new AnimationCharacter(character);
        ImageView initImgView = animationForTheCharacter.nextImage();
        initImgView.setX(currentCoordinateOfTheCharacter.getX());
        initImgView.setY(currentCoordinateOfTheCharacter.getY());
        pane.getChildren().add(initImgView);
        enableEvent();
        animationForTheCharacter.getTimeline().getKeyFrames().add(new KeyFrame(
                Duration.millis(100),
                tps->{
                    if(walkToRight){
                        pane.getChildren().clear();
                        currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX()+5);
                        ImageView imgView = animationForTheCharacter.nextImage();
                        imgView.setX(currentCoordinateOfTheCharacter.getX());
                        pane.getChildren().add(imgView);
                    }
                }));
        animationForTheCharacter.getTimeline().setCycleCount(Animation.INDEFINITE);
        animationForTheCharacter.getTimeline().play();
    }

    /**
     * Enable event for the key on the level of the game
     */
    private void enableEvent(){

        lvlOfTheGame.setOnKeyPressed(eventKeyPressed->{
            eventForMovement(eventKeyPressed);
        });

        lvlOfTheGame.setOnKeyReleased(event -> {
            walkToRight = false;
        });
    }

    /**
     * Function who will decide how character will move.
     * @param eventForPressedKey the event who can say what key is pressed.
     */
    private void eventForMovement(KeyEvent eventForPressedKey){
        if(eventForPressedKey.getCode() == KeyCode.D){
            //System.out.println("okok");
            walkToRight = true;

        }
    }

}
