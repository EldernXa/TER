package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

import java.util.EventListener;

public class Crate extends InteractiveObject {

    public Crate(Coordinate coordinate ) {
        super(coordinate, new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/crate.png",coordinate));
    }

    public void interaction(CollideObject collideObject) {
        if (collideObject.getCoordinate().getX() < super.getCoordinate().getX()) {
            if(verify(CommingFrom.LEFT)){
                super.setCoordinate(new Coordinate(super.getCoordinate().getX() + collideObject.getHMouvementSpan(), super.getCoordinate().getY()));
            }
        } else {
            if(verify(CommingFrom.RIGHT)) {
                super.setCoordinate(new Coordinate(super.getCoordinate().getX() - collideObject.getHMouvementSpan(), super.getCoordinate().getY()));
            }
        }
    }

    @Override
    public Node getAppropriateNode() {
        return super.getImageView();
    }

    @Override
    public Node clone() {
        return new ImageView(super.getImageView().getImage());
    }

    @Override
    public double getHMouvementSpan() {
        return 10;
    }

    @Override
    public double getVMouvementSpan() {
        return 0;
    }

    @Override
    public Coordinate getCoordinate() {
        return super.getCoordinate();
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {
        super.setCoordinate(coordinate);
    }
}
