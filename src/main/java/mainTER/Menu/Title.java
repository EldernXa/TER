package mainTER.Menu;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
        /*Text text = new Text(name);
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,40));


        setAlignment(Pos.TOP_CENTER);
        getChildren().addAll(text);*/
    }
}
