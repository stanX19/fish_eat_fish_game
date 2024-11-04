package com.deepseadevs.fisheatfish;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPage {
    private TextField usernameField;
    private PasswordField passwordField;
    private Label messageLabel;
    private final LoginHandler loginHandler;

    public LoginPage(LoginHandler loginHandler) {
        this.loginHandler = loginHandler;
    }

    public void display(Stage primaryStage) {
        primaryStage.setTitle("Login Page");

        // Setup layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        // Username field
        usernameField = new TextField();
        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);

        // Password field
        passwordField = new PasswordField();
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);

        // Message label
        messageLabel = new Label();
        grid.add(messageLabel, 1, 2);

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> handleLogin());
        grid.add(loginButton, 1, 3);

        // Scene
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean success = loginHandler.login(username, password);
        if (success) {
            messageLabel.setText("Login successful!");
        } else {
            messageLabel.setText("Invalid username or password.");
        }
    }
}
