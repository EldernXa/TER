package mainTER.MapPackage;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Map {

    private final MapFileReader mapFileReader;
    private final Pane pane;


    public Map(Collide collide, Pane pane, ImageView backgroundImage) {

        this.pane = pane;
        this.pane.setBackground(new Background(new BackgroundImage(backgroundImage.getImage(),BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT, new BackgroundSize(backgroundImage.getImage().getWidth(),
                backgroundImage.getImage().getHeight(),false,false,false,false))));




        mapFileReader = new MapFileReader("./src/main/resources/mainTER/MapPackage/Files/Forest.txt");
        collide.setCollisionObjectArrayList(mapFileReader.getCollisionObjectArrayList());

    }

    public MapFileReader getReadFileMap() {
        return mapFileReader;
    }

    public void addCollisionObject(){
        for (CollideObject collideObject : this.getReadFileMap().getCollisionObjectArrayList()){
            pane.getChildren().add(collideObject.getAppropriateNode());
        }
    }
}
