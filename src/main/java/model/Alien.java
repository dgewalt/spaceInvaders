package main.java.model;

public class Alien extends MovingObject {

	public Alien(Point2D position) {
		setPosition(position.getX(), position.getY());
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		// muss ich die hier verwenden?
	}

	public void moveDown() {
		setPosition(getPosition().getX(), getPosition().getY() + 25);
	}

	public void moveRight() {
		setPosition(getPosition().getX() + 1, getPosition().getY());
	}

	public void moveLeft() {
		setPosition(getPosition().getX() - 1, getPosition().getY());
	}

	public void dropItem() {
		// create new Item here
	}
}
