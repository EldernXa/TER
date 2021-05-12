package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;

public class ObjectLinker extends DetectableObject {

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


    @Override
    public Node getAppropriateNode() {
        return detectableObject1.getAppropriateNode();
    }

    @Override
    public ObjectLinker clone() {
        return null;
    }

    @Override
    public double getHMouvementSpan() {
        return detectableObject1.getHMouvementSpan();
    }

    @Override
    public Coordinate getCoordinate() {
        return detectableObject1.getCoordinate();
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {
        detectableObject1.setCoordinate(coordinate);
        detectableObject2.setCoordinate(coordinate);
    }

    @Override
    public double getX() {
        return detectableObject1.getX();
    }

    @Override
    public double getY() {
        return detectableObject1.getY();
    }

    @Override
    public void setX(double x) {
        detectableObject1.setX(x);
        detectableObject2.setX(x);
    }

    @Override
    public void setY(double y) {
        detectableObject1.setY(y);
        detectableObject2.setY(y);
    }

    @Override
    public double getWidth() {
        return detectableObject1.getWidth();
    }

    @Override
    public double getHeight() {
        return detectableObject1.getHeight();
    }
}
