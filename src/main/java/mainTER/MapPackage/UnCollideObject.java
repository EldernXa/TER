package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;

public class UnCollideObject extends DetectableObject {
    @Override
    public Node getAppropriateNode() {
        return null;
    }

    @Override
    public DetectableObject clone() {
        return null;
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }

    @Override
    public Coordinate getCoordinate() {
        return null;
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {

    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public void setX(double x) {

    }

    @Override
    public void setY(double y) {

    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    /**
     * Return the double corresponding to the down distance calculated between this as a UncollideObject and the @param detectableObject, so the return will be the HMouvementSpan
     * @param detectableObject
     * @return
     */
    @Override
    public double rightMvt(DetectableObject detectableObject) {
        return detectableObject.getHMouvementSpan();
    }

    /**
     * Return the double corresponding to the down distance calculated between this as a UncollideObject and the @param detectableObject, so the return will be the HMouvementSpan
     * @param detectableObject
     * @return
     */
    @Override
    public double leftMvt(DetectableObject detectableObject) {
        return detectableObject.getHMouvementSpan();
    }

    /**
     * Return the double corresponding to the down distance calculated between this as a UncollideObject and the @param detectableObject, so the return will be the JumpMouvementSpan
     * @param detectableObject
     * @return
     */
    @Override
    public double upMvt(DetectableObject detectableObject) {
        return detectableObject.getJumpMouvementSpan();
    }

    /**
     * Return the double corresponding to the down distance calculated between this as a UncollideObject and the @param detectableObject, so the return will be the FallMouvementSpan
     * @param detectableObject
     * @return
     */
    @Override
    public double downMvt(DetectableObject detectableObject) {
        System.out.println("Technique NON calculée");
        return detectableObject.getFallMouvementSpan();
    }
}
