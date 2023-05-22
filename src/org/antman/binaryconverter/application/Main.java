package org.antman.binaryconverter.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui/FrontPage.fxml"));
        primaryStage.setTitle("Ant-Man");
        primaryStage.setScene(new Scene(root, 1200, 550));
        primaryStage.setMinHeight(420);
        primaryStage.setMinWidth(750);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
