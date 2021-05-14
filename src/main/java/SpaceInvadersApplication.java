package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SpaceInvadersApplication extends Application {


    private static final int APPLICATION_WIDTH = 600;
    private static final int APPLICATION_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("TumSpaceInvaders");
        primaryStage.setWidth(APPLICATION_WIDTH);
        primaryStage.setHeight(APPLICATION_HEIGHT);

        Pane root = FXMLLoader.load(getClass().getClassLoader().getResource("FXMLDocument.fxml"));
        Scene mainMenu = new Scene(root);
        primaryStage.setScene(mainMenu);
        primaryStage.show();
    }

    public static void startApp(String[] args) {
        launch(args);
    }

}
