package main.java.model;

import javafx.util.converter.ShortStringConverter;
import main.java.view.UserInterface;

import java.util.LinkedList;
import java.util.List;

public class GameBoard {

//    private int size;
    private Dimension2D size;
    private String background;
    private UserInterface ui;

    private final Player currentPlayer;
    private Player[] leaderBoard;

    private List<Alien> aliens;
    private List<Shot> playerShots;
    private List<Shot> alienShots;


    private boolean running;

    public GameBoard(int width, int height) {
        this.size = new Dimension2D(width, height);

        this.currentPlayer = new Player(new Spaceship(new Point2D(this.size.getWidth()/2, this.size.getHeight() - 100)));


        aliens = new LinkedList<>();
        playerShots = new LinkedList<>();
        alienShots = new LinkedList<>();

    }

    public void update() {

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
    
    public void moveAliens() {
    	
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
