package main.java.model;


public class Spaceship extends MovingObject{
	
	private String powerUp;
	private static final double MAXSPEED = 4.0;


	public Spaceship(Point2D position) {
		setPosition(position.getX(), position.getY());
		speed = 0;
	}
	@Override
	public void move() {
		if(getPosition().getX()>25 && getPosition().getX()<540) {
			setPosition(getPosition().getX() + speed, getPosition().getY());
		}else {
			if(getPosition().getX()<=25) {
				setPosition(30, getPosition().getY());
			}else {
				setPosition(535, getPosition().getY());
			}
		}
	}

	public void moveRight() {
//		setPosition(getPosition().getX() + 2, getPosition().getY());
		speed = MAXSPEED;
//		System.out.println(speed);
	}
	public void stopMove() {
		speed = 0;
	}

	public void moveLeft() {
//		setPosition(getPosition().getX() - 2, getPosition().getY());
		speed = -MAXSPEED;
	}

}
