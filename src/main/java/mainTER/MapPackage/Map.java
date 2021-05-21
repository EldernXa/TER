package mainTER.MapPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import mainTER.Music.Music;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Create the map by filling the appropriate pane
 */
public class Map {

    private final MapFileReader mapFileReader;
    private Pane pane;
    private ImageView backgroundImage;
    private String fileName;
    public static ArrayList<ObjectLinker> objectLinkers;
    public static double mapHeight;




    public Map( Pane pane,String fileName) {
        objectLinkers =  new ArrayList<>();

        this.pane = pane;
        String url = "./src/main/resources/mainTER/MapPackage/Files/";
        this.fileName = fileName;

        this.backgroundImage = new ImageView(new Image(new File("src/main/resources/mainTER/MapPackage/Sprites/Back/Background"+fileName +".png").toURI().toString()));

        mapHeight = backgroundImage.getImage().getHeight();

        mapFileReader = new MapFileReader(url , fileName);

    }

    /**
     * Set the map background
     */
    public void displayMap(){
        pane.setBackground( new Background(new BackgroundImage(backgroundImage.getImage(),BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT, new BackgroundSize(backgroundImage.getImage().getWidth(),
                backgroundImage.getImage().getHeight(),false,false,false,false))));
        //Music.stopMusic();
        //Music.launchMenuSound(fileName);

    }

    /**
     * Return the mapFileReader
     * @return
     */
    public MapFileReader getReadFileMap() {
        return mapFileReader;
    }

    /**
     * Add all the Detectable object's nodes to the appropriate pane
     * @param bool
     */
    public void addCollisionObjectNetwork(boolean bool){
        Iterator<DetectableObject> list = this.getReadFileMap().getDetectableObjectArrayList().iterator();
        while (list.hasNext()) {
            DetectableObject detectableObject = list.next();
            DetectableObject detectableObject2 = detectableObject.clone();
            objectLinkers.add(new ObjectLinker(detectableObject, detectableObject2));
            if(detectableObject instanceof Lever){
                ((Lever)detectableObject2).setInteractiveObject(((Lever)detectableObject).getInteractiveObject().clone());
                objectLinkers.add(new ObjectLinker(((Lever)detectableObject).getInteractiveObject(),((Lever)detectableObject2).getInteractiveObject()));
            }
            //TODO finir ça
            if(bool){
                pane.getChildren().add(detectableObject.getAppropriateNode());
                if(detectableObject instanceof Lever){
                    pane.getChildren().add(((Lever)detectableObject).getInteractiveObject().getAppropriateNode());
                }
            }
            else {
                pane.getChildren().add(detectableObject2.getAppropriateNode());
                if(detectableObject instanceof Lever){
                    pane.getChildren().add(((Lever)detectableObject2).getInteractiveObject().getAppropriateNode());                }
            }
        }
    }

    /**
     * Remove all Detectable object's node from pane
     */
    public void clearColissionObjectNetwork(){
        for (DetectableObject detectableObject : this.getReadFileMap().getDetectableObjectArrayList()){
            pane.getChildren().remove(detectableObject.getAppropriateNode());
        }
    }

    /**
     * Return the pane
     * @return
     */
    public Pane getPane() {
        return pane;
    }

    /**
     * Return the File name
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Return the background image
     * @return
     */
    public ImageView getBackgroundImage() {
        return backgroundImage;
    }


}
