package main.java.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.controller.UserInputController;
import main.java.model.GameBoard;

public class SpaceInvadersApplication extends Application {


    private static final int APPLICATION_WIDTH = 800;
    private static final int APPLICATION_HEIGHT = 800;

    private GameBoard gameBoard;
    private UserInputController controller;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("TumSpaceInvaders");
        primaryStage.setWidth(APPLICATION_WIDTH);
        primaryStage.setHeight(APPLICATION_HEIGHT);
        primaryStage.setResizable(false);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("FXMLDocument.fxml"));
        Pane root = fxmlLoader.load();


        controller = fxmlLoader.getController();
        gameBoard = new GameBoard(200);

        UserInterface ui = new UserInterface(gameBoard);

        controller.setGameBoard(gameBoard);
        gameBoard.setUi(ui);




        Scene mainMenu = new Scene(root);
        primaryStage.setScene(mainMenu);
        primaryStage.show();
    }



    public static void startApp(String[] args) {
        launch(args);
    }

}
