package mainTER.MapPackage;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mainTER.DBManage.BestProfileDBManager;
import mainTER.DBManage.ProfileDBManager;
import mainTER.Menu.MainMenu;
import mainTER.Menu.MenuItem;
import mainTER.Menu.MenuProfile;
import mainTER.Network.GameServer;
import mainTER.Network.Player;
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
    private boolean exist ;


    public EndObject(String name, Coordinate coordinate,boolean exist) {

        super(coordinate, new ImageViewSizePos("/mainTER/MapPackage/Objects/"+ name +".png",coordinate));
        this.name = name;
        this.exist = exist;
        if(!exist){
            this.getImageView().setImage(null);

        }
    }

    /**
     * Close the map when you touch the detectableObject, open the map selection menu.
     */
    public void interaction(DetectableObject detectableObject){
        try {

            if(isExist()){
                MenuItem.timeline.stop();


                ((Stage) detectableObject.getAppropriateNode().getScene().getWindow()).close();


                if(Player.multi == null){
                    nameProfile = MenuItem.pseudo.getText();
                    time = Integer.parseInt(MenuItem.timerLabel.getText());
                    mapName = MenuItem.mapName;
                    profileDBManager.createTableProfile();


                    if(!profileDBManager.nameExist(nameProfile,mapName)){
                        profileDBManager.insertIntoTableProfile(nameProfile,time,mapName);
                    }else{
                        System.out.println(time +" " + profileDBManager.getTime(nameProfile,mapName));
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
                    if(GameServer.ss != null)
                        GameServer.ss.close();


                    Platform.exit();
                    System.exit(0);

                }

            }

        }
        catch (Exception ignored){

        }
    }

    @Override
    public void actionTriggered() {


        if(!exist){
            this.getImageView().setImage(new ImageViewSizePos("/mainTER/MapPackage/Objects/"+ name +".png", getCoordinate()).getImageView().getImage());
            this.exist = true;
        }
    }

    @Override
    public Node getAppropriateNode() {
        return super.getImageView();
    }

    public boolean isExist() {
        return exist;
    }

    @Override
    public EndObject clone() {
        return new EndObject(name,new Coordinate(this.getX(),this.getY()),this.isExist());
    }

    @Override
    public double getHMouvementSpan() {
        return 0;
    }


    @Override
    public double rightMvt(DetectableObject detectableObject) {
        return detectableObject.getHMouvementSpan();
    }

    /**
     * Return the double corresponding to the down distance calculated between this as a UncollideObject and the @param detectableObject, so the return will be the HMouvementSpan
     * @param detectableObject
     * @return
     */
    @Override
    public double leftMvt(DetectableObject detectableObject) {
        return detectableObject.getHMouvementSpan();
    }

    /**
     * Return the double corresponding to the down distance calculated between this as a UncollideObject and the @param detectableObject, so the return will be the JumpMouvementSpan
     * @param detectableObject
     * @return
     */
    @Override
    public double upMvt(DetectableObject detectableObject) {
        return detectableObject.getJumpMouvementSpan();
    }

    /**
     * Return the double corresponding to the down distance calculated between this as a UncollideObject and the @param detectableObject, so the return will be the FallMouvementSpan
     * @param detectableObject
     * @return
     */
    @Override
    public double downMvt(DetectableObject detectableObject) {
        return detectableObject.getFallMouvementSpan();
    }



}
