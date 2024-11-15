package com.deepseadevs.fisheatfish;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LoginPage extends BasePage {
    Label usernameLabel;
    Label passwordLabel;
    Button loginButton;
    TextField usernameField;
    PasswordField passwordField;
    Text feedbackText;

    public LoginPage(UIController uiController, SessionManager sessionManager) {
        super(uiController, sessionManager);
    }

    protected Scene createScene() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        gridPane.setAlignment(Pos.CENTER);

        // widgets
        usernameLabel = new Label("Username:");
        usernameLabel.setFont(new Font("Arial", 16));
        usernameField = new TextField();
        usernameField.setFont(new Font("Arial", 14));
        passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", 16));
        passwordField = new PasswordField();
        passwordField.setFont(new Font("Arial", 14));
        loginButton = new Button("Login");
        loginButton.setFont(new Font("Arial", 14));
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        feedbackText = new Text();
        feedbackText.setFont(new Font("Arial", 12));

        // hooks
        loginButton.setOnAction(e -> this.attemptLogin());
        // Move focus to password field when "Enter" is pressed
        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });
        // Trigger login button when "Enter" is pressed
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        });

        // Add nodes to grid
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 1, 2);
        gridPane.add(feedbackText, 1, 3);

        // Outer container
        VBox outerBox = new VBox(gridPane);
        outerBox.setPadding(new Insets(30));
        outerBox.setStyle("-fx-background-color: #F0F8FF; -fx-border-color: #B0C4DE;" +
                          "-fx-border-radius: 10; -fx-background-radius: 10;");
        outerBox.setAlignment(Pos.CENTER);

        return new Scene(outerBox, 400, 300);
    }

    private void attemptLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (DatabaseManager.getInstance().isCorrectPassword(username, password)) {
            sessionManager.setUser(username);
            uiController.gotoMainMenu();
        } else {
            feedbackText.setText("Invalid username or password");
            feedbackText.setFill(Color.RED);
        }
    }
}
