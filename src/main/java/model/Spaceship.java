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
		if(getPosition().getX()>5 && getPosition().getX()<570) {
			setPosition(getPosition().getX() + speed, getPosition().getY());
		}else {
			if(getPosition().getX()<=5) {
				setPosition(10, getPosition().getY());
			}else {
				setPosition(565, getPosition().getY());
			}
		}
//		speed = speed >= 1 ? speed - 0.4 : speed <= -1 ? speed + 0.4 : 0;
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
