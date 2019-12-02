package org.antman.binaryconverter.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gui/FrontPage.fxml"));
        primaryStage.setTitle("Ant-Man");
       // TextField textField = new TextField("brijehs");

        primaryStage.setScene(new Scene(root, 1200, 550));
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}
