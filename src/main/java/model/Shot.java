package main.java.model;

public class Shot extends MovingObject {

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
}
