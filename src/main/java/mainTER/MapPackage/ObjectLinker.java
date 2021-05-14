package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;

public class ObjectLinker {

    DetectableObject detectableObject1;
    DetectableObject detectableObject2;

    public ObjectLinker(DetectableObject detectableObject1, DetectableObject detectableObject2){
        this.detectableObject1 = detectableObject1;
        this.detectableObject2 = detectableObject2;
    }

    public DetectableObject getCollideObject1() {
        return detectableObject1;
    }

    public DetectableObject getCollideObject2() {
        return detectableObject2;
    }

    public void setCollideObject1(DetectableObject detectableObject1) {
        this.detectableObject1 = detectableObject1;
    }

    public void setCollideObject2(DetectableObject detectableObject2) {
        this.detectableObject2 = detectableObject2;
    }
}
