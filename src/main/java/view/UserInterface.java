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

public class UserInterface extends Pane {


    VBox vBox;
    HBox gameToolBar ;
    Button startGame;
    Button stopGame;
    Label gameLabel;

    private final Canvas canvas;

    private static final Color BACKGROUND_COLOR = Color.WHITE;

    private static final int UPDATE_PERIOD = 1000 / 25;
    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 300;

    private static final Dimension2D DEFAULT_SIZE = new Dimension2D(DEFAULT_WIDTH, DEFAULT_HEIGHT);

    public static Dimension2D getPreferredSize() {
        return DEFAULT_SIZE;
    }

    private Timer gameTimer;

    public UserInterface() {

        canvas = new Canvas();
        canvas.setWidth(DEFAULT_WIDTH);
        canvas.setHeight(DEFAULT_HEIGHT - 50);
    }

    private void setupUIElements() {
        startGame = new Button("Start Game");
        stopGame = new Button("Stop Game");
        gameLabel = new Label("Space Invaders");
        gameToolBar = new HBox(startGame, stopGame, gameLabel);

        vBox = new VBox(gameToolBar, canvas);


    }

    void initialize() {
        setupGameBoard();
    }

    private void setupGameBoard() {

    }
    
    void paintObjects() {
    	
    }
    
    


}
