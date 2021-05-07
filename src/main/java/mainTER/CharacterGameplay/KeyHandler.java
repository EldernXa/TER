package mainTER.CharacterGameplay;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainTER.DBManage.ControlsDBManager;
import mainTER.MapPackage.SwitchCharacter;
import mainTER.Menu.MenuPause;
import mainTER.Menu.SkillsMenu;
import mainTER.exception.ControlsDataGetException;

import java.util.ArrayList;

public class KeyHandler {
    private final Stage stage;
    private final DisplayCharacter displayCharacter;
    private final ArrayList<Character> listCharacter;
    private final SwitchCharacter sc ;
    private String switchUp;
    private String switchDown;


    public KeyHandler(Stage stage, DisplayCharacter displayCharacter, ArrayList<Character> listCharacter, SwitchCharacter sc) {
        this.stage = stage;
        this.displayCharacter = displayCharacter;
        this.listCharacter = listCharacter;
        this.sc = sc;
        try {
            ControlsDBManager controlsDBManager = new ControlsDBManager();
            switchDown = controlsDBManager.getSwitchDown();
            switchUp = controlsDBManager.getSwitchUp();
        } catch (ControlsDataGetException e) {
            e.printStackTrace();
        }
    }

    public void handleEvend( KeyEvent event2){
        String event = event2.getCode().getChar().toLowerCase();
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
        else if(event2.getCode() == KeyCode.ESCAPE) {

            Stage stagea = new Stage(StageStyle.TRANSPARENT);
            MenuPause menuPause = new MenuPause(stage,stagea);
            stagea.initOwner(stage);
            stagea.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(menuPause.getVbox(), Color.TRANSPARENT);
            stagea.setScene(scene);
            stagea.show();
            scene.addEventHandler(KeyEvent.KEY_PRESSED, event3 -> {
                if(event3.getCode() == KeyCode.ESCAPE)
                    stagea.close();
            });

        }
        else if(event2.getCode() == KeyCode.TAB){



            Stage stagep = new Stage(StageStyle.TRANSPARENT);
            SkillsMenu skillsMenu = new SkillsMenu(stage,listCharacter,displayCharacter.getCharacter());
            stagep.initOwner(stage);
            stagep.initModality(Modality.APPLICATION_MODAL);
            stagep.setScene(skillsMenu.getScene());
            stagep.show();
            skillsMenu.getScene().addEventHandler(KeyEvent.KEY_PRESSED, event3 -> {
                if(event3.getCode() == KeyCode.TAB)
                    stagep.close();
            });

        }
    }
}
