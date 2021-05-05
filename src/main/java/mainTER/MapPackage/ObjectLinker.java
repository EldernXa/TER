package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;

public class ObjectLinker extends CollideObject{

    CollideObject collideObject1;
    CollideObject collideObject2;

    public ObjectLinker(CollideObject collideObject1, CollideObject collideObject2){
        this.collideObject1 = collideObject1;
        this.collideObject2 = collideObject2;
    }

    public CollideObject getCollideObject1() {
        return collideObject1;
    }

    public CollideObject getCollideObject2() {
        return collideObject2;
    }

    public void setCollideObject1(CollideObject collideObject1) {
        this.collideObject1 = collideObject1;
    }

    public void setCollideObject2(CollideObject collideObject2) {
        this.collideObject2 = collideObject2;
    }


    @Override
    public Node getAppropriateNode() {
        return collideObject1.getAppropriateNode();
    }

    @Override
    public Node clone() {
        return null;
    }

    @Override
    public double getHMouvementSpan() {
        return collideObject1.getHMouvementSpan();
    }

    @Override
    public Coordinate getCoordinate() {
        return collideObject1.getCoordinate();
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {
        collideObject1.setCoordinate(coordinate);
        collideObject2.setCoordinate(coordinate);
    }

    @Override
    public double getX() {
        return collideObject1.getX();
    }

    @Override
    public double getY() {
        return collideObject1.getY();
    }

    @Override
    public void setX(double x) {
        collideObject1.setX(x);
        collideObject2.setX(x);
    }

    @Override
    public void setY(double y) {
        collideObject1.setY(y);
        collideObject2.setY(y);
    }

    @Override
    public double getWidth() {
        return collideObject1.getWidth();
    }

    @Override
    public double getHeight() {
        return collideObject1.getHeight();
    }
}
