package mainTER.MapPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import mainTER.Music.Music;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class Map {

    private final MapFileReader mapFileReader;
    private Pane pane = new Pane();
    private ImageView backgroundImage;
    private String fileName;
    public ArrayList<CollideObject> collideObjects1 = new ArrayList<>();
    public static ArrayList<ObjectLinker> objectLinkers = new ArrayList<>();



    public Map( Pane pane,String fileName) {

        this.pane = pane;
        String url = "./src/main/resources/mainTER/MapPackage/Files/";
        this.fileName = fileName;

        this.backgroundImage = new ImageView(new Image(new File("src/main/resources/mainTER/MapPackage/Sprites/Back/Background"+fileName +".png").toURI().toString()));

        mapFileReader = new MapFileReader(url , fileName);


    }
    public void displayMap(){

        pane.setBackground( new Background(new BackgroundImage(backgroundImage.getImage(),BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT, new BackgroundSize(backgroundImage.getImage().getWidth(),
                backgroundImage.getImage().getHeight(),false,false,false,false))));
        Music.stopMusique();
        Music.launchMenuSound(fileName);

    }


    public MapFileReader getReadFileMap() {
        return mapFileReader;
    }

    public void addCollisionObject(){
        for (CollideObject collideObject : this.getReadFileMap().getCollisionObjectArrayList()){

            pane.getChildren().add(collideObject.getAppropriateNode());
        }
    }

    public void addCollisionObjectNetwork(boolean bool){

        Iterator<CollideObject> list = this.getReadFileMap().getCollisionObjectArrayList().iterator();
        while (list.hasNext()) {
            CollideObject collideObject = list.next();
            CollideObject collideObject2 = collideObject.clone();
            objectLinkers.add(new ObjectLinker(collideObject, collideObject2));
            //TODO Add second pane for the clones
            collideObjects1.add(collideObject2);
            if(bool){
                pane.getChildren().add(collideObject.getAppropriateNode());
            }else {
                pane.getChildren().add(collideObject2.getAppropriateNode());
            }

        }

    }

    public void clearColissionObjectNetwork(){
        for (CollideObject collideObject : this.getReadFileMap().getCollisionObjectArrayList()){
            pane.getChildren().remove(collideObject.getAppropriateNode());
        }
    }

    public ArrayList<CollideObject> getCollideObjects1() {
        return collideObjects1;
    }

    public Pane getPane() {
        return pane;
    }

    public String getFileName() {
        return fileName;
    }

    public ImageView getBackgroundImage() {
        return backgroundImage;
    }


}
