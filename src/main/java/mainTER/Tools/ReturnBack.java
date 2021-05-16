package mainTER.Tools;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ReturnBack {


    /**
     * set the return to back for menus
     * @param stage
     * @param sceneBack
     * @param pane
     */
    public static void setRevenir(Stage stage, Scene sceneBack, Pane pane){
        ImageViewSizePos revenir = new ImageViewSizePos("src/main/resources/mainTER/Tools/returnhover.png",50,50,new Coordinate(0,0));


        StackPane.setAlignment(revenir.getImageView(), Pos.TOP_LEFT);

        pane.getChildren().add(revenir.getImageView());
        enterRevenir(revenir);
        exitRevenir(revenir);
        clickRevenir(stage,sceneBack,revenir);
        setTooltip(revenir);

    }


    public static void enterRevenir(ImageViewSizePos revenir){
        revenir.getImageView().setOnMouseEntered(mouseEvent -> revenir.setImageView("src/main/resources/mainTER/Tools/return.png"));
    }
    public static void exitRevenir(ImageViewSizePos revenir){
        revenir.getImageView().setOnMouseExited(mouseEvent -> revenir.setImageView("src/main/resources/mainTER/Tools/returnhover.png"));
    }
    public static void clickRevenir(Stage stage,Scene sceneback,ImageViewSizePos revenir){
        revenir.getImageView().setOnMouseClicked(mouseEvent -> stage.setScene(sceneback));

    }
    public static void setTooltip(ImageViewSizePos revenir){
        Tooltip tooltip_revenir=new Tooltip("Return back");
        tooltip_revenir.setStyle(" -fx-background-color: gray;");
        tooltip_revenir.setShowDelay(new Duration(0));
        Tooltip.install(revenir.getImageView(),tooltip_revenir);
    }

}
