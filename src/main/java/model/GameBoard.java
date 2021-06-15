package main.java.model;

import javafx.util.converter.ShortStringConverter;
import main.java.view.UserInterface;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GameBoard {

	private Dimension2D size;
	private String background;
	private UserInterface ui;

	// enables measuring time for minimum time span between shots
	private int frameCounter;
	private static final int FPS = 25;
	private static final int RECHARGE_TIME = 15;

	private final Player currentPlayer;
	private Player[] leaderBoard;

	private List<Alien> aliens;
	private List<Shot> playerShots;
	private List<Shot> alienShots;

	private boolean running;
	private boolean alienLeft;

	public GameBoard(int width, int height) {
		this.size = new Dimension2D(width, height);
		this.frameCounter = 0;

		this.currentPlayer = new Player(
				new Spaceship(new Point2D(this.size.getWidth() / 2, this.size.getHeight() - 100)));

		aliens = new LinkedList<>();
		playerShots = new LinkedList<>();
		alienShots = new LinkedList<>();
		
		//direction of aliens is RIGHT at the start of the game 
		alienLeft = false;
		//inital aliens
		//aliens.add(new Alien(new Point2D(100, 25)));
		for(int y = 25; y < 100; y+=50) {
			for(int x = 100; x < (size.getWidth()-100); x+=50) {
				aliens.add(new Alien(new Point2D(x, y)));
			}
		}
		
	}

	public void update() {
		this.frameCounter++;
		moveShots();
		getSpaceship().move();
		moveAliens();
	}

	public boolean isRunning() {
		return this.running;
	}

	public void startGame() {
		this.running = true;
	}

	public void stopGame() {
		this.running = false;
	}

	public void moveShots() {
		for (int i = playerShots.size() - 1; i >= 0; i--) {
			Shot playershot = playerShots.get(i);
			if (playershot.getPosition().getY() <= 0) {
				playerShots.remove(i);
			} else {
				System.out.println(playershot.getPosition().toString());
				playershot.move();
			}
		}
	}

	public void moveAliens() {
		if(getLeastYPosition() >= size.getHeight() - 25) {
			// Game is lost!
		}
		// aliens are too far left -> move down and right
		else if (getMinXPosition() <= 25) {
			Iterator<Alien> iterator = aliens.iterator();
			while(iterator.hasNext()) {
				Alien alien = iterator.next();
				alien.moveDown();
				alien.moveRight();
			}
			alienLeft = false;
		} // aliens are too far right -> move down and left
		else if (getMaxXPosition() >= size.getWidth() - 50) {
			Iterator<Alien> iterator = aliens.iterator();
			while(iterator.hasNext()) {
				Alien alien = iterator.next();
				alien.moveDown();
				alien.moveLeft();
			}
			alienLeft = true;
		} // aliens are on their way to the left -> move left
		else if (alienLeft == true) {
			Iterator<Alien> iterator = aliens.iterator();
			while(iterator.hasNext()) {
				Alien alien = iterator.next();
				alien.moveLeft();
			}
		} // aliens are on their way to the right -> move right
		else {
			Iterator<Alien> iterator = aliens.iterator();
			while(iterator.hasNext()) {
				Alien alien = iterator.next();
				alien.moveRight();
			}
		}
	}

	private int getMinXPosition() {
		// save min x position to x
		Iterator<Alien> iterator = aliens.iterator();
		double x = size.getWidth();
		while (iterator.hasNext()) {
			Alien alien = iterator.next();
			if (alien.getPosition().getX() < x)
				x = alien.getPosition().getX();
		}
		return (int) x;
	}

	private int getMaxXPosition() {
		// save max x position to x
		Iterator<Alien> iterator = aliens.iterator();
		double x = 0;
		while (iterator.hasNext()) {
			Alien alien = iterator.next();
			if (alien.getPosition().getX() > x)
				x = alien.getPosition().getX();
		}
		return (int) x;
	}
	
	private int getLeastYPosition() {
		// save min y position to y
		Iterator<Alien> iterator = aliens.iterator();
		double y = 0;
		while (iterator.hasNext()) {
			Alien alien = iterator.next();
			if (alien.getPosition().getY() > y)
				y = alien.getPosition().getY();
		}
		return (int) y;
	}
	
	/*private LinkedList<Alien> initalizeAliens() {
		List<Alien> initializedAliens = new LinkedList<Alien>();
		initializedAliens.add(new Alien(new Point2D(100, 25)));
		return (LinkedList<Alien>) initializedAliens;
	}*/

	public void steerRight() {
		currentPlayer.getSpaceship().moveRight();
	}

	public void steerLeft() {
		getSpaceship().moveLeft();
	}

	public void stopSteer() {
		getSpaceship().stopMove();
	}

	public void shoot() {
		if (playerShots.size() < 5 && frameCounter > RECHARGE_TIME) {
			playerShots.add(new Shot(getSpaceship().getPosition()));
			this.frameCounter = 0;
		}
	}

	public Spaceship getSpaceship() {
		return this.currentPlayer.getSpaceship();
	}

	public List<Alien> getAliens() {
		return aliens;
	}

	public List<Shot> getPlayerShots() {
		return playerShots;
	}

	public List<Shot> getAlienShots() {
		return alienShots;
	}

	public Dimension2D getSize() {
		return this.size;
	}

	public void setUi(UserInterface ui) {
		this.ui = ui;
	}

	public UserInterface getUi() {
		return ui;
	}
}
