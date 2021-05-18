package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.Tools.Coordinate;

import java.util.concurrent.ExecutionException;

public abstract class DetectableObject {
    /**
     * Get the Node that corresponds to the appearance of the object
     *
     * @return
     */
    public abstract Node getAppropriateNode();

    /**
     * Create a new DetectableObject that is the same as the current one but has a different memory location
     *
     * @return Node
     */
    public abstract DetectableObject clone();

    /**
     * Return the length of the horizontal mouvement span
     *
     * @return
     */
    public abstract double getHMouvementSpan();

    /**
     * Return the length of the jump mouvement span
     *
     * @return
     */
    public double getJumpMouvementSpan() {
        return 0;
    }

    /**
     * Return the length of the falling mouvement span
     *
     * @return
     */
    public double getFallMouvementSpan() {
        return 0;
    }

    /**
     * Return this coordinate
     *
     * @return
     */
    public abstract Coordinate getCoordinate();

    /**
     * Set this coordinate with the value of @param
     *
     * @param coordinate
     */
    public abstract void setCoordinate(Coordinate coordinate);

    /**
     * Return the value of the x attribute in this coordinate
     *
     * @return
     */
    public abstract double getX();

    /**
     * Return the value of the y attribute in this coordinate
     *
     * @return
     */
    public abstract double getY();

    /**
     * Set the value of the x attribute in this coordinate with the value of @param
     *
     * @param x
     */
    public abstract void setX(double x);

    /**
     * Set the value of the x attribute in this coordinate with the value of @param
     *
     * @param y
     */
    public abstract void setY(double y);

    /**
     * Return the value of the appropriate node width
     *
     * @return
     */
    public abstract double getWidth();

    /**
     * Return the value of the appropriate node height
     *
     * @return
     */
    public abstract double getHeight();

    /**
     * Return the value of the minimum x of the appropriate node
     *
     * @return
     */
    public double getMinXBound() {
        return this.getAppropriateNode().getBoundsInParent().getMinX();
    }

    /**
     * Return the value of the maximum x of the appropriate node
     *
     * @return
     */
    public double getMaxXBound() {
        return this.getAppropriateNode().getBoundsInParent().getMaxX();
    }

    /**
     * Return the value of the maximum y of the appropriate node
     *
     * @return
     */
    public double getMaxYBound() {
        return this.getAppropriateNode().getBoundsInParent().getMaxY();
    }

    /**
     * Return the value of the minimum y of the appropriate node
     *
     * @return
     */
    public double getMinYBound() {
        return this.getAppropriateNode().getBoundsInParent().getMinY();
    }

    /**
     * Return the double corresponding to the right distance calculated between this and the @param detectableObject
     *
     * @param detectableObject
     * @return
     */
    public double rightMvt(DetectableObject detectableObject) {
        return this.getAppropriateNode().getBoundsInParent().getMinX() - detectableObject.getAppropriateNode().getBoundsInParent().getMaxX();
    }

    /**
     * Return the double corresponding to the left distance calculated between this and the @param detectableObject
     *
     * @param detectableObject
     * @return
     */
    public double leftMvt(DetectableObject detectableObject) {
        return detectableObject.getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX();
    }

    /**
     * Return the double corresponding to the up distance calculated between this and the @param detectableObject
     *
     * @param detectableObject
     * @return
     */
    public double upMvt(DetectableObject detectableObject) {
        return detectableObject.getAppropriateNode().getBoundsInParent().getMinY() - this.getAppropriateNode().getBoundsInParent().getMaxY();
    }

    /**
     * Return the double corresponding to the down distance calculated between this and the @param detectableObject
     *
     * @param detectableObject
     * @return
     */
    public double downMvt(DetectableObject detectableObject) {

        return this.getAppropriateNode().getBoundsInParent().getMinY() - detectableObject.getAppropriateNode().getBoundsInParent().getMaxY();
    }


    /**
     * Simulate the appropriate interaction between two objects
     *
     * @param detectableObject
     */
    public void interaction(DetectableObject detectableObject) {
    }

