package main.java.view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType;
import main.java.model.*;

import java.util.Timer;
import java.util.TimerTask;

public class UserInterface extends Pane {


    private VBox vBox;
    private HBox gameToolBar;
    private Button startGame;
    private Button stopGame;
    private Label gameLabel;

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
        gameToolBar = new HBox(startGame, stopGame, gameLabel);

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
            paint();
        }
    }

    private void paint() {
        canvas.getGraphicsContext2D().setFill(BACKGROUND_COLOR);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //paint player spaceship
        paintObject(gameBoard.getSpaceship());

        for (Alien alien : gameBoard.getAliens()) {
            paintObject(alien);
        }

        for (Shot playerShot : gameBoard.getPlayerShots()) {
            System.out.println("test");
            paintObject(playerShot);
        }

        for (Shot alienShot : gameBoard.getAlienShots()) {
            paintObject(alienShot);
        }

    }


}
