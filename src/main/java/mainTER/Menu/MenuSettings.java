package mainTER.Menu;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mainTER.Tools.Coordinate;
import mainTER.Tools.ImageViewSizePos;

import java.io.File;

public class MenuSettings {

    private final StackPane pane = new StackPane();
    private final Scene scene=  new Scene(pane, Screen.getPrimary().getVisualBounds().getWidth()/2,Screen.getPrimary().getVisualBounds().getHeight()/2);;
    private final ImageViewSizePos revenir = new ImageViewSizePos("./data/Logos/return.png",50,50,new Coordinate(2,2));;
    private static final Stage stage = new Stage();

    private Button boutonSon= new Button("Réglage son");
    //private Button boutonCommande = new Button("Réglage commandes");
    private final Label titre = new Label("Paramètres");
    VBox vBox = new VBox(10);
    MenuSound menuSound;
    //MenuParametresCommandes menuParametresCommandes;


    public MenuSettings() {
        if(!stage.isShowing()) {

            //scene.getStylesheets().add(new File("./ressources/style.css").toURI().toString());
            titre.setStyle("-fx-font-size: 30px");



            setButton();
            pane.setStyle("-fx-background-color: lightgray");
            pane.getChildren().addAll(titre,revenir.getImageView());
            //setRevenir();

            StackPane.setAlignment(revenir.getImageView(),Pos.TOP_LEFT);
            StackPane.setAlignment(titre,Pos.TOP_CENTER);

            stage.setScene(scene);
            stage.show();
        }
    }



    public void setButton(){
        boutonSon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                menuSound = new MenuSound(stage,scene);
                stage.setScene(menuSound.getScene());
            }
        });
/*
        boutonCommande.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                menuParametresCommandes = new MenuParametresCommandes(stage,scene,game);
                stage.setScene(menuParametresCommandes.getScene());
            }
        }); */
        vBox.getChildren().addAll(boutonSon);
        vBox.setAlignment(Pos.CENTER_LEFT);
        pane.getChildren().add(vBox);
    }
    public Scene getScene() {
        return scene;
    }
}
