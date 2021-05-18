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
    private Pane pane;
    private ImageView backgroundImage;
    private String fileName;
    public ArrayList<DetectableObject> detectableObjects1 = new ArrayList<>();
    public static ArrayList<ObjectLinker> objectLinkers = new ArrayList<>();



    public Map( Pane pane,String fileName,boolean bool) {

        this.pane = pane;
        String url = "./src/main/resources/mainTER/MapPackage/Files/";
        this.fileName = fileName;

        this.backgroundImage = new ImageView(new Image(new File("src/main/resources/mainTER/MapPackage/Sprites/Back/Background"+fileName +".png").toURI().toString()));



        mapFileReader = new MapFileReader(url , fileName,bool);

    }
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
        Music.stopMusic();
        Music.launchMenuSound(fileName);

    }


    public MapFileReader getReadFileMap() {
        return mapFileReader;
    }

    public void addCollisionObject(){
        for (DetectableObject detectableObject : this.getReadFileMap().getCollisionObjectArrayList()){
            pane.getChildren().add(detectableObject.getAppropriateNode());
            /*if(detectableObject instanceof Lever){
                pane.getChildren().add(((Lever)detectableObject).getInteractiveObject().getAppropriateNode());
            }*/
        }
        System.out.println(this.getReadFileMap().getCollisionObjectArrayList().size());
    }

    public void addCollisionObjectNetwork(boolean bool){


        Iterator<DetectableObject> list = this.getReadFileMap().getCollisionObjectArrayList().iterator();
        while (list.hasNext()) {
            DetectableObject detectableObject = list.next();
            DetectableObject detectableObject2 = detectableObject.clone();
            objectLinkers.add(new ObjectLinker(detectableObject, detectableObject2));
            /*if(detectableObject instanceof Lever){
                ((Lever)detectableObject2).setInteractiveObject(((Lever)detectableObject).getInteractiveObject().clone());
            }*/
            //TODO Add second pane for the clones
            detectableObjects1.add(detectableObject2);
            if(bool){
                pane.getChildren().add(detectableObject.getAppropriateNode());
            }else {
                pane.getChildren().add(detectableObject2.getAppropriateNode());
            }

        }

    }

    public void clearColissionObjectNetwork(){
        for (DetectableObject detectableObject : this.getReadFileMap().getCollisionObjectArrayList()){
            pane.getChildren().remove(detectableObject.getAppropriateNode());
        }
    }

    public ArrayList<DetectableObject> getCollideObjects1() {
        return detectableObjects1;
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
