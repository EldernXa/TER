package mainTER.CharacterGameplay;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * Class For the display of one character (with animation).
 */
public class DisplayCharacter {

    private Coordinate currentCoordinateOfTheCharacter;
    private AnimationCharacter animationForTheCharacter;
    private ImageView imgViewOfTheCharacter;
    private final Pane lvlOfTheGame;

    /**
     *
     * @param node is the level of the game.
     * @param character is the character we will display.
     */
    public DisplayCharacter(Pane node, Character character){
        // TODO get ImageView thanks to character.
        this.lvlOfTheGame = node;
        node.getChildren().add(imgViewOfTheCharacter);
        enableEvent();
    }

    /**
     * Enable event for the key on the level of the game
     */
    private void enableEvent(){
        lvlOfTheGame.setOnKeyPressed(eventKeyPressed->{
            eventForMovement(eventKeyPressed);
        });
    }

    /**
     * Function who will decide how character will move.
     * @param eventForPressedKey the event who can say what key is pressed.
     */
    private void eventForMovement(KeyEvent eventForPressedKey){
        // TODO do the movement for the character.
    }

}
