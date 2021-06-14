package main.java.model;

public class Alien extends MovingObject {

	public Alien(Point2D position) {
		setPosition(position.getX(), position.getY());
	}
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		//Logik -> je nachdem wo Aliens sind alle links, rechts oder unten
		//setPosition(getPosition().getX(), getPosition().getY() - 5);
	}
	
	public void dropItem() {
		//create new Item here
	}
	
	public int getLeastPosition() {
		//irgendwie brauche ich die Position des Aliens ganz links und die Position des Aliens ganz rechts 
		//Damit kann ich sagen, wann rechts moven, wann down moven oder wann links moven, am besten relativ zur Spielfeldgroesse
		return 0;
	}
}
