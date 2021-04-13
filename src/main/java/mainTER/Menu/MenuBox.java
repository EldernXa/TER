package mainTER.Menu;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MenuBox  extends VBox {

    /**
     * Constructor to create the VBox
     * @param items is the list of items that VBox will contains
     */
    public MenuBox(MenuItem... items){
        getChildren().add(createSeparator());
        for(MenuItem item : items){
            getChildren().addAll(item,createSeparator());
        }
    }

    /**
     * it's the separator between each item
     * @return
     */
    private Line createSeparator() {
        Line sep = new Line();
        sep.setEndX(200);
        sep.setStroke(Color.DARKGREY);
        return sep;
    }
}
