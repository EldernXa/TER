package mainTER.Tools;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Objects;

public class ImageViewSizePos {
    private ImageView imageView = new ImageView();
    private Coordinate coordinate;
    String pathImage;

    /**
     * Constructeur permettant de créer une ImageViewPosSize dans rien préciser
     */
    public ImageViewSizePos(){
    }

    /**
     * Constructeur permettant de créer une ImageViewPosSize en précisant le lien de l'image, ainsi que les coordonnée ou l'on
     * souhaite que l'image apparaisse sur l'écran
     * @param pathImage
     * @param coordinate
     */
    public ImageViewSizePos(String pathImage, Coordinate coordinate){
        this.pathImage = pathImage;
        this.coordinate = coordinate;
        imageView.setImage(new Image(new File(Objects.requireNonNull(this.getClass().getResource(pathImage)).getPath().replace("%20", " ")).toURI().toString()));
        setCoordinate(coordinate);
    }

    /**
     * Constructeur permettant de créer une ImageViewPosSize en précisant le lien de l'image, ainsi que les coordonnée ou l'on
     * souhaite que l'image apparaisse sur l'écran, ainsi que la dimension que l'on souhaite pour notre image
     * @param pathImage
     * @param width
     * @param height
     * @param coordinate
     */
    public ImageViewSizePos(String pathImage, double width, double height, Coordinate coordinate){
        this.pathImage = pathImage;
        this.coordinate = coordinate;
        imageView.setImage(new Image(new File(pathImage).toURI().toString()));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        setCoordinate(coordinate);
    }

    /**
     * Constructeur permettant de créer une ImageViewPosSize en précisant le lien de l'image, ainsi que
     * la dimension que l'on souhaite pour notre image sans préciser ou l'ont souhaite qu'lle soit placée
     * @param pathImage
     * @param width
     * @param height
     */
    public ImageViewSizePos(String pathImage, double width, double height){
        this.pathImage = pathImage;
        imageView.setImage(new Image(new File(pathImage).toURI().toString()));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    /**
     * Permet de récupérer le lien de l'image
     * @return
     */
    public String getPathImage() {
        return pathImage;
    }

    /**
     * Permet d'affecter un nouveau lien d'image
     * @param pathImage
     */
    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }


    /**
     * Permet de réucupérer les coordonnées de l'image
     * @return
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Permet d'affecter des coordonnées à l'image
     * @param coordinate
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        imageView.setX(coordinate.getX());
        imageView.setY(coordinate.getY());
    }

    /**
     * Permet de récupérer l'ImageView associé à notre image
     * @return
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Permet d'affecter une ImageView à notre image
     * @param pathImage
     */
    public void setImageView(String pathImage) {
        this.pathImage = pathImage;
        this.imageView.setImage(new Image(new File(pathImage).toURI().toString()));
    }

    /**
     * Permet de redimensionné l'image
     * @param width
     * @param height
     */
    public void setSize(double width, double height) {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }
}
