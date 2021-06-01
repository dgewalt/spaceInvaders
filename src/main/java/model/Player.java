package main.java.model;

public class Player {

    private Spaceship spaceship;
    private Highscore score;
    private String name;
    private int lives;

    public Player(Spaceship spaceship) {
        this.spaceship = spaceship;
    }
}
