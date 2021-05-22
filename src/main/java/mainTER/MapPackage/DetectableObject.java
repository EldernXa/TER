package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.Tools.Coordinate;

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


            for (ObjectLinker objectLinker : Map.objectLinkers) {
                if ((!this.equals(objectLinker.getDetectableObject1())) && (rect.intersects(objectLinker.getDetectableObject1().getAppropriateNode().getBoundsInParent())) && (objectLinker.getDetectableObject1().getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX() >= 0) && (objectLinker.getDetectableObject1().getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX() <= this.getHMouvementSpan())) {//Prob it considers that it's true at the begening
                    calc = true;
                    objectLinker.getDetectableObject1().interaction(this);
                    if( !(objectLinker.getDetectableObject1() instanceof  UnCollideObject) && !(objectLinker.getDetectableObject1() instanceof  Lever)) {

                        if (objectLinker.getDetectableObject1().getY() >this.getY()+(this.getHeight()/4)*3) {


                            this.setY(objectLinker.getDetectableObject1().getY()-this.getHeight()-5);
                        }
                    }
                    multiInteractions(objectLinker.getDetectableObject1());

                    if (objectLinker.getDetectableObject1().rightMvt(this) < miniRightMvt) {
                        miniRightMvt = objectLinker.getDetectableObject1().rightMvt(this);
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
            for (ObjectLinker objectLinker : Map.objectLinkers) {
                if ((!this.equals(objectLinker.getDetectableObject1())) && (rect.intersects(objectLinker.getDetectableObject1().getAppropriateNode().getBoundsInParent())) && (this.getAppropriateNode().getBoundsInParent().getMinX() - objectLinker.getDetectableObject1().getAppropriateNode().getBoundsInParent().getMaxX() >= 0) && (this.getAppropriateNode().getBoundsInParent().getMinX() - objectLinker.getDetectableObject1().getAppropriateNode().getBoundsInParent().getMaxX() <= this.getHMouvementSpan())) {
                    calc = true;
                    objectLinker.getDetectableObject1().interaction(this);

                    if( !(objectLinker.getDetectableObject1() instanceof  UnCollideObject) && !(objectLinker.getDetectableObject1() instanceof  Lever)) {

                        if (objectLinker.getDetectableObject1().getY() >this.getY()+(this.getHeight()/4)*3) {

                            this.setY(objectLinker.getDetectableObject1().getY()-this.getHeight()-5);
                        }
                    }
                    multiInteractions(objectLinker.getDetectableObject1());

                    if (objectLinker.getDetectableObject1().leftMvt(this) < miniLeftMvt) {
                        miniLeftMvt = objectLinker.getDetectableObject1().leftMvt(this);
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
            Rectangle rect = new Rectangle(((DisplayCharacter)this).getAnimationForTheCharacter().getWidthMotionless(), ((DisplayCharacter)this).getAnimationForTheCharacter().getHeightMotionless());

            rect.setX(this.getX());
            rect.setY(this.getY() - ((DisplayCharacter)this).getTempNewHeigt());

            for (ObjectLinker objectLinker : Map.objectLinkers) {

                if (rect.intersects(objectLinker.getDetectableObject1().getAppropriateNode().getBoundsInParent())) {


                    if( !(objectLinker.getDetectableObject1() instanceof  UnCollideObject) && !(objectLinker.getDetectableObject1() instanceof  Lever)){
                        if((objectLinker.getDetectableObject1().leftMvt(this) != 0 && objectLinker.getDetectableObject1().getAppropriateNode().getBoundsInParent().getMinX()  - rect.getBoundsInParent().getMaxX() !=0)){


                            objectLinker.getDetectableObject1().interaction(this);
                            multiInteractions(objectLinker.getDetectableObject1());


                            this.setY( objectLinker.detectableObject1.getAppropriateNode().getBoundsInParent().getMaxY() +5 );
                        }
                    }


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

            for (ObjectLinker objectLinker : Map.objectLinkers) {

                        if(this.getAppropriateNode().getBoundsInParent().getMaxY() >= Map.mapHeight+30){


                            ((DisplayCharacter)this).death();
                        }


                    if ((!this.equals(objectLinker.getDetectableObject1()))&&(rect.intersects(objectLinker.getDetectableObject1().getAppropriateNode().getBoundsInParent()))&&
                            (objectLinker.getDetectableObject1().leftMvt(this) != 0 && objectLinker.getDetectableObject1().rightMvt(this) != 0)) {
                        calc = true;
                        objectLinker.getDetectableObject1().interaction(this);
                        multiInteractions(objectLinker.getDetectableObject1());

                        if ((objectLinker.getDetectableObject1().downMvt(this) < miniDownMvt)  ) {

                            miniDownMvt = objectLinker.getDetectableObject1().downMvt(this);
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
     * Check if their is a collision between the class object and the list of collide.
     * @param commingFrom
     * @return the distance between the two objects
     */
    public double check(String commingFrom) {

        double value = 10000;
        Rectangle rect = new Rectangle(this.getAppropriateNode().getBoundsInParent().getMaxX() - this.getAppropriateNode().getBoundsInParent().getMinX(), this.getAppropriateNode().getBoundsInParent().getMaxY() - this.getAppropriateNode().getBoundsInParent().getMinY());
        rect.setX(this.getX());
        rect.setY(this.getY());
        for (ObjectLinker objectLinker : Map.objectLinkers) {
            if (commingFrom.equals("Right")) {

                if ((!this.equals(objectLinker.getDetectableObject1())) && (rect.intersects(objectLinker.getDetectableObject1().getAppropriateNode().getBoundsInParent())) && (objectLinker.getDetectableObject1().getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX() >= 0) && (objectLinker.getDetectableObject1().getAppropriateNode().getBoundsInParent().getMinX() - this.getAppropriateNode().getBoundsInParent().getMaxX() <= this.getHMouvementSpan())) {

                    value = objectLinker.getDetectableObject1().rightMvt(this);
                }
            } else if (commingFrom.equals("Up")) {
                if (rect.intersects(objectLinker.getDetectableObject1().getAppropriateNode().getBoundsInParent())) {
                    if (!(objectLinker.getDetectableObject1() instanceof UnCollideObject) && !(objectLinker.getDetectableObject1() instanceof Lever)) {

                        if(((DisplayCharacter)this).getCharacter().getName().contains("Serpent")){
                            rect.setY(rect.getY()- (((DisplayCharacter)this).getAnimationForTheCharacter().getHeightMotionless() - rect.getHeight()));
                            rect.setHeight(((DisplayCharacter)this).getAnimationForTheCharacter().getHeightMotionless());

                        }
                        rect.setHeight(rect.getHeight() / 1.5);
                        if (objectLinker.getDetectableObject1().getAppropriateNode().getBoundsInParent().getMinY() - rect.getBoundsInParent().getMaxY() < 0) {
                            if ((objectLinker.getDetectableObject1().leftMvt(this) != 0 && objectLinker.getDetectableObject1().getAppropriateNode().getBoundsInParent().getMinX() - rect.getBoundsInParent().getMaxX() != 0)) {

                                value = objectLinker.getDetectableObject1().upMvt(this);
                            }
                        }
                    }

                }
            }
        }

        return value;
    }

    /**
     * Realise the interaction of the multi each time an interaction is made
     *
     * @param detectableObject
     */
    private void multiInteractions(DetectableObject detectableObject) {

        for (ObjectLinker objectLinker : Map.objectLinkers) {
            if ((objectLinker.getDetectableObject2() != null)){
                if (objectLinker.getDetectableObject1().equals(detectableObject)) {
                    objectLinker.detectableObject2.interaction(this);
                } else if (objectLinker.getDetectableObject2().equals(detectableObject)) {
                    objectLinker.detectableObject1.interaction(this);


                } else {
                    //print error if you want
                }
            }
        }
    }


}