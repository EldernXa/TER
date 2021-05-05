package mainTER.MapPackage;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import mainTER.Menu.MenuLevel;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

import java.util.List;

public class EndObject extends InteractiveObject {


    public EndObject(Coordinate coordinate) {
        super(coordinate, new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/forgottensword.png",coordinate));

    }


    public void interaction(CollideObject collideObject){
        try {



                ((Stage) collideObject.getAppropriateNode().getScene().getWindow()).close();
                 collideObject.setCoordinate(new Coordinate(0,0));
                Stage stage = new Stage();

                MenuLevel menuLevel = new MenuLevel(stage);
                Scene scene = new Scene( menuLevel.getPane(), 860,600);
                stage.setScene(scene);
                stage.show();

        }
        catch (Exception e){

        }
    }

    @Override
    public Node getAppropriateNode() {
        return super.getImageView();
    }

    @Override
    public EndObject clone() {
        return new EndObject(new Coordinate(this.getX(),this.getY()));
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }


}
