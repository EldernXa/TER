package mainTER.Tools;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestImageViewSizePos {

    private ImageViewSizePos imageViewSizePos;
    private ImageViewSizePos imgViewSizePos;
    private final String pathImage = "/mainTER/CharacterGameplay/Logo/Paladin.png";
    private final Coordinate coordinate = new Coordinate(1250,0);
    private final int width = 50;
    private final int height = 50;

    TestImageViewSizePos(){
        JFrame frame = new JFrame("");
        JFXPanel jfxPanel = new JFXPanel();
        frame.add(jfxPanel);
    }

    @BeforeEach
    public void initVar(){
        imageViewSizePos = new ImageViewSizePos(pathImage, coordinate);
        imgViewSizePos = new ImageViewSizePos(pathImage, width, height, coordinate);
    }

    @AfterEach
    public void deleteVar(){
        imageViewSizePos = null;
    }

    @Test
    public void testGettingPathImage(){
        assertEquals(pathImage, imageViewSizePos.getPathImage());
    }

    @Test
    public void testGettingPathImageIsWrong(){
        assertNotEquals(pathImage+"/", imageViewSizePos.getPathImage());
    }

    @Test
    public void testGettingBeforeAndAfterSettingPathImage(){
        assertEquals(pathImage, imageViewSizePos.getPathImage());
        imageViewSizePos.setPathImage(pathImage+"/");
        assertNotEquals(pathImage, imageViewSizePos.getPathImage());
        assertEquals(pathImage+"/", imageViewSizePos.getPathImage());
    }

    @Test
    public void testGetCoordinate(){
        assertEquals(coordinate.getX(), imageViewSizePos.getCoordinate().getX());
        assertEquals(coordinate.getY(), imageViewSizePos.getCoordinate().getY());
        assertEquals(coordinate.getX(), imageViewSizePos.getImageView().getX());
        assertEquals(coordinate.getY(), imageViewSizePos.getImageView().getY());
    }

    @Test
    public void testGettingBeforeAndAfterSettingCoordinate(){
        assertEquals(coordinate.getX(), imageViewSizePos.getCoordinate().getX());
        assertEquals(coordinate.getY(), imageViewSizePos.getCoordinate().getY());
        assertEquals(coordinate.getX(), imageViewSizePos.getImageView().getX());
        assertEquals(coordinate.getY(), imageViewSizePos.getImageView().getY());
        imageViewSizePos.setCoordinate(new Coordinate(coordinate.getX()+1, coordinate.getY()+1));
        assertEquals(coordinate.getX()+1, imageViewSizePos.getCoordinate().getX());
        assertEquals(coordinate.getY()+1, imageViewSizePos.getCoordinate().getY());
        assertEquals(coordinate.getX()+1, imageViewSizePos.getImageView().getX());
        assertEquals(coordinate.getY()+1, imageViewSizePos.getImageView().getY());
    }

    @Test
    public void testConstructorWithHeightAndWidth(){
        assertEquals(width, imgViewSizePos.getImageView().getFitWidth());
        assertEquals(height, imgViewSizePos.getImageView().getFitHeight());
    }

    @Test
    public void testSetForcedSize(){
        assertEquals(width, imgViewSizePos.getImageView().getFitWidth());
        assertEquals(height, imgViewSizePos.getImageView().getFitHeight());
        imgViewSizePos.setSize(width+50, height+50);
        assertEquals(width+50, imgViewSizePos.getImageView().getFitWidth());
        assertEquals(height+50, imgViewSizePos.getImageView().getFitHeight());
    }

}
