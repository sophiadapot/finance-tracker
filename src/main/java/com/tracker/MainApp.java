package com.tracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // This path must match the folder structure in src/main/resources
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tracker/main.fxml"));
        Parent root = loader.load();
        
        primaryStage.setTitle("AI Finance Tracker");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}