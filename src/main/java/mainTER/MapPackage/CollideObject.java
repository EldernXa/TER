package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.CharacterGameplay.DisplayCharacter;

public abstract class CollideObject {
    public abstract Node getAppropriateNode();
    public void interaction(DisplayCharacter displayCharacter){}
}
