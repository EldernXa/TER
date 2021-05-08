package mainTER.CharacterGameplay;

import javafx.event.EventHandler;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.*;
import mainTER.DBManage.ControlsDBManager;
import mainTER.MapPackage.SwitchCharacter;
import mainTER.Menu.MenuPause;
import mainTER.Menu.SkillsMenu;
import mainTER.Tools.CharacterMovementAndDisplayManagement;
import mainTER.Tools.Coordinate;
import mainTER.exception.ControlsDataGetException;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.MouseEvent;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

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
        //ddSystem.out.println(isActivated);
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


        if (camera.getTranslateX() > translateXvalue) {
            camera.translateXProperty().set(translateXvalue);
            sc.setTranslateX(translateXvalue / scalingValue);


        } else if (camera.getTranslateX() < 1) {
            camera.translateXProperty().set(1);
            sc.setTranslateX(1);

        }

        camera.translateYProperty().set(displayCharacter.getCurrentCoordinateOfTheCharacter().getY() * scalingValue - Screen.getPrimary().getBounds().getHeight() / 2);
        double translateYvalue = (background.getImage().getHeight() - Screen.getPrimary().getBounds().getHeight() / scalingValue) * scalingValue;
        sc.setTranslateY(displayCharacter.getCurrentCoordinateOfTheCharacter().getY() - Screen.getPrimary().getBounds().getHeight() / 2);

        if (camera.getTranslateY() > translateYvalue) {
            camera.translateYProperty().set(translateYvalue);
            sc.setTranslateY(translateYvalue);
        } else if (camera.getTranslateY() < 1) {
            camera.translateYProperty().set(1);
            sc.setTranslateY(1);
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
        } else {


            double xSc = sc.getTranslateX();
            sc.setTranslateX(xSc + (translateValue / scalingValue - xSc) * 0.1);
        }


        if (camera.getTranslateY() > (background.getImage().getHeight() - Screen.getPrimary().getBounds().getHeight() / scalingValue) * scalingValue) {
            camera.translateYProperty().set(yCamera + (yCamera - yCamera) * 0.1);
        } else if (camera.getTranslateY() < 1) {
            camera.translateYProperty().set(0);
            sc.translateYProperty().set(1);
        } else {
            double ySc = sc.getTranslateY();
            sc.setTranslateY(ySc + (translateYvalue / scalingValue - ySc) * 0.1);
        }


    }


}
