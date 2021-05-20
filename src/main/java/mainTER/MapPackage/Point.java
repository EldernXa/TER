package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.DBManage.CheckpointsDBManager;
import mainTER.DBManage.NbPointsDBManager;
import mainTER.DBManage.PointsUpgradeDBManager;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;
import mainTER.exception.CheckpointsCharacterDoesntExistException;
import mainTER.exception.CheckpointsMapDoesntExistException;

public class Point extends UnCollideObject{

    private final Coordinate coordinate;
    private final ImageViewSizePos defaultImage;
    private final String mapName;
    private PointsUpgradeDBManager pointsUpgradeDBManager ;
    private NbPointsDBManager nbPointsDBManager;
    private boolean isTaken ;


    public Point(Coordinate coordinate, String mapName) {

        this.coordinate = coordinate;
        isTaken = false;
        this.mapName = mapName;
        this.defaultImage = new ImageViewSizePos("/mainTER/MapPackage/Objects/point.png", coordinate);
        pointsUpgradeDBManager = new PointsUpgradeDBManager();
        nbPointsDBManager = new NbPointsDBManager();

    }

    /**
     *  Create the interaction on contact.
     */
    @Override
    public void interaction(DetectableObject detectableObject) {

        if(!isTaken){
            isTaken =true;
            effect();
        }

        defaultImage.getImageView().setX(-100);
        defaultImage.getImageView().setY(-100);
        defaultImage.setCoordinate(new Coordinate(-100,-100));
        defaultImage.getImageView().setImage(null);

    }

    /**
     * Setup the checkpointsDBManager
     */

    public void effect() {

        pointsUpgradeDBManager.setIsTaken(getX(),getY(),mapName,true);
        nbPointsDBManager.createTableNbPoints();
        if(nbPointsDBManager.getNbPoints() == -1){
            nbPointsDBManager.insertIntoTableNbPoints(pointsUpgradeDBManager.getPoints());
        }
        nbPointsDBManager.setNbPoints(nbPointsDBManager.getNbPoints()+1);


    }


    @Override
    public Node getAppropriateNode() {
        return defaultImage.getImageView();
    }

    @Override
    public Point clone() {
        return new Point(new Coordinate(this.getX(),this.getY()),mapName);
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
        return coordinate.getX();
    }

    @Override
    public double getY() {
        return coordinate.getY();
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
}
