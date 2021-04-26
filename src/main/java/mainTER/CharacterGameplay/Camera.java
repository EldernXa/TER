package mainTER.CharacterGameplay;

import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import mainTER.MapPackage.SwitchCharacter;
import mainTER.Tools.Coordinate;

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


    public Camera(Scene scene, DisplayCharacter displayCharacter, SwitchCharacter sc, ArrayList<Character> listCharacter, double scalingValue, ImageView background) {
        this.scene = scene;
        this.displayCharacter = displayCharacter;
        this.sc = sc;
        this.listCharacter = listCharacter;
        this.scalingValue = scalingValue;
        this.background = background;
        camera = new PerspectiveCamera();
        scene.setCamera(camera);
        moveCamera();
    }


    void moveCamera() {

        camera.translateXProperty().set(displayCharacter.getCurrentCoordinateOfTheCharacter().getX() * scalingValue - Screen.getPrimary().getBounds().getWidth() / 2);

        //A voir pour les Y quand la map monte ou decend


        scene.addEventHandler(KeyEvent.KEY_PRESSED, event2 -> {

            switch (event2.getCode()) {
                case D:
                    coordCamera();
                    break;

                case Q:
                    if (camera.getTranslateX() > 1 ) {
                        coordCamera();
                    }
                    break;
                case A:
                    int k = 0;
                    for (int i = 0; i < listCharacter.size(); i++) {
                        if (listCharacter.get(i) == displayCharacter.getCharacter()) {
                            k = i;
                        }
                    }
                    displayCharacter.setCharacter(listCharacter.get((k + 1) % listCharacter.size()));
                    sc.changeToUp();
                    break;

                case E:
                    k = 0;
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
                    break;
            }
        });
    }

    private void coordCamera() {
        double translateValue = displayCharacter.getCurrentCoordinateOfTheCharacter().getX() * scalingValue - Screen.getPrimary().getBounds().getWidth() / 2;
        double xCamera = camera.getTranslateX();


        camera.translateXProperty().set(xCamera + (translateValue - xCamera) * 0.1);
        if (displayCharacter.getCurrentCoordinateOfTheCharacter().getX() + Screen.getPrimary().getBounds().getWidth()/2 >background.getImage().getWidth()) {
            translateValue = background.getImage().getWidth() - 1;
            camera.translateXProperty().set(xCamera + (translateValue - xCamera) * 0.1);
        }
        else if (camera.getTranslateX() < 1) {
            camera.translateXProperty().set(1);
        }
        else {

            double switchCharacterValue = displayCharacter.getCurrentCoordinateOfTheCharacter().getX()  -  Screen.getPrimary().getBounds().getWidth() / 2.8;
            double xSc = sc.getTranslateX();
            sc.setTranslateX(xSc + (switchCharacterValue - xSc)*0.1);
        }


    }


}
