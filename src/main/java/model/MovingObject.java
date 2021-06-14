package main.java.model;

public abstract class MovingObject {

    protected static final int DEFAULT_WIDTH = 25;
    protected static final int DEFAULT_HEIGHT = 25;


    private Point2D position;
    protected double speed;

    private String icon;

    private Dimension2D size = new Dimension2D(DEFAULT_WIDTH, DEFAULT_HEIGHT);


    public abstract void move();

    public Point2D getPosition() {
        return this.position;
    }

    public void setPosition(double x, double y) {
        this.position = new Point2D(x, y);
    }

    public Dimension2D getSize() {
        return this.size;
    }


}
