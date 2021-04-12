package mainTER.Menu;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;



public class Title extends StackPane {
    public Title(String name){


        Text text = new Text(name);
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,40));


        setAlignment(Pos.TOP_CENTER);
        getChildren().addAll(text);
    }
}
