package mainTER.MapPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.util.Iterator;

public class Map {

    private final MapFileReader mapFileReader;
    private final Pane pane;
    private ImageView backgroundImage;
    private String fileName;


    public Map( Pane pane,String fileName) {

        this.pane = pane;
        String url = "./src/main/resources/mainTER/MapPackage/Files/";
        this.fileName = fileName;

        this.backgroundImage = new ImageView(new Image(new File("src/main/resources/mainTER/MapPackage/Sprites/Back/Background"+fileName +".png").toURI().toString()));


        System.out.println(backgroundImage.getImage().getUrl());
        System.out.println(backgroundImage.getImage().getHeight());

        pane.setBackground( new Background(new BackgroundImage(backgroundImage.getImage(),BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT, new BackgroundSize(backgroundImage.getImage().getWidth(),
                backgroundImage.getImage().getHeight(),false,false,false,false))));


        mapFileReader = new MapFileReader(url , fileName);
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
        Iterator<CollideObject> list = this.getReadFileMap().getCollisionObjectArrayList().iterator();
        while (list.hasNext()) {
            CollideObject collideObject = list.next();


            pane.getChildren().add(collideObject.getAppropriateNode());
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
