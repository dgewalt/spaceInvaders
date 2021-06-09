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

    public void buttonClicked() throws Exception {
        Stage stage = (Stage) button1.getScene().getWindow();
        Pane root = FXMLLoader.load(getClass().getClassLoader().getResource("FXMLGameBoard.fxml"));
        stage.setScene(new Scene(root));

    }

    void keyboardInput() {

    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
}
