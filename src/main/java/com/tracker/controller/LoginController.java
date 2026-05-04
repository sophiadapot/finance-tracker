package com.tracker.controller;

import com.tracker.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;

public class LoginController {
    @FXML private TextField userField;
    @FXML private PasswordField passField;
    @FXML private ComboBox<String> personaBox;
    @FXML private Button loginButton;

    @FXML
    public void initialize() 
    {
        personaBox.getItems().addAll("Frugal Advisor", "Risk Taker", "Strict Accountant");
    }

    @FXML
    public void handleLogin() 
    {
        String username = userField.getText();
        String password = passField.getText();
        String persona = personaBox.getValue();

        // 1. Create the User object
        User currentUser = new User(username, password, persona);

        // 2. Registration Logic: Create file if it doesn't exist
        File userFile = new File(username + "_data.csv");
        if (!userFile.exists()) {
            try {
                userFile.createNewFile();
                // Optional: Save password/persona to a config file here
            } catch (Exception e) {
                System.out.println("Could not create user file.");
            }
        }

        // 3. Switch to Main UI and pass the User object
        navigateToMain(currentUser);
    }

    private void navigateToMain(User user) 
    {
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tracker/main.fxml"));
            Parent root = loader.load();

            // GET THE MAIN CONTROLLER INSTANCE
            MainController mainCtrl = loader.getController();
            
            // PASS THE USER DATA
            mainCtrl.setLoggedInUser(user);

            // Show the new stage
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}