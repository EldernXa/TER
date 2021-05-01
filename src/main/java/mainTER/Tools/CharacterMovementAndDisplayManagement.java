package mainTER.Tools;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CharacterMovementAndDisplayManagement {

    private final Pane pane;
    private ImageView imgView;
    private static final double MULTIPLY_X = 1/2.6;


    public CharacterMovementAndDisplayManagement(Pane pane){
        this.pane = pane;
    }

    public void displayNode(ImageView imgView, double x, double y){
        pane.getChildren().remove(this.imgView);
        this.imgView = imgView;
        imgView.setX(calculateRightPosition(new Coordinate(x,y)).getX());
        imgView.setY(calculateRightPosition(new Coordinate(x,y)).getY());
        pane.getChildren().add(imgView);
    }

    public Coordinate getCoordinateOfTheActualImg(){
        return new Coordinate(imgView.getX(), imgView.getY());
    }

    public Coordinate calculateRightPosition(Coordinate coordinate){
        return new Coordinate(coordinate.getX() - imgView.getImage().getWidth()*MULTIPLY_X, coordinate.getY());
    }

    public double getMultiplyX(){
        return MULTIPLY_X;
    }
}