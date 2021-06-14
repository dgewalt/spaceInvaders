package main.java.model;

/**
 * This class defines the behavior when two moving objects collide
 */

public class Collision {
	
	private MovingObject object1;
	private MovingObject object2;
	private final boolean crash;

	public Collision(MovingObject object1, MovingObject object2) {
		this.object1 = object1;
		this.object2 = object2;
		this.crash=checkCollision();
	}
	public boolean isCrash(){return crash;}

	public boolean checkCollision(){
		Point2D p1 = object1.getPosition();
		Dimension2D d1 = object1.getSize();

		Point2D p2 = object2.getPosition();
		Dimension2D d2 = object2.getSize();

		boolean above = p1.getY() + d1.getHeight() < p2.getY();
		boolean below = p1.getY() > p2.getY() + d2.getHeight();
		boolean right = p1.getX() + d1.getWidth() < p2.getX();
		boolean left = p1.getX() > p2.getX() + d2.getWidth();

		return !above && !below && !right && !left;
	}
	//todo methode nicht relevant da kein gewinner oder verlierer existieren
	public void evaluate() {

	}
}
