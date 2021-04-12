package mainTER.Menu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import mainTER.LoadOfFXML;
import mainTER.Version;

public class MainMenu extends VBox {

    @FXML
    private Label lblVersion;

    public MainMenu(){
        LoadOfFXML.loadFXML("/mainTER/Menu/MainMenu.fxml", this, this);
        lblVersion.setText(Version.getVersion());
    }

}
