package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class SecondMoultSerpent extends CollideObject{

    private final Coordinate coordinate;
    private final ImageViewSizePos imgMoult;
    private final boolean reversePosition;

    public SecondMoultSerpent(Coordinate coordinate, boolean reversePosition){
        this.coordinate = coordinate;
        this.reversePosition = reversePosition;
        if(!reversePosition) {
            imgMoult = new ImageViewSizePos("/mainTER/CharacterGameplay/images/Serpent/moult/serpenta.png", coordinate);
        }else{
            imgMoult = new ImageViewSizePos("/mainTER/CharacterGameplay/images/Serpent/moult/serpentaReverse.png", coordinate);
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
    public SecondMoultSerpent clone(){
        return new SecondMoultSerpent(new Coordinate(this.getX(), this.getY()), reversePosition);
    }

}
