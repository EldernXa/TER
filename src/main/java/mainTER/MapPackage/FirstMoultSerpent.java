package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class FirstMoultSerpent extends UnCollideObject{

    private final Coordinate coordinate;
    private final ImageViewSizePos imgMoult;
    private final boolean reversePosition;

    public FirstMoultSerpent(Coordinate coordinate, boolean reversePosition){
        this.coordinate = coordinate;
        this.reversePosition = reversePosition;
        if(!reversePosition) {
            imgMoult = new ImageViewSizePos("/mainTER/CharacterGameplay/images/Serpent/moult/serpent.png", coordinate);
        }
        else{
            imgMoult = new ImageViewSizePos("/mainTER/CharacterGameplay/images/Serpent/moult/serpentReverse.png", coordinate);
        }
    }

    @Override
    public Node getAppropriateNode(){
        return imgMoult.getImageView();
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
    public FirstMoultSerpent clone(){
        return new FirstMoultSerpent(new Coordinate(getX(), getY()), reversePosition);
    }

}
