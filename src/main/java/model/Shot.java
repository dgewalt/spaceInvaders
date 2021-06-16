package main.java.model;

public class Shot extends MovingObject {

	private static final int SHOT_WIDTH = 5;
	private static final int SHOT_HEIGHT = 20;

	public Shot(Point2D position){
		setPosition(position.getX(), position.getY());
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		setPosition(getPosition().getX(), getPosition().getY() - 5);
	}
	
	
	public void moveAlienShot() {
		setPosition(getPosition().getX(), getPosition().getY() + 4);
	}

	public Dimension2D getSize() {
		return new Dimension2D(SHOT_WIDTH, SHOT_HEIGHT);
	}
}
