package mainTER.MapPackage;

import javafx.scene.Node;
import mainTER.Tools.Coordinate;

public abstract class MapFieldForm {

    private Coordinate coordinate;
    private double width;
    private double height;



    public MapFieldForm(Coordinate coordinate, double width, double height) {
        this.coordinate = coordinate;
        this.width = width;
        this.height = height;

    }


    public abstract Node getAppropriateNode();





}
