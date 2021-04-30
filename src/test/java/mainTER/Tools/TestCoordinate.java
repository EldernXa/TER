package mainTER.Tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCoordinate {

    private Coordinate coordinate;
    private final double coordinateX = 50;
    private final double coordinateY = 63;
    private final int step = 50;

    @BeforeEach
    public void initTest(){
        coordinate = new Coordinate(coordinateX, coordinateY);
    }

    @Test
    public void testGettingCoordinate(){
        assertEquals(coordinateX, coordinate.getX());
        assertEquals(coordinateY, coordinate.getY());
    }

    @Test
    public void testGettingBeforeAndAfterSettingForCoordinate(){
        assertEquals(coordinateX, coordinate.getX());
        assertEquals(coordinateY, coordinate.getY());
        coordinate.setX(coordinateX+step);
        coordinate.setY(coordinateY+step);
        assertEquals(coordinateX+step, coordinate.getX());
        assertEquals(coordinateY+step, coordinate.getY());
    }

}
