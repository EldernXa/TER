package mainTER.MapPackage;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Map {

    private ReadFileMap readFileMap;
    private Pane pane;


    public Map(Collision collision, Pane pane,ImageView backgroundImage) {

        this.pane = pane;
        this.pane.setBackground(new Background(new BackgroundImage(backgroundImage.getImage(),BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT, new BackgroundSize(backgroundImage.getImage().getWidth(),
                backgroundImage.getImage().getHeight(),false,false,false,false))));




        readFileMap = new ReadFileMap("./src/main/resources/mainTER/MapPackage/Files/Forest.txt");
        collision.setCollisionObjectArrayList(readFileMap.getCollisionObjectArrayList());

    }

    public ReadFileMap getReadFileMap() {
        return readFileMap;
    }

    public void addCollisionObject(){
        for (CollisionObject collisionObject : this.getReadFileMap().getCollisionObjectArrayList()){
            pane.getChildren().add(collisionObject.getAppropriateNode());
        }
    }
}
