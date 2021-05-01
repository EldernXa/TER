package mainTER.Tools;

import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;

public class TestCharacterMovementAndDisplayManagement {

    private final Pane pane = new Pane();
    private final CharacterMovementAndDisplayManagement characterMovementAndDisplayManagement = new CharacterMovementAndDisplayManagement(pane);
    private ImageView imgView;
    private final double xCoordinate = 15;
    private final double yCoordinate = 25;

    private TestCharacterMovementAndDisplayManagement(){
        JFrame frame = new JFrame("");
        JFXPanel jfxPanel = new JFXPanel();
        frame.add(jfxPanel);
    }

    @BeforeEach
    public void initVar(){
        ClassLoader classLoader = getClass().getClassLoader();
        try{
            File file = Paths.get(Objects.requireNonNull(Objects.requireNonNull(classLoader.getResource("mainTER/dragon1.png")).toURI())).toFile();
            imgView = new ImageView(new Image(file.toURI().toString()));
            characterMovementAndDisplayManagement.displayNode(imgView, xCoordinate, yCoordinate);
        }catch(Exception exception){
            fail();
        }
    }

    @Test
    public void testGettingBeforeAndAfterSettingCoordinate(){
        Coordinate rightCoordinate = characterMovementAndDisplayManagement.calculateRightPosition(new Coordinate(xCoordinate, yCoordinate));
        assertEquals(rightCoordinate.getX(), characterMovementAndDisplayManagement.getCoordinateOfTheActualImg().getX());
        assertEquals(rightCoordinate.getY(), characterMovementAndDisplayManagement.getCoordinateOfTheActualImg().getY());
        characterMovementAndDisplayManagement.displayNode(imgView, xCoordinate+50, yCoordinate+50);
        rightCoordinate = characterMovementAndDisplayManagement.calculateRightPosition(new Coordinate(xCoordinate+50, yCoordinate+50));
        assertEquals(rightCoordinate.getX(), characterMovementAndDisplayManagement.getCoordinateOfTheActualImg().getX());
        assertEquals(rightCoordinate.getY(), characterMovementAndDisplayManagement.getCoordinateOfTheActualImg().getY());
    }

    @Test
    public void testGettingRightCoordinate(){
        Coordinate rightCoordinate = characterMovementAndDisplayManagement.calculateRightPosition(new Coordinate(xCoordinate, yCoordinate));
        assertEquals(rightCoordinate.getX(), characterMovementAndDisplayManagement.getCoordinateOfTheActualImg().getX());
        assertEquals(rightCoordinate.getY(), characterMovementAndDisplayManagement.getCoordinateOfTheActualImg().getY());
    }

    @Test
    public void testGettingTheRightX(){
        assertEquals(xCoordinate - imgView.getImage().getWidth()* characterMovementAndDisplayManagement.getMultiplyX(),
                characterMovementAndDisplayManagement.getCoordinateOfTheActualImg().getX());
    }

}
