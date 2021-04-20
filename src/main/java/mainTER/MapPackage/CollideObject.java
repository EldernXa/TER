package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.CharacterGameplay.DisplayCharacter;

public abstract class CollideObject {
    public abstract Node getAppropriateNode();
    public abstract CollideObject clone();
    public void interaction(DisplayCharacter displayCharacter){}
    //TODO Forsee if the the movement cause an interaction
    //TODO généraliser au CollideObjects
}
