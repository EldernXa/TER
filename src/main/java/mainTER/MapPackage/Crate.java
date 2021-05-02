package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mainTER.CharacterGameplay.DisplayCharacter;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

import java.util.EventListener;

public class Crate extends InteractiveObject { //TODO make it a CollideObject not an InteractiveObject

    public Crate(Coordinate coordinate ) {
        super(coordinate, new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/crate.png",coordinate));
    }

    public void interaction(CollideObject collideObject) {
        if(collideObject.getY() + collideObject.getHeight() >= super.getY() + super.getHeight()*2/5) {
            if (collideObject.getCoordinate().getX() < super.getCoordinate().getX()) {
                    super.setCoordinate(new Coordinate(super.getCoordinate().getX() + calcMvt(CommingFrom.LEFT), super.getCoordinate().getY()));
            } else {
                    super.setCoordinate(new Coordinate(super.getCoordinate().getX() - calcMvt(CommingFrom.RIGHT), super.getCoordinate().getY()));
            }
        }
    }

    @Override
    public Node getAppropriateNode() {
        return super.getImageView();
    }

    @Override
    public Node clone() {
        ImageView imageView = new ImageView(super.getImageView().getImage());
        imageView.setLayoutX(super.getImageView().getLayoutX());
        imageView.setLayoutY(super.getImageView().getLayoutY());
        return imageView;
    }

    @Override
    public double getHMouvementSpan() {
        return 10;
    }

    @Override
    public double getJumpMouvementSpan() {
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
