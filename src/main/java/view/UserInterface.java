package main.java.view;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.java.model.*;

import java.net.URL;
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

    private Image alienImage;
    private Image spaceShipImage;
    private Image backgroundImage;

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

    void paintShot(Shot shot) {
        Point2D position = shot.getPosition();
        Dimension2D size = shot.getSize();

        canvas.getGraphicsContext2D().setFill(Color.BLACK);
        canvas.getGraphicsContext2D().fillRect(position.getX(), position.getY(), size.getWidth(), size.getHeight());
    }

    private void paintAliens() {
        if (alienImage == null) {
            URL alienURL = getClass().getClassLoader().getResource(gameBoard.getAlienImage());
            if (alienURL == null) {
                throw new IllegalArgumentException("Please ensure that the resources folder contains the required files.");
            }
            alienImage = new Image(alienURL.toExternalForm());
        }
        for (Alien alien : gameBoard.getAliens()) {
            Point2D alienPos = alien.getPosition();
            canvas.getGraphicsContext2D().drawImage(this.alienImage, alienPos.getX(), alienPos.getY(), alien.getSize().getWidth(), alien.getSize().getHeight());
        }
    }

    private void paintSpaceship() {
        if (spaceShipImage == null) {
            URL spaceshipURL = getClass().getClassLoader().getResource(gameBoard.getSpaceshipImage());
            if (spaceshipURL == null) {
                throw new IllegalArgumentException("Please ensure that the resources folder contains the required files.");
            }
            spaceShipImage = new Image(spaceshipURL.toExternalForm());
        }
        Point2D spaceshipPos = gameBoard.getSpaceship().getPosition();
        Dimension2D spaceshipSize = gameBoard.getSpaceship().getSize();
        canvas.getGraphicsContext2D().drawImage(this.spaceShipImage, spaceshipPos.getX(), spaceshipPos.getY(), spaceshipSize.getWidth(), spaceshipSize.getHeight());
    }

    private void paintBackground() {
        if (backgroundImage == null) {
            URL backgroundURL = getClass().getClassLoader().getResource(gameBoard.getBackgroundImage());
            if (backgroundURL == null) {
                throw new IllegalArgumentException("Please ensure that the resources folder contains the required files.");
            }
            backgroundImage = new Image(backgroundURL.toExternalForm());
        }
        canvas.getGraphicsContext2D().drawImage(backgroundImage,0, 0, canvas.getWidth(), canvas.getHeight());
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

//        canvas.getGraphicsContext2D().setFill(BACKGROUND_COLOR);
//        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        paintBackground();

        //paint player spaceship
        paintSpaceship();
        for (Alien alien : gameBoard.getAliens()) {
            paintAliens();
        }

        for (Shot playerShot : gameBoard.getPlayerShots()) {
            paintShot(playerShot);
        }

        for (Shot alienShot : gameBoard.getAlienShots()) {
            paintShot(alienShot);
        }

    }


}
