package mainTER.Tools;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import mainTER.CharacterGameplay.Camera;

public class CharacterMovementAndDisplayManagement {

    private final Pane pane;
    private ImageView imgView;
    private static final double MULTIPLY_X = 1/2.6;
    private Camera camera;
    private boolean canDisplay;
    Thread t;

    public CharacterMovementAndDisplayManagement(Pane pane, Camera camera){
        this.pane = pane;
        this.camera = camera;
        this.canDisplay = true;
    }

    public void setCamera(Camera camera){
        this.camera = camera;
    }

    public void displayOtherNode(ImageView imgView, double x, double y){
        imgView.setX(calculateRightPosition(new Coordinate(x, y)).getX());
        imgView.setY(calculateRightPosition(new Coordinate(x, y)).getY());
        pane.getChildren().add(imgView);
    }

    public void removeOtherNode(ImageView imgView){
        pane.getChildren().remove(imgView);
    }

    public void displayNode(ImageView imgView, double x, double y){
        if(canDisplay) {
            pane.getChildren().remove(this.imgView);
            this.imgView = imgView;


            double tempX = imgView.getX();
            double tempY = imgView.getY();
            imgView.setX(calculateRightPosition(new Coordinate(x, y)).getX());
            imgView.setY(calculateRightPosition(new Coordinate(x, y)).getY());

            pane.getChildren().add(imgView);

            if (camera != null && (Math.abs(x - tempX) >= 30 || Math.abs(y - tempY) >= 30)) {

                camera.moveOnlyCamera();

            }
        }else{
            imgView.setX(this.imgView.getX());
            imgView.setY(this.imgView.getY());
            this.imgView = imgView;
        }

    }

    public void setCanDisplay(){
        canDisplay = true;
        pane.getChildren().add(imgView);
    }

    public void setCannotDisplay(){
        canDisplay = false;
        pane.getChildren().remove(imgView);
    }

    public Coordinate getCoordinateOfTheActualImg(){
        return new Coordinate(imgView.getX(), imgView.getY());
    }

    public Coordinate calculateRightPosition(Coordinate coordinate){
        return new Coordinate(coordinate.getX(), coordinate.getY());
    }

    public double getMultiplyX(){
        return MULTIPLY_X;
    }
}