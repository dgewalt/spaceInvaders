package main.java.model;

import main.java.view.UserInterface;

public class GameBoard {

    private int size;
    private String background;
    private UserInterface ui;

    private final Player currentPlayer;
    private Player[] leaderBoard;
    private MovingObject[] objects;

    private boolean running;

    public GameBoard(int size) {
        this.size = size;

        this.currentPlayer = new Player(new Spaceship());
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
    
    public void setUi(UserInterface ui) {
    	this.ui = ui;
    }
    
    public UserInterface getUi() {
    	return ui;
    }
}
