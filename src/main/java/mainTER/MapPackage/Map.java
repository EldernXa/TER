package mainTER.MapPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class Map {

    private final MapFileReader mapFileReader;
    private Pane pane = new Pane();
    private ImageView backgroundImage;
    private String fileName;


    public Map( Pane pane,String fileName) {


        String url = "./src/main/resources/mainTER/MapPackage/Files/";
        this.fileName = fileName;

        this.backgroundImage = new ImageView(new Image(new File("src/main/resources/mainTER/MapPackage/Sprites/Back/Background"+fileName +".png").toURI().toString()));




        mapFileReader = new MapFileReader(url , fileName);


    }
    public void displayMap(Pane pane){
        this.pane = pane;
        pane.setBackground( new Background(new BackgroundImage(backgroundImage.getImage(),BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT, new BackgroundSize(backgroundImage.getImage().getWidth(),
                backgroundImage.getImage().getHeight(),false,false,false,false))));
        addCollisionObjectNetwork();

    }


    public MapFileReader getReadFileMap() {
        return mapFileReader;
    }

    public void addCollisionObject(){
        for (CollideObject collideObject : this.getReadFileMap().getCollisionObjectArrayList()){

            pane.getChildren().add(collideObject.getAppropriateNode());
        }
    }

    public void addCollisionObjectNetwork(){
        ArrayList<ObjectLinker> objectLinkers = new ArrayList<>();
        Iterator<CollideObject> list = this.getReadFileMap().getCollisionObjectArrayList().iterator();
        while (list.hasNext()) {
            CollideObject collideObject = list.next();
            objectLinkers.add(new ObjectLinker(collideObject, collideObject.clone()));

            pane.getChildren().add(collideObject.getAppropriateNode());
        }

    }

    public void clearColissionObjectNetwork(){
        for (CollideObject collideObject : this.getReadFileMap().getCollisionObjectArrayList()){
            pane.getChildren().remove(collideObject.getAppropriateNode());
        }
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
