package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class PaladinShieldMode extends CollideObject{

    private final Coordinate coordinate;
    private final ImageViewSizePos imgPaladinShield;
    private final boolean reversePosition;

    public PaladinShieldMode(Coordinate coordinate, boolean reversePosition){
        this.coordinate = new Coordinate(coordinate.getX(), coordinate.getY());
        this.reversePosition = reversePosition;
        if(!reversePosition){
            imgPaladinShield = new ImageViewSizePos("/mainTER/CharacterGameplay/images/Paladin/shieldmotionless/templieShieldIdle1.png", coordinate);
        }else{
            imgPaladinShield = new ImageViewSizePos("/mainTER/CharacterGameplay/images/Paladin/Shieldreversemotionless/templieShieldIdle1.png", coordinate);
        }
    }

    @Override
    public Node getAppropriateNode(){
        return imgPaladinShield.getImageView();
    }

    @Override
    public double getX(){
        return coordinate.getX();
    }

    @Override
    public double getY(){
        return coordinate.getY();
    }

    @Override
    public PaladinShieldMode clone(){
        return new PaladinShieldMode(new Coordinate(getX(), getY()), reversePosition);
    }

}
