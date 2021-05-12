package mainTER.CharacterGameplay;

import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.*;
import mainTER.MapPackage.SwitchCharacter;

import static mainTER.Menu.MenuItem.timerLabel;

public class Camera {
    private Scene scene;
    private javafx.scene.Camera camera;
    private DisplayCharacter displayCharacter;
    private SwitchCharacter sc;
    private double scalingValue;
    private ImageView background;

    private boolean isActivated = false;
    Stage stage;



    public Camera(Scene scene, DisplayCharacter displayCharacter, SwitchCharacter sc, double scalingValue, ImageView background, Stage stage) {
        this.scene = scene;
        this.displayCharacter = displayCharacter;
        this.sc = sc;
        this.scalingValue = scalingValue;
        this.background = background;
        this.stage = stage;




        camera = new PerspectiveCamera();
        scene.setCamera(camera);
        moveCamera();
    }


    public void moveOnlyCamera() {
        if (!isActivated) {
            coordCamera();
        }


    }


    private void moveCamera() {

        initTranslateCamera();

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event2 -> {

            coordCamera();
            isActivated = true;
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, event2 -> isActivated = false);

    }

    private void initTranslateCamera() {

        camera.translateXProperty().set(displayCharacter.getCurrentCoordinateOfTheCharacter().getX() * scalingValue - Screen.getPrimary().getBounds().getWidth() / 2);
        double translateXvalue = (background.getImage().getWidth() - Screen.getPrimary().getBounds().getWidth() / scalingValue) * scalingValue;
        sc.setTranslateX(displayCharacter.getCurrentCoordinateOfTheCharacter().getX() - Screen.getPrimary().getBounds().getWidth() / 2 / scalingValue);
        timerLabel.translateXProperty().set(sc.getTranslateX() );
        if (camera.getTranslateX() > translateXvalue) {
            camera.translateXProperty().set(translateXvalue);
            sc.setTranslateX(translateXvalue / scalingValue);

            timerLabel.setTranslateX(sc.getTranslateX());

        } else if (camera.getTranslateX() < 1) {
            camera.translateXProperty().set(1);
            sc.setTranslateX(1);

            timerLabel.setTranslateX(sc.getTranslateX());

        }

        camera.translateYProperty().set(displayCharacter.getCurrentCoordinateOfTheCharacter().getY() * scalingValue - Screen.getPrimary().getBounds().getHeight() / 2);
        double translateYvalue = (background.getImage().getHeight() - Screen.getPrimary().getBounds().getHeight() / scalingValue) * scalingValue;
        sc.setTranslateY(displayCharacter.getCurrentCoordinateOfTheCharacter().getY() - Screen.getPrimary().getBounds().getHeight() / 2);
        timerLabel.setTranslateY(displayCharacter.getCurrentCoordinateOfTheCharacter().getY() - Screen.getPrimary().getBounds().getHeight() / 2 /scalingValue);
        if (camera.getTranslateY() > translateYvalue) {
            camera.translateYProperty().set(translateYvalue);
            sc.setTranslateY(translateYvalue);
            timerLabel.setTranslateY(sc.getTranslateY()+230);
        } else if (camera.getTranslateY() < 1) {
            camera.translateYProperty().set(1);
            sc.setTranslateY(1);
            timerLabel.setTranslateY(sc.getTranslateY()+230);
        }


    }


    private void coordCamera() {

        double xCamera = camera.getTranslateX();
        double yCamera = camera.getTranslateY();


        double translateValue = displayCharacter.getCurrentCoordinateOfTheCharacter().getX() * scalingValue - Screen.getPrimary().getBounds().getWidth() / 2;
        double translateYvalue = displayCharacter.getCurrentCoordinateOfTheCharacter().getY() * scalingValue - Screen.getPrimary().getBounds().getHeight() / 2;

        camera.translateXProperty().set(xCamera + (translateValue - xCamera) * 0.1);
        camera.translateYProperty().set(yCamera + (translateYvalue - yCamera) * 0.1);
        if (camera.getTranslateX() > (background.getImage().getWidth() - Screen.getPrimary().getBounds().getWidth() / scalingValue) * scalingValue) {
            camera.translateXProperty().set(xCamera + (xCamera - xCamera) * 0.1);

        } else if (camera.getTranslateX() < 1) {
            camera.translateXProperty().set(1);
            sc.translateXProperty().set(1);
            timerLabel.setTranslateX(sc.getTranslateX());
        } else {


            double xSc = sc.getTranslateX();
            sc.setTranslateX(xSc + (translateValue / scalingValue - xSc) * 0.1);
            timerLabel.setTranslateX(sc.getTranslateX());
        }


        if (camera.getTranslateY() > (background.getImage().getHeight() - Screen.getPrimary().getBounds().getHeight() / scalingValue) * scalingValue) {
            camera.translateYProperty().set(yCamera + (yCamera - yCamera) * 0.1);
        } else if (camera.getTranslateY() < 1) {
            camera.translateYProperty().set(0);
            sc.translateYProperty().set(1);
            timerLabel.setTranslateY(sc.getTranslateY()+230);
        } else {
            double ySc = sc.getTranslateY();

            sc.setTranslateY(ySc + (translateYvalue / scalingValue - ySc) * 0.1);
            timerLabel.setTranslateY(sc.getTranslateY()+230);
        }


    }


}
