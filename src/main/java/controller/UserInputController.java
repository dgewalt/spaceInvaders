package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.model.GameBoard;

public class UserInputController {

    private GameBoard gameBoard;

    @FXML
    private Button button1;
    
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

    public void buttonClicked() throws Exception {
        Stage stage = (Stage) button1.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getClassLoader().getResource("FXMLGameBoard.fxml"));
        stage.setScene(new Scene(root));
    }
    
    public void alien1Clicked() throws Exception{
    	
    }
    public void alien2Clicked() throws Exception{
    	
    }
    public void alien3Clicked() throws Exception{
    	
    }
    
    public void background1Clicked() throws Exception{
    	
    }
    public void background2Clicked() throws Exception{
    	
    }
    public void background3Clicked() throws Exception{
    	
    }
    
    public void spaceship1Clicked() throws Exception{
    	
    }
    public void spaceship2Clicked() throws Exception{
    	
    }
    public void spaceship3Clicked() throws Exception{
    	
    }
    


    void keyboardInput() {

    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
}
