package main.java.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.model.GameBoard;


public class UserInputController {

    private GameBoard gameBoard;
    private Scene gameScene;

    @FXML
    private Button startbutton1;
    @FXML
    private Button startbutton2;

    @FXML
    private Button settingsButton;
    @FXML
    private Button background1;
    @FXML
    private Button background2;
    @FXML
    private Button background3;
    
    @FXML
    private Button alien1;
    @FXML
    private Button alien2;
    @FXML
    private Button alien3;
    
    @FXML
    private Button spaceship1;
    @FXML
    private Button spaceship2;
    @FXML
    private Button spaceship3;


    public void startGame() throws Exception {
    	gameScene = new Scene(gameBoard.getUi());
        Stage stage = (Stage) startbutton1.getScene().getWindow();
        stage.setScene(gameScene);
        startKeyHandler();
    }
    
    public void startGame2() throws Exception {
    	gameScene = new Scene(gameBoard.getUi());
        Stage stage = (Stage) startbutton2.getScene().getWindow();
        stage.setScene(gameScene);
        startKeyHandler();
    }
    
    public void alien1Clicked() {
    	gameBoard.setAlienImage("Alien1.png");
    }
    public void alien2Clicked() {
    	gameBoard.setAlienImage("Alien2.png");
    }
    public void alien3Clicked() {
    	gameBoard.setAlienImage("Alien3.png");
    }
    
    public void background1Clicked() {
    	gameBoard.setBackgroundImage("background1.jpg");
    }
    public void background2Clicked() {
    	gameBoard.setBackgroundImage("background2.jpg");
    }
    public void background3Clicked() {
        gameBoard.setBackgroundImage("background3.jpg");
    }
    
    public void spaceship1Clicked() {
    	gameBoard.setSpaceshipImage("spaceship1.png");
    }
    public void spaceship2Clicked() {
    	gameBoard.setSpaceshipImage("spaceship2.png");
    }
    public void spaceship3Clicked() {
    	gameBoard.setSpaceshipImage("spaceship3.png");
    }

    public void openSettings() throws Exception {
        Stage stage = (Stage) settingsButton.getScene().getWindow();
//        Pane root = FXMLLoader.load(getClass().getClassLoader().getResource("FXMLGameSettings.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("FXMLGameSettings.fxml"));
        Pane root = fxmlLoader.load();
        ((UserInputController)fxmlLoader.getController()).setGameBoard(gameBoard);

        stage.setScene(new Scene(root));
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
    
    private void startKeyHandler() {
        //when key is pressed speed of spaceship is set to +/-MAXSPEED
        //when key is release speed of spaceship is zeroed -> makes the animation smoother
    	gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case A, LEFT: gameBoard.steerLeft();break;
                    case W: gameBoard.playerShoot();break;
                    case S: ;break;
                    case D, RIGHT: gameBoard.steerRight();break;
                    case SPACE: gameBoard.playerShoot();break;
                }
            }
        });

    	gameScene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case A, LEFT: gameBoard.stopSteer();break;

                case D, RIGHT: gameBoard.stopSteer();break;

            }
        });
    }
}
