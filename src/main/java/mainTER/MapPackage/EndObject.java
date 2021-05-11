package mainTER.MapPackage;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import mainTER.DBManage.ProfileDBManager;
import mainTER.Menu.MenuItem;
import mainTER.Menu.MenuLevel;
import mainTER.Menu.MenuProfile;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

import java.util.List;

public class EndObject extends InteractiveObject {

    String name;
    int time;
    String nameProfile;
    String mapName;
    ProfileDBManager profileDBManager = new ProfileDBManager();
    public EndObject(String name, Coordinate coordinate) {

        super(coordinate, new ImageViewSizePos("./src/main/resources/mainTER/MapPackage/Objects/"+name+".png",coordinate));
        this.name = name;

    }


    public void interaction(CollideObject collideObject){
        try {

                MenuItem.timeline.stop();


                ((Stage) collideObject.getAppropriateNode().getScene().getWindow()).close();

                nameProfile = MenuItem.pseudo.getText();
                time = Integer.parseInt(MenuItem.timerLabel.getText());
                mapName = MenuItem.mapName;
                //profileDBManager.insertIntoTableProfile(nameProfile,time,mapName);
                collideObject.setCoordinate(new Coordinate(0,0));
                Stage stage = new Stage();

                //MenuLevel menuLevel = new MenuLevel(stage);
                MenuProfile menuProfile = new MenuProfile(stage,nameProfile,time,mapName);
                Scene scene = new Scene( menuProfile.getPane(), 860,600);
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
        return new EndObject(name,new Coordinate(this.getX(),this.getY()));
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }


}
