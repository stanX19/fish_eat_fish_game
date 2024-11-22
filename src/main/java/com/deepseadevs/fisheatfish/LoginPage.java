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
    Label userIDLabel;
    Label passwordLabel;
    Button loginButton;
    Button newAccountButton;
    TextField userIDField;
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

        // widgets and design
        userIDLabel = new Label("Username:");
        userIDLabel.setFont(new Font("Arial", 16));
        userIDField = new TextField();
        userIDField.setFont(new Font("Arial", 14));
        passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", 16));
        passwordField = new PasswordField();
        passwordField.setFont(new Font("Arial", 14));
        loginButton = new Button("Login");
        loginButton.setFont(new Font("Arial", 14));
        loginButton.setStyle("-fx-background-color: #66ccff; -fx-text-fill: white;");
        // TODO: beautify newAccount button
        newAccountButton = new Button("New Account");
        newAccountButton.setFont(new Font("Arial", 14));
        newAccountButton.setStyle("-fx-background-color: #0099ff; -fx-text-fill: white;");
        feedbackText = new Text();
        feedbackText.setFont(new Font("Arial", 12));

        // hooks
        loginButton.setOnAction(e -> this.attemptLogin());
        newAccountButton.setOnAction(e -> uiController.gotoNewAccount());
        // Move focus to password field when "Enter" is pressed
        userIDField.setOnKeyPressed(event -> {
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
        gridPane.add(userIDLabel, 0, 0);
        gridPane.add(userIDField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 1, 2);
        gridPane.add(newAccountButton, 1, 3);
        gridPane.add(feedbackText, 1, 4);

        // Outer container
        VBox outerBox = new VBox(gridPane);
        outerBox.setPadding(new Insets(30));
        outerBox.setStyle("-fx-background-color: #F0F8FF; -fx-border-color: #B0C4DE;" +
                          "-fx-background-radius: 10;");
        outerBox.setAlignment(Pos.CENTER);

        return new Scene(outerBox, 400, 300);
    }

    private void attemptLogin() {
        String userID = userIDField.getText();
        String password = passwordField.getText();

        if (DatabaseManager.getInstance().isCorrectPassword(userID, password)) {
            sessionManager.setUser(userID);
            uiController.gotoMainMenu();
        } else {
            feedbackText.setText("Invalid username or password");
            feedbackText.setFill(Color.RED);
        }
    }
}
