package mainTER;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static mainTER.Menu.MainMenu.createContent;

class AppTest extends ApplicationTest {


    @Override
    public void start(Stage stage) {
        var scene = new Scene(createContent(stage));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() throws Exception{
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }




}