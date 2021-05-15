package mainTER.Menu;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import mainTER.LoadOfFXML;


public class Title extends StackPane {

    @FXML
    private Text textTitle;

    /**
     * Constructor of the title
     * @param name is the title name
     */
    public Title(String name){
        LoadOfFXML.loadFXML("/mainTER/Menu/FXML/Title.fxml", this, this);
        textTitle.setText(name);
    }
}
