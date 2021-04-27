package mainTER.Tools;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CharacterMovementAndDisplayManagement {

    private final Pane pane;
    private ImageView imgView;


    public CharacterMovementAndDisplayManagement(Pane pane){
        this.pane = pane;
    }

    public void displayNode(ImageView imgView, double x, double y){
        pane.getChildren().remove(this.imgView);
        this.imgView = imgView;
        imgView.setX(x);
        imgView.setY(y);
        pane.getChildren().add(imgView);
    }

}
