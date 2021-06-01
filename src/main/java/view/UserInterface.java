package main.java.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import main.java.model.Dimension2D;
import main.java.model.GameBoard;

import java.util.Timer;

public class UserInterface extends Canvas {
	
	private UserInterface userInterface;

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
    	
    }

    void initialize() {
        setupGameBoard();
    }

    private void setupGameBoard() {

    }
    
    void paintObjects() {
    	
    }
    
    


}
