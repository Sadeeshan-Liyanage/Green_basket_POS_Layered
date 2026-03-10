package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.App;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void onUsernameAction() {
        passwordField.requestFocus();
    }

    @FXML
    private void login() throws IOException {
        String realUsername = "Sadee";
        String realPassword = "@1234";

        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println(username + " - " + password);

        if(username.equals(realUsername) && password.equals(realPassword)) {
            App.setRoot("Dashboard");
        }
    }

}