    /**
     * Return the double corresponding to the  distance calculated between this and the object colliding,
     * return the span corresponding if no object is colliding, it uses the @param to know the direction to consider
     *
     * @param commingFrom
     * @return
     */
    public double calcMvt(CommingFrom commingFrom) {

        Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInParent().getMaxX() - this.getAppropriateNode().getBoundsInParent().getMinX(), this.getAppropriateNode().getBoundsInParent().getMaxY() - this.getAppropriateNode().getBoundsInParent().getMinY());
        rect.setX(this.getX());
        rect.setY(this.getY());

        if (commingFrom == CommingFrom.LEFT) {
            return verifTrackRight();
        }
        if (commingFrom == CommingFrom.RIGHT) {
            return verifTrackLeft();
        }
        if (commingFrom == CommingFrom.UP) {
            return verifTrackDown();
        }
        if (commingFrom == CommingFrom.DOWN) {
            return verifTrackUp();
        }

        System.out.println("Gros probleme");
        return 0;
    }


    /**
     * Verify if there'll be an collide for the next right mouvement and return the distance between this and the object or return the HmouvementSpan if there's no collide to come
     *
     * @return
     */
    private double verifTrackRight() {
        double miniRightMvt = Double.MAX_VALUE;
        boolean calc = false;
        for (int i = 0; i < this.getHMouvementSpan(); i++) {
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInParent().getMaxX() - this.getAppropriateNode().getBoundsInParent().getMinX(), this.getAppropriateNode().getBoundsInParent().getMaxY() - this.getAppropriateNode().getBoundsInParent().getMinY());
            rect.setX(this.getX());
            rect.setY(this.getY());
            rect.setX(rect.getX() + i);

            for (DetectableObject detectableObject2 : MapFileReader.detectableObjectArrayList) {
                if ((!this.equals(detectableObject2)) && (rect.intersects(detectableObject2.getAppropriateNode().getBoundsInParent())) && (detectableObject2.getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX() >= 0) && (detectableObject2.getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX() <= this.getHMouvementSpan())) {//Prob it considers that it's true at the begening
                    calc = true;
                    detectableObject2.interaction(this);

                    multiInteractions(detectableObject2);

                    if (detectableObject2.rightMvt(this) < miniRightMvt) {
                        miniRightMvt = detectableObject2.rightMvt(this);
                    }
                }
            }
            if (calc) {
                return miniRightMvt;
            }
        }
        return this.getHMouvementSpan();
    }

    /**
     * Verify if there'll be an collide for the next left mouvement and return the distance between this and the object or return the HmouvementSpan if there's no collide to come
     *
     * @return
     */
    private double verifTrackLeft() {
        double miniLeftMvt = Double.MAX_VALUE;
        boolean calc = false;
        for (int i = 0; i < this.getHMouvementSpan(); i++) {
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInParent().getMaxX() - this.getAppropriateNode().getBoundsInParent().getMinX(), this.getAppropriateNode().getBoundsInParent().getMaxY() - this.getAppropriateNode().getBoundsInParent().getMinY());
            rect.setX(this.getX());
            rect.setY(this.getY());
            rect.setX(rect.getX() - i);
            for (DetectableObject detectableObject2 : MapFileReader.detectableObjectArrayList) {
                if ((!this.equals(detectableObject2)) && (rect.intersects(detectableObject2.getAppropriateNode().getBoundsInParent())) && (this.getAppropriateNode().getBoundsInParent().getMinX() - detectableObject2.getAppropriateNode().getBoundsInParent().getMaxX() >= 0) && (this.getAppropriateNode().getBoundsInParent().getMinX() - detectableObject2.getAppropriateNode().getBoundsInParent().getMaxX() <= this.getHMouvementSpan())) {
                    calc = true;
                    detectableObject2.interaction(this);

                    multiInteractions(detectableObject2);

                    if (detectableObject2.leftMvt(this) < miniLeftMvt) {
                        miniLeftMvt = detectableObject2.leftMvt(this);
                    }

                }
            }
            if (calc) {
                return miniLeftMvt;
            }
        }
        return this.getHMouvementSpan();
    }

    /**
     * Verify if there'll be an collide for the next up mouvement and return the distance between this and the object or return the jumpMouvementSpan if there's no collide to come
     *
     * @return
     */
    private double verifTrackUp() {
        double miniUpMvt = Double.MAX_VALUE;
        boolean calc = false;
        for (int i = 0; i < this.getJumpMouvementSpan(); i++) {
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInParent().getWidth(), this.getAppropriateNode().getBoundsInParent().getHeight());
            rect.setX(this.getX());
            rect.setY(this.getY() - i);
            for (DetectableObject detectableObject2 : MapFileReader.detectableObjectArrayList) {
                if (rect.intersects(detectableObject2.getAppropriateNode().getBoundsInParent())) {

                    try {
                        //System.out.println("--------"+ ((DisplayCharacter)this).getTempNewHeigt());
                    } catch (Exception e) {
                        //System.out.println("pas display");
                    }


                }
                if ((this.getAppropriateNode().getBoundsInParent().getMinY() - detectableObject2.getAppropriateNode().getBoundsInParent().getMaxY() >= 0) || (rect.intersects(detectableObject2.getAppropriateNode().getBoundsInParent()))) {
                }
                if ((!this.equals(detectableObject2)) && (rect.intersects(detectableObject2.getAppropriateNode().getBoundsInParent())) && ((this.getAppropriateNode().getBoundsInParent().getMinY() - detectableObject2.getAppropriateNode().getBoundsInParent().getMaxY() >= 0)/*||( (detectableObject2.getAppropriateNode().getBoundsInParent().getMinY() - this.getAppropriateNode().getBoundsInParent().getMaxY() + ((DisplayCharacter)this).getTempNewHeigt())>=0)*/ )/*&&(this.getAppropriateNode().getBoundsInParent().getMinY() - collideObject2.getAppropriateNode().getBoundsInParent().getMaxY() <= this.getJumpMouvementSpan())*/) { //a voir pour le -i
                    calc = true;
                    detectableObject2.interaction(this);

                    multiInteractions(detectableObject2);

                    if (detectableObject2.upMvt(this) < miniUpMvt) {
                        miniUpMvt = detectableObject2.upMvt(this);
                    }
                }
            }
            if (calc) {

                return miniUpMvt;
            }
        }
        return this.getJumpMouvementSpan();
    }

    /**
     * Verify if there'll be an collide for the next down mouvement and return the distance between this and the object or return the fallMouvementSpan if there's no collide to come
     *
     * @return
     */
    private double verifTrackDown() {
        double miniDownMvt = Double.MAX_VALUE;
        boolean calc = false;
        for (int i = 0; i < this.getFallMouvementSpan(); i++) {
            Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInParent().getMaxX() - this.getAppropriateNode().getBoundsInParent().getMinX(), this.getAppropriateNode().getBoundsInParent().getMaxY() - this.getAppropriateNode().getBoundsInParent().getMinY());
            rect.setX(this.getX());
            rect.setY(this.getY() + i);

            for (DetectableObject detectableObject2 : MapFileReader.detectableObjectArrayList) {

                if ((!this.equals(detectableObject2)) && (rect.intersects(detectableObject2.getAppropriateNode().getBoundsInParent())) && (detectableObject2.leftMvt(this) != 0 && detectableObject2.rightMvt(this) != 0) /*&&((detectableObject2.getAppropriateNode().getBoundsInParent().getMinY() - this.getAppropriateNode().getBoundsInParent().getMaxY()) >= 0)*/) {
                    calc = true;
                    detectableObject2.interaction(this);
                    multiInteractions(detectableObject2);
//                    System.out.println("distance between " + this + " and " + detectableObject2 + " = " + detectableObject2.downMvt(this));

                    if (detectableObject2.downMvt(this) < miniDownMvt) {
                        miniDownMvt = detectableObject2.downMvt(this);
                    }
                }
            }
            if (calc) {
                return miniDownMvt;
            }
        }
        return this.getFallMouvementSpan();
    }

    /**
     * Realise the interaction of the multi each time an interaction is made
     *
     * @param detectableObject
     */
    private void multiInteractions(DetectableObject detectableObject) {
        for (ObjectLinker objectLinker : Map.objectLinkers) {
            System.out.println("Object to be equals = " + detectableObject);
            if (objectLinker.getCollideObject1().equals(detectableObject)) {
                objectLinker.detectableObject2.interaction(this);
                System.out.println("Object1 = " + objectLinker.getCollideObject1());

            } else if (objectLinker.getCollideObject2().equals(detectableObject)) {
                objectLinker.detectableObject1.interaction(this);
                System.out.println("Object2 = " + objectLinker.getCollideObject2());

            } else {
                //print eror if you want
            }
        }
    }
}