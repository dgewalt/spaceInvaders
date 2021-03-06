package main.java.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.controller.UserInputController;
import main.java.model.GameBoard;

public class SpaceInvadersApplication extends Application {


    private static final int APPLICATION_WIDTH = 600;
    private static final int APPLICATION_HEIGHT = 600;

    private GameBoard gameBoard;
    private UserInputController controller;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("TumSpaceInvaders");
        primaryStage.setWidth(APPLICATION_WIDTH);
        primaryStage.setHeight(APPLICATION_HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> System.exit(0));

        gameBoard = new GameBoard(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        UserInterface ui = new UserInterface(gameBoard);
        gameBoard.setUi(ui);


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("FXMLDocument.fxml"));
        Pane root = fxmlLoader.load();


        controller = fxmlLoader.getController();

        controller.setGameBoard(gameBoard);



        Scene mainMenu = new Scene(root);
        primaryStage.setScene(mainMenu);
        primaryStage.show();
    }



    public static void startApp(String[] args) {
        launch(args);
    }

}
