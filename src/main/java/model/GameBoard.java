package main.java.model;

import javafx.util.converter.ShortStringConverter;
import main.java.view.UserInterface;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
    private GameOutcome gameOutcome = GameOutcome.OPEN;

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
        checkCollisions();

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
            if (playershot.getPosition().getY() <= 0) {
                playerShots.remove(i);
            } else {
                System.out.println(playershot.getPosition().toString());
                playershot.move();
            }
        }
    }

    public void moveAliens() {

    }

    public void checkCollisions() {
        collisionHelp(aliens, playerShots);
        List temp = new ArrayList();
        temp.add(currentPlayer.getSpaceship());
        collisionHelp(temp, alienShots);
        collisionHelp(temp, aliens);
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

    public GameOutcome getGameOutcome() {
        return gameOutcome;
    }
}
