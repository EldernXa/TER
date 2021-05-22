package mainTER.Tools;

/**
 * Coordinate with a X and Y
 */
public class Coordinate {

    private double x;
    private double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return X
     */
    public double getX() {
        return x;
    }

    /**
     * Set the x
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }
    /**
     *
     * @return Y
     */
    public double getY() {
        return y;
    }
    /**
     * Set the y
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
