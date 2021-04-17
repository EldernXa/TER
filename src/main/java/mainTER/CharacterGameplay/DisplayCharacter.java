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

import java.util.ArrayList;


/**
 * Class For the display of one character (with animation).
 */
public class DisplayCharacter {

    private Coordinate currentCoordinateOfTheCharacter;
    private AnimationCharacter animationForTheCharacter;
    private final Character character;
    private final Scene lvlOfTheGame;
    private final Pane pane;
    private boolean walkToRight = false;
    private boolean walkToLeft = false;

    /**
     *
     * @param scene
     * @param pane is the level of the game.
     * @param character is the character we will display.
     */
    public DisplayCharacter(Scene scene, Pane pane, Character character, Collision collision){
        this.lvlOfTheGame = scene;
        this.pane = pane;
        this.character = character;
        currentCoordinateOfTheCharacter = new Coordinate(character.getInitialCoordinate().getX(), character.getInitialCoordinate().getY());
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
                        //TODO Global variable for step
                        if(collision.verify(animationForTheCharacter.actualImg().getImage(), new Coordinate(currentCoordinateOfTheCharacter.getX()+10,currentCoordinateOfTheCharacter.getY()))){
                            removeAllImgViewOfThePane();
                            currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX()+10);
                            ImageView imgView = animationForTheCharacter.nextImage();
                            imgView.setX(currentCoordinateOfTheCharacter.getX());
                            imgView.setY(currentCoordinateOfTheCharacter.getY());
                            pane.getChildren().add(imgView);
                        }

                    }
                    else if(walkToLeft){
                        if(collision.verify(animationForTheCharacter.actualImg().getImage(), new Coordinate(currentCoordinateOfTheCharacter.getX()-10,currentCoordinateOfTheCharacter.getY()))){
                            removeAllImgViewOfThePane();
                            currentCoordinateOfTheCharacter.setX(currentCoordinateOfTheCharacter.getX()-10);
                            ImageView imgView = animationForTheCharacter.nextImage();
                            imgView.setX(currentCoordinateOfTheCharacter.getX());
                            imgView.setY(currentCoordinateOfTheCharacter.getY());
                            pane.getChildren().add(imgView);
                        }
                    }
                }));
        animationForTheCharacter.getTimeline().setCycleCount(Animation.INDEFINITE);
        animationForTheCharacter.getTimeline().play();
    }

    public void removeAllImgViewOfThePane(){
        for(ArrayList<ImageView> listImg : character.getListOfPictureOfTheCharacter()) {
            for (ImageView imageView : listImg) {
                pane.getChildren().remove(imageView);
            }
        }
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
            walkToLeft = false;
        });
    }

    /**
     * Function who will decide how character will move.
     * @param eventForPressedKey the event who can say what key is pressed.
     */
    private void eventForMovement(KeyEvent eventForPressedKey){
        if(eventForPressedKey.getCode() == KeyCode.D){
            //System.out.println("okok");
            walkToLeft = false;
            walkToRight = true;
            animationForTheCharacter.setWalk();
        }
        else if(eventForPressedKey.getCode() == KeyCode.Q){
            walkToRight = false;
            walkToLeft = true;
            animationForTheCharacter.setReverseWalk();
        }
    }

}
