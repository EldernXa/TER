package mainTER.Menu;


import javafx.stage.Stage;


import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import mainTER.Music.Music;
import mainTER.Tools.ReturnBack;

public class MenuSound {

    private final StackPane pane = new StackPane();
    private final Scene scene = new Scene(pane, Screen.getPrimary().getVisualBounds().getWidth()/2,Screen.getPrimary().getVisualBounds().getHeight()/2);
    private final Slider volumeSlider = new Slider();
    private final Label labelSon = new Label("Adjust the sound level :");


    public MenuSound(Stage stage) {
        pane.setStyle("-fx-background-color: lightgray");
        setVolumeSlider();
        styleLabelSon();
        HBox son = new HBox(10);
        son.getChildren().addAll(labelSon,volumeSlider);
        son.setAlignment(Pos.CENTER_LEFT);
        pane.getChildren().add(son);
        ReturnBack.setRevenir(stage,stage.getScene(),pane);

    }


    /**
     * set the volume slider
     */

    public void setVolumeSlider(){
        try {
            volumeSlider.setValue(Music.mediaPlayer.getVolume() *100);
            volumeSlider.valueProperty().addListener(observable -> Music.mediaPlayer.setVolume(volumeSlider.getValue()/100));
            volumeSlider.setMaxSize(70,70);
            volumeSlider.setOrientation(Orientation.HORIZONTAL);
            volumeSlider.setStyle("-fx-control-inner-background: gray!important;");
            StackPane.setAlignment(volumeSlider, Pos.CENTER_RIGHT);
        }catch (Exception e){
            System.out.println("Aucune musique n'est lancée");
        }


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
