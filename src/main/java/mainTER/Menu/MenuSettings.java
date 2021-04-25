package mainTER.Menu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class MenuSettings {

    private final StackPane pane = new StackPane();
    private final Scene scene=  new Scene(pane, Screen.getPrimary().getVisualBounds().getWidth()/2,Screen.getPrimary().getVisualBounds().getHeight()/2);
    private static final Stage stage = new Stage();


    MenuBox vbox = new MenuBox(
            new MenuItem("SOUND SETTINGS",stage),
            new MenuItem("CONTROLS SETTINGS",stage)
    );

    public MenuSettings() {
        if(!stage.isShowing()) {

            //scene.getStylesheets().add(new File("./ressources/style.css").toURI().toString());
            Label titre = new Label("Paramètres");
            titre.setStyle("-fx-font-size: 30px");

            pane.setStyle("-fx-background-color: lightgray");
            pane.getChildren().addAll(titre);
            vbox.setTranslateX(290);
            vbox.setTranslateY(170);
            vbox.setSpacing(5);
            pane.getChildren().add(vbox);



            StackPane.setAlignment(titre,Pos.TOP_CENTER);

            stage.setScene(scene);
            stage.show();
        }
    }



   /* public void setButton(){
        boutonSon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                menuSound = new MenuSound(stage);
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
        });


    }*/
    public Scene getScene() {
        return scene;
    }
}
