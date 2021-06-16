package main.java.model;

import javafx.util.converter.ShortStringConverter;
import main.java.view.UserInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameBoard {

	private Dimension2D size;
	private UserInterface ui;

	// enables measuring time for minimum time span between shots
	private int frameCounter;
	private static final int FPS = 25;
	private static final int RECHARGE_TIME = 15;

    private final Player currentPlayer;
    private Player[] leaderBoard;
    private int alienStartingCount;

	private List<Alien> aliens;
	private List<Shot> playerShots;
	private List<Shot> alienShots;

    private boolean alienLeft;
    private boolean running;
    private GameOutcome gameOutcome = GameOutcome.OPEN;

    private String alienImage;
    private String spaceshipImage;
    private String backgroundImage;

	public GameBoard(int width, int height) {
		this.size = new Dimension2D(width, height);
		this.frameCounter = 0;

		this.currentPlayer = new Player(
				new Spaceship(new Point2D(this.size.getWidth() / 2, this.size.getHeight() - 100)));

		aliens = new ArrayList<>();
		playerShots = new LinkedList<>();
		alienShots = new LinkedList<>();

        //direction of aliens is RIGHT at the start of the game
        alienLeft = false;
        //inital aliens
        for (int y = 25; y < 100; y += 50) {
            for (int x = 100; x < (size.getWidth() - 100); x += 50) {
                aliens.add(new Alien(new Point2D(x, y)));
            }
        }
        alienStartingCount = aliens.size();

		// inital aliens
		for (int y = 25; y < 100; y += 50) {
			for (int x = 100; x < (size.getWidth() - 100); x += 50) {

    }

	public void update() {
		this.frameCounter++;
		movePlayerShots();
		getSpaceship().move();
		moveAliens();
		alienShoot(); 
		moveAlienShots();
		checkCollisions
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

	public void movePlayerShots() {
		for (int i = playerShots.size() - 1; i >= 0; i--) {
			Shot playershot = playerShots.get(i);
			if (playershot.getPosition().getY() <= 0) {
				playerShots.remove(i);
			} else {
				playershot.move();
			}
		}
	}

	public void moveAlienShots() {
		for (int i = alienShots.size() - 1; i >= 0; i--) {
			Shot alienShot = alienShots.get(i);
			if (alienShot.getPosition().getY() >= 600) {
				alienShots.remove(i);
			} else {
				alienShot.moveAlienShot();
			}
		}
	}
    public void moveAliens() {
		if (getLeastYPosition() >= size.getHeight() - 25) {
			// Game is lost!
		}
		// aliens are too far left -> move down and right
		else if (getMinXPosition() <= 25) {
			Iterator<Alien> iterator = aliens.iterator();
			while (iterator.hasNext()) {
				Alien alien = iterator.next();
				alien.moveDown();
				alien.moveRight();
			}
			alienLeft = false;
		} // aliens are too far right -> move down and left
		else if (getMaxXPosition() >= size.getWidth() - 65) {
			Iterator<Alien> iterator = aliens.iterator();
			while (iterator.hasNext()) {
				Alien alien = iterator.next();
				alien.moveDown();
				alien.moveLeft();
			}
			alienLeft = true;
		} // aliens are on their way to the left -> move left
		else if (alienLeft == true) {
			Iterator<Alien> iterator = aliens.iterator();
			while (iterator.hasNext()) {
				Alien alien = iterator.next();
				alien.moveLeft();
			}
		} // aliens are on their way to the right -> move right
		else {
			Iterator<Alien> iterator = aliens.iterator();
			while (iterator.hasNext()) {
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

    public void checkCollisions() {
        collisionHelp(aliens, playerShots);
        List temp = new ArrayList();
        temp.add(currentPlayer.getSpaceship());
        collisionHelp(temp, alienShots);
        collisionHelp(temp, aliens);
        collisionHelp(alienShots, playerShots);
    }

    private void collisionHelp(List<? extends MovingObject> list1, List<? extends MovingObject> list2) {
        //TODO noch kein sound bei crash implementiert
        A:
        for (MovingObject object1 : list1) {
            if (object1.isCrunched())
                continue;
            for (MovingObject object2 : list2) {
                if (object2.isCrunched())
                    continue;
                if (new Collision(object1, object2).isCrash()) {
                    object1.crunch();
                    object2.crunch();
                    object1.setSpeed(0);
                    object2.setSpeed(0);
                    continue A;
                }
            }
        }
        if (currentPlayer.getSpaceship().isCrunched())
            gameOutcome = GameOutcome.LOST;

        aliens = aliens.stream().filter(o -> !o.isCrunched()).collect(Collectors.toList());
        playerShots = playerShots.stream().filter(o -> !o.isCrunched()).collect(Collectors.toList());
        alienShots = alienShots.stream().filter(o -> !o.isCrunched()).collect(Collectors.toList());

        if (aliens.size() == 0)
            gameOutcome = GameOutcome.WON;

    }

    public int getHighScore() {
        return (alienStartingCount - aliens.size()) * 10;
    }

    public void steerRight() {
        currentPlayer.getSpaceship().moveRight();
    }

	public void steerLeft() {
		getSpaceship().moveLeft();
	}

	public void stopSteer() {
		getSpaceship().stopMove();
	}

	public void playerShoot() {
		if (playerShots.size() < 5 && frameCounter > RECHARGE_TIME) {
			playerShots.add(new Shot(getSpaceship().getPosition()));
			this.frameCounter = 0;
		}
	}

	public void alienShoot() {
		double s = Math.random();
		if (s <= 0.1) {
			if (alienShots.size() < 10) { // max 10 alien shots exist at a time
				alienShots.add(new Shot(chooseRandomAlien().getPosition()));
			}
		}
	}

	private Alien chooseRandomAlien() {
		int r = (int) (Math.random() * (aliens.size() - 1));
		return aliens.get(r);
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

    public GameOutcome getGameOutcome() {
        return gameOutcome;
    }

	public String getAlienImage() {
		return alienImage;
	}

	public void setAlienImage(String alienImage) {
		this.alienImage = alienImage;
	}

	public String getSpaceshipImage() {
		return spaceshipImage;
	}

	public void setSpaceshipImage(String spaceshipImage) {
		this.spaceshipImage = spaceshipImage;
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
}
