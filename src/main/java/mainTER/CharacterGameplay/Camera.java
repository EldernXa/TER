package mainTER.CharacterGameplay;

import javafx.geometry.Pos;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainTER.DBManage.ControlsDBManager;
import mainTER.MapPackage.SwitchCharacter;
import mainTER.Menu.MenuPause;
import mainTER.Tools.Coordinate;
import mainTER.exception.ControlsDataGetException;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Camera {
    private Scene scene;
    private javafx.scene.Camera camera;
    private DisplayCharacter displayCharacter;
    private SwitchCharacter sc;
    private ArrayList<Character> listCharacter;
    private double scalingValue;
    private ImageView background;
    private String right;
    private String left;
    private String down;
    private String up;
    private String switchUp;
    private String switchDown;
    Stage stage;


    public Camera(Scene scene, DisplayCharacter displayCharacter, SwitchCharacter sc, ArrayList<Character> listCharacter, double scalingValue, ImageView background, Stage stage) {
        this.scene = scene;
        this.displayCharacter = displayCharacter;
        this.sc = sc;
        this.listCharacter = listCharacter;
        this.scalingValue = scalingValue;
        this.background = background;
        right = "";
        left = "";
        switchUp = "";
        switchDown = "";
        this.stage = stage;


        ControlsDBManager controlsDBManager = new ControlsDBManager();
        try {
            this.right = controlsDBManager.getRight();
            this.left = controlsDBManager.getLeft();
            this.switchDown = controlsDBManager.getSwitchDown();
            this.switchUp = controlsDBManager.getSwitchUp();
        } catch (ControlsDataGetException e) {
            e.printStackTrace();


        }

        camera = new PerspectiveCamera();
        scene.setCamera(camera);
        moveCamera();
    }

    public void moveOnlyCamera(){
        coordCamera();
    }



    private void moveCamera() {

        initTranslateCamera();

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event2 -> {

            String event = event2.getCode().getChar().toLowerCase();
            System.out.println(event2);
             if (event.equals(switchUp)) {
                int k = 0;
                for (int i = 0; i < listCharacter.size(); i++) {
                    if (listCharacter.get(i) == displayCharacter.getCharacter()) {
                        k = i;
                    }
                }
                displayCharacter.setCharacter(listCharacter.get((k + 1) % listCharacter.size()));
                sc.changeToUp();

            } else if (event.equals(switchDown)) {
                int k = 0;
                for (int i = 0; i < listCharacter.size(); i++) {
                    if (listCharacter.get(i) == displayCharacter.getCharacter()) {
                        k = i;
                    }
                }
                if (k == 0) {
                    displayCharacter.setCharacter(listCharacter.get(listCharacter.size() - 1));
                } else {
                    displayCharacter.setCharacter(listCharacter.get((k - 1) % listCharacter.size()));
                }
                sc.changeToDown();
            }
             else if(event2.getCode() == KeyCode.ESCAPE){
                 System.out.println("on passe ici");

                 Stage stagea = new Stage(StageStyle.TRANSPARENT);
                 MenuPause menuPause = new MenuPause(stagea);
                 stagea.initOwner(stage);
                 stagea.initModality(Modality.APPLICATION_MODAL);
                 stagea.setScene(new Scene(menuPause.getVbox(), Color.TRANSPARENT));
                 stagea.show();
                 //StackPane.setAlignment(menuPause.getPane(), Pos.CENTER);

             }
        });
    }

    private void initTranslateCamera() {

        camera.translateXProperty().set(displayCharacter.getCurrentCoordinateOfTheCharacter().getX() * scalingValue - Screen.getPrimary().getBounds().getWidth() / 2);
        double translateXvalue = (background.getImage().getWidth() - Screen.getPrimary().getBounds().getWidth() / scalingValue) * scalingValue;
        sc.setTranslateX( displayCharacter.getCurrentCoordinateOfTheCharacter().getX() - Screen.getPrimary().getBounds().getWidth() / 2/scalingValue);


        if (camera.getTranslateX() > translateXvalue) {
            camera.translateXProperty().set(translateXvalue);
            sc.setTranslateX(translateXvalue/scalingValue);


        } else if (camera.getTranslateX() < 1) {
            camera.translateXProperty().set(1);
            sc.setTranslateX(1);

        }

        camera.translateYProperty().set(displayCharacter.getCurrentCoordinateOfTheCharacter().getY() * scalingValue - Screen.getPrimary().getBounds().getHeight() / 2);
        double translateYvalue = (background.getImage().getHeight() - Screen.getPrimary().getBounds().getHeight() / scalingValue) * scalingValue;
        sc.setTranslateY( displayCharacter.getCurrentCoordinateOfTheCharacter().getY() - Screen.getPrimary().getBounds().getHeight() / 2);

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

       // background.setX(background.getX()+displayCharacter.getCurrentCoordinateOfTheCharacter().getX());


        camera.translateXProperty().set(xCamera + (translateValue - xCamera) * 0.1);
        camera.translateYProperty().set(yCamera + (translateYvalue - yCamera) * 0.1);
        if (camera.getTranslateX() > (background.getImage().getWidth() - Screen.getPrimary().getBounds().getWidth() / scalingValue) * scalingValue) {
            camera.translateXProperty().set(xCamera + (xCamera - xCamera) * 0.1);

        } else if (camera.getTranslateX() < 1) {
            camera.translateXProperty().set(1);
            sc.translateXProperty().set(1);
        } else {


            double xSc = sc.getTranslateX();
            sc.setTranslateX(xSc + (translateValue/scalingValue - xSc) * 0.1);
        }


        if (camera.getTranslateY() > (background.getImage().getHeight() - Screen.getPrimary().getBounds().getHeight() / scalingValue) * scalingValue) {
            camera.translateYProperty().set(yCamera + (yCamera - yCamera) * 0.1);
        } else if (camera.getTranslateY() < 1) {
            camera.translateYProperty().set(0);
            sc.translateYProperty().set(1);
        } else {
            double ySc = sc.getTranslateY();
            sc.setTranslateY(ySc + (translateYvalue/scalingValue - ySc) * 0.1);
        }


    }


}
