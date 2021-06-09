package main.java.view;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.java.model.Dimension2D;
import main.java.model.GameBoard;

import java.util.Timer;
import java.util.TimerTask;

public class UserInterface extends Pane {


    private VBox vBox;
    private HBox gameToolBar ;
    private Button startGame;
    private Button stopGame;
    private Label gameLabel;

    private final Canvas canvas;
    private final GameBoard gameBoard;

    private static final Color BACKGROUND_COLOR = Color.WHITE;

    private static final int UPDATE_PERIOD = 1000 / 25;
    private static final int DEFAULT_WIDTH = 500;
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

    }

    private void setupUIElements() {
        startGame = new Button("Start Game");
        stopGame = new Button("Stop Game");
        gameLabel = new Label("Space Invaders");
        startGame.setVisible(true);
        startGame.setMinSize(50, 20);
        gameToolBar = new HBox(startGame, stopGame, gameLabel);

        vBox = new VBox(gameToolBar, canvas);

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
    
    void paintObjects() {
    	
    }

    private void updateGame() {
        if (gameBoard.isRunning()) {
            // updates car positions and re-renders graphics
            gameBoard.update();
            paint();
        }
    }

    private void paint() {

    }
    
    


}
