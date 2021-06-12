package main.java.model;

public class Spaceship extends MovingObject{
	
	private String powerUp;


	public Spaceship(Point2D position) {
		setPosition(position.getX(), position.getY());
	}
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

	public void moveRight() {
		setPosition(getPosition().getX() + 2, getPosition().getY());
	}

	public void moveLeft() {
		setPosition(getPosition().getX() - 2, getPosition().getY());
	}
	

}
