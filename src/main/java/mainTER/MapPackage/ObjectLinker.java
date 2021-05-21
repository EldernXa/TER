package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;

import javax.swing.plaf.synth.SynthOptionPaneUI;

/**
 * Class usefull to link 2 items together so when something happens to one of both it happens to the second one too
 */
public class ObjectLinker {

    DetectableObject detectableObject1;
    DetectableObject detectableObject2;

    public ObjectLinker(DetectableObject detectableObject1, DetectableObject detectableObject2){
        this.detectableObject1 = detectableObject1;
        this.detectableObject2 = detectableObject2;
    }

    /**
     * Get the first DetectableObject
     * @return
     */
    public DetectableObject getCollideObject1() {
        return detectableObject1;
    }

    /**
     * Get the second DetectableObject
     * @return
     */
    public DetectableObject getCollideObject2() {
        return detectableObject2;
    }

    /**
     * Set the First DetectableObject
     * @param detectableObject1
     */
    public void setCollideObject1(DetectableObject detectableObject1) {
        this.detectableObject1 = detectableObject1;
    }

    /**
     * Set the second DetectableObject
     * @param detectableObject2
     */
    public void setCollideObject2(DetectableObject detectableObject2) {
        this.detectableObject2 = detectableObject2;
    }
}
