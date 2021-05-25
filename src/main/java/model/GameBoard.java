package main.java.model;

public class GameBoard {

    private final Dimension2D size;

    private final Player player;

    private boolean running;

    public GameBoard(Dimension2D size) {
        this.size = size;

        this.player = new Player(new Spaceship());
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
}
