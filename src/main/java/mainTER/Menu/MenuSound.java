package mainTER.Menu;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mainTER.Music.Music;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

public class MenuSound {

    private final StackPane pane = new StackPane();
    private final Scene scene = new Scene(pane, Screen.getPrimary().getVisualBounds().getWidth()/2,Screen.getPrimary().getVisualBounds().getHeight()/2);
    private final Slider volumeSlider = new Slider();
    private final HBox son = new HBox(10);
    //private final ImageViewSizePos revenir = new ImageViewSizePos("./data/Logos/return.png",50,50,new Coordinate(2,2));
    private final Label labelSon = new Label("Régler le niveau du son :");


    public MenuSound(Stage stage) {
        pane.setStyle("-fx-background-color: lightgray");
        setVolumeSlider();
        styleLabelSon();
        son.getChildren().addAll(labelSon,volumeSlider);
        son.setAlignment(Pos.CENTER_LEFT);
        pane.getChildren().add(son);
        //setRevenir(stage,sceneBack);
       // setdisable(game);
        //pane.getChildren().add(revenir.getImageView());
        //StackPane.setAlignment(revenir.getImageView(),Pos.TOP_LEFT);
    }


    /*public void setdisable(Game game){
        if(game.getListMusiques().isEmpty()){
            getVolumeSlider().setValue(0);
            getVolumeSlider().setDisable(true);
        }
    }*/
    public void setVolumeSlider(){
        volumeSlider.setValue(Music.mediaPlayer.getVolume() *100);
        volumeSlider.valueProperty().addListener(observable -> Music.mediaPlayer.setVolume(volumeSlider.getValue()/100));
        volumeSlider.setMaxSize(70,70);
        volumeSlider.setOrientation(Orientation.HORIZONTAL);
        volumeSlider.setStyle("-fx-control-inner-background: gray!important;");
        //Pane thumb = (Pane) volumeSlider.lookup(".thumb");
        //thumb.setStyle("-fx-background-color: #1354cb!important;");
        //volumeSlider.getStyleClass().add("slider");
        StackPane.setAlignment(volumeSlider, Pos.CENTER_RIGHT);

    }
    public Slider getVolumeSlider() {
        return volumeSlider;
    }
    public void styleLabelSon(){
        labelSon.setFont(Font.font("Arial",15));
        labelSon.setStyle("-fx-font-weight: bold");
        labelSon.setTextFill(Color.BLACK);
    }


    public Scene getScene() {
        return scene;
    }
}
