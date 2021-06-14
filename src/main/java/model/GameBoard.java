package main.java.model;

import javafx.util.converter.ShortStringConverter;
import main.java.view.UserInterface;

import java.util.LinkedList;
import java.util.List;

public class GameBoard {

    private Dimension2D size;
    private String background;
    private UserInterface ui;

    //enables measuring time for minimum time span between shots
    private int frameCounter;
    private static final int FPS = 25;
    private static final int RECHARGE_TIME = 15;

    private final Player currentPlayer;
    private Player[] leaderBoard;

    private List<Alien> aliens;
    private List<Shot> playerShots;
    private List<Shot> alienShots;


    private boolean running;

    public GameBoard(int width, int height) {
        this.size = new Dimension2D(width, height);
        this.frameCounter = 0;

        this.currentPlayer = new Player(new Spaceship(new Point2D(this.size.getWidth() / 2, this.size.getHeight() - 100)));


        aliens = new LinkedList<>();
        playerShots = new LinkedList<>();
        alienShots = new LinkedList<>();

    }

    public void update() {
        this.frameCounter++;
        moveShots();
        getSpaceship().move();

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

    public void configureGame() {

    }

    public void moveShots() {
        for (int i = playerShots.size() - 1; i >= 0; i--) {
            Shot playershot = playerShots.get(i);
            if (playershot.getPosition().getY() <= 0 ) {
                playerShots.remove(i);
            } else {
                System.out.println(playershot.getPosition().toString());
                playershot.move();
            }
        }
    }

    public void moveAliens() {

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
