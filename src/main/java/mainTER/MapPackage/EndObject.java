package mainTER.MapPackage;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mainTER.DBManage.BestProfileDBManager;
import mainTER.DBManage.ProfileDBManager;
import mainTER.Menu.MainMenu;
import mainTER.Menu.MenuItem;
import mainTER.Menu.MenuProfile;
import mainTER.Network.GameServer;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;
/**
 * Create a EndObject item that will be placed on the map.
 * It close the map and bring back to map selection menu on contact.
 */
public class EndObject extends InteractiveObject {

    String name;
    int time;
    String nameProfile;
    String mapName;
    ProfileDBManager profileDBManager = new ProfileDBManager();
    BestProfileDBManager bestProfileDBManager = new BestProfileDBManager();
    public EndObject(String name, Coordinate coordinate) {

        super(coordinate, new ImageViewSizePos("/mainTER/MapPackage/Objects/"+ name +".png",coordinate));
        this.name = name;

    }

    /**
     * Close the map when you touch the detectableObject, open the map selection menu.
     */
    public void interaction(DetectableObject detectableObject){
        try {

                MenuItem.timeline.stop();


                ((Stage) detectableObject.getAppropriateNode().getScene().getWindow()).close();


                if(GameServer.multi == null){
                    nameProfile = MenuItem.pseudo.getText();
                    time = Integer.parseInt(MenuItem.timerLabel.getText());
                    mapName = MenuItem.mapName;
                    profileDBManager.createTableProfile();

                    if(!profileDBManager.nameExist(nameProfile,mapName)){
                        profileDBManager.insertIntoTableProfile(nameProfile,time,mapName);
                    }else{
                        if(time< profileDBManager.getTime(nameProfile,mapName)){
                            profileDBManager.setTime(nameProfile,time,mapName);
                        }
                    }

                    bestProfileDBManager.createTableBestProfile();
                    if(bestProfileDBManager.getTime(mapName) != -1){
                        if(time < bestProfileDBManager.getTime(mapName)){
                            bestProfileDBManager.setTime(time,mapName);
                            bestProfileDBManager.setName(nameProfile,mapName);
                        }
                    }else {
                        bestProfileDBManager.insertIntoTableBestProfile(nameProfile,time,mapName);
                    }

                    detectableObject.setCoordinate(new Coordinate(0,0));
                    Stage stage = new Stage();

                    MenuProfile menuProfile = new MenuProfile(stage,nameProfile,time,mapName);
                    Scene scene = new Scene( menuProfile.getPane(), 860,600);
                    stage.setScene(scene);
                    stage.show();
                }else {
                    MainMenu.createContent(new Stage());
                }


        }
        catch (Exception ignored){

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
