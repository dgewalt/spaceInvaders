package main.java.view;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType;
import main.java.controller.UserInputController;
import main.java.model.*;
import javafx.scene.image.Image;

import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class UserInterface extends Pane {


    private VBox vBox;
    private HBox gameToolBar;
    private Button startGame;
    private Button stopGame;
    private Label gameLabel;
    private Label scoreLabel;


    private final Canvas canvas;
    private final GameBoard gameBoard;

    private static final Color BACKGROUND_COLOR = Color.WHITE;

    private static final int UPDATE_PERIOD = 1000 / 25;
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 600;

    private static final Dimension2D DEFAULT_SIZE = new Dimension2D(DEFAULT_WIDTH, DEFAULT_HEIGHT);

    public static Dimension2D getPreferredSize() {
        return DEFAULT_SIZE;
    }

    private Timer gameTimer;

    private HashMap<String, Image> alienimageCache;
    private HashMap<String, Image> spaceshipimageCache;
    private HashMap<String, Image> backgroundimageCache;

    public UserInterface(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

        setHeight(DEFAULT_HEIGHT);
        setWidth(DEFAULT_WIDTH);

        canvas = new Canvas();
        canvas.setWidth(DEFAULT_WIDTH);
        canvas.setHeight(DEFAULT_HEIGHT - 50);
        setupUIElements();

        canvas.setFocusTraversable(true);

    }

    private void setupUIElements() {
        startGame = new Button("Start Game");
        stopGame = new Button("Stop Game");

        startGame.setOnMouseClicked(e -> startGame());
        stopGame.setOnMouseClicked(e -> stopGame());

        gameLabel = new Label("Space Invaders");
        scoreLabel = new Label("Score: 0          ");
        Region placeholder1 = new Region();
        HBox.setHgrow(placeholder1, Priority.ALWAYS);
        Region placeholder2 = new Region();
        HBox.setHgrow(placeholder2, Priority.ALWAYS);


        gameToolBar = new HBox(startGame, stopGame, placeholder1, gameLabel, placeholder2, scoreLabel);

        vBox = new VBox(gameToolBar, canvas);

        gameToolBar.setFocusTraversable(false);
        startGame.setFocusTraversable(false);
        stopGame.setFocusTraversable(false);

        this.getChildren().add(vBox);


    }

    public void startGame() {
        if (!this.gameBoard.isRunning()) {
            this.gameBoard.startGame();
            startTimer();
            paint();
        }
    }

    public void stopGame() {
        if (this.gameBoard.isRunning()) {
            this.gameBoard.stopGame();
            this.gameTimer.cancel();
        }
    }

    private void startTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                updateGame();
            }
        };
        if (this.gameTimer != null) {
            this.gameTimer.cancel();
        }
        this.gameTimer = new Timer();
        this.gameTimer.scheduleAtFixedRate(timerTask, UPDATE_PERIOD, UPDATE_PERIOD);
    }

    void paintObject(MovingObject object) {
        Point2D position = object.getPosition();
        Dimension2D size = object.getSize();

        //nur temporär, später werden die icons gezeichnet
        canvas.getGraphicsContext2D().setFill(Color.BLACK);
        canvas.getGraphicsContext2D().fillRect(position.getX(), position.getY(), size.getWidth(), size.getHeight());

    }

    private void updateGame() {
        if (gameBoard.isRunning()) {
            // updates object positions and repaints graphics
            gameBoard.update();
            if (this.gameBoard.getGameOutcome() == GameOutcome.LOST) {
                //todo bumpers async alert
                System.out.println("Oh.. you lost.");
                this.stopGame();
            } else if (this.gameBoard.getGameOutcome() == GameOutcome.WON) {
                //todo bumpers async alert
                System.out.println("Congratulations! You won!!");
                this.stopGame();
            }
            Platform.runLater(() -> scoreLabel.setText("Score: " + gameBoard.getHighScore()+"          "));


            paint();
        }
    }



    private void paint() {

        canvas.getGraphicsContext2D().setFill(BACKGROUND_COLOR);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //paint player spaceship
        setupSpaceshipImageCache();
        paintSpaceship(gameBoard.getSpaceship());

        setupAlienImageCache();
        for (Alien alien : gameBoard.getAliens()) {
            paintAlien(alien);
        }

        for (Shot playerShot : gameBoard.getPlayerShots()) {
            //System.out.println("test");
            paintObject(playerShot);
        }

        for (Shot alienShot : gameBoard.getAlienShots()) {
            paintObject(alienShot);
        }
    }

    private void paintAlien(Alien alien) {
        Point2D alienPosition = alien.getPosition();

        canvas.getGraphicsContext2D().drawImage(this.alienimageCache.get(gameBoard.getAlienImage()), alienPosition.getX(),
                alienPosition.getY(), alien.getSize().getWidth(), alien.getSize().getHeight());
    }

    private void paintSpaceship(Spaceship spaceship) {
        Point2D spaceshipPosition = spaceship.getPosition();

        canvas.getGraphicsContext2D().drawImage(this.spaceshipimageCache.get(gameBoard.getSpaceshipImage()), spaceshipPosition.getX(),
                spaceshipPosition.getY(), spaceship.getSize().getWidth(), spaceship.getSize().getHeight());
    }

    private void setupAlienImageCache() {
        this.alienimageCache = new HashMap<>();
        for (Alien alien : this.gameBoard.getAliens()) {
            String imageLocation = gameBoard.getAlienImage();
            this.alienimageCache.computeIfAbsent(imageLocation, this::getAlienImage);
        }
        String alienImageLocation = this.gameBoard.getAlienImage();
        this.alienimageCache.put(alienImageLocation, getAlienImage(alienImageLocation));
    }

    private Image getAlienImage(String alienImage) {
        URL alienImageUrl = getClass().getClassLoader().getResource(alienImage);
        if (alienImageUrl == null) {
            throw new IllegalArgumentException(
                    "Please ensure that your resources folder contains the appropriate files for this exercise.");
        }
        return new Image(alienImageUrl.toExternalForm());
    }

    private void setupSpaceshipImageCache() {
        this.spaceshipimageCache = new HashMap<>();
        String imageLocation = gameBoard.getSpaceshipImage();
        this.spaceshipimageCache.computeIfAbsent(imageLocation, this::getSpaceshipImage);

        String spaceshipImageLocation = this.gameBoard.getSpaceshipImage();
        this.spaceshipimageCache.put(spaceshipImageLocation, getSpaceshipImage(spaceshipImageLocation));
    }

    private Image getSpaceshipImage(String spaceshipFilePath) {
        URL spaceshipImageUrl = getClass().getClassLoader().getResource(spaceshipFilePath);
        if (spaceshipImageUrl == null) {
            throw new IllegalArgumentException(
                    "Please ensure that your resources folder contains the appropriate files for this exercise.");
        }
        return new Image(spaceshipImageUrl.toExternalForm());
    }
}
