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

public class NewAccountPage extends BasePage {
    Label usernameLabel;
    Label passwordLabel;
    Label confirmPasswordLabel;
    Button createAccountButton;
    Button backToLoginButton;
    TextField usernameField;
    PasswordField passwordField;
    PasswordField confirmPasswordField;
    Text feedbackText;

    public NewAccountPage(UIController uiController, SessionManager sessionManager) {
        super(uiController, sessionManager);
    }

    protected Scene createScene() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        gridPane.setAlignment(Pos.CENTER);

        // Widgets and design
        usernameLabel = new Label("Username:");
        usernameLabel.setFont(new Font("Arial", 16));

        usernameField = new TextField();
        usernameField.setFont(new Font("Arial", 14));

        passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", 16));

        passwordField = new PasswordField();
        passwordField.setFont(new Font("Arial", 14));

        confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Arial", 16));

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setFont(new Font("Arial", 14));

        createAccountButton = new Button("Create Account");
        createAccountButton.setFont(new Font("Arial", 14));
        createAccountButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        backToLoginButton = new Button("Back to Login");
        backToLoginButton.setFont(new Font("Arial", 14));
        backToLoginButton.setStyle("-fx-background-color: #B0C4DE; -fx-text-fill: white;");

        feedbackText = new Text();
        feedbackText.setFont(new Font("Arial", 12));

        // Hooks
        createAccountButton.setOnAction(e -> this.attemptCreateAccount());

        // Keypress events
        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                confirmPasswordField.requestFocus();
            }
        });
        confirmPasswordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                createAccountButton.fire();
            }
        });

        backToLoginButton.setOnAction(e -> uiController.gotoLogin());

        // Add nodes to grid
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(confirmPasswordLabel, 0, 2);
        gridPane.add(confirmPasswordField, 1, 2);
        gridPane.add(createAccountButton, 1, 3);
        gridPane.add(backToLoginButton, 0, 3);
        gridPane.add(feedbackText, 1, 4);

        // Outer container
        VBox outerBox = new VBox(gridPane);
        outerBox.setPadding(new Insets(30));
        outerBox.setStyle("-fx-background-color: #F0F8FF; -fx-border-color: #B0C4DE;" +
                "-fx-background-radius: 10;");
        outerBox.setAlignment(Pos.CENTER);

        return new Scene(outerBox, 400, 350);
    }

    private void attemptCreateAccount() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            feedbackText.setText("All fields are required.");
            feedbackText.setFill(Color.RED);
        } else if (!password.equals(confirmPassword)) {
            feedbackText.setText("Passwords do not match.");
            feedbackText.setFill(Color.RED);
        } else if (DatabaseManager.getInstance().userExists(username)) {
            feedbackText.setText("Username already taken.");
            feedbackText.setFill(Color.RED);
        } else {
            createAccountButton.setDisable(true);
            DatabaseManager.getInstance().createNewUser(username, password);
            feedbackText.setText("Account created successfully!");
            feedbackText.setFill(Color.GREEN);
        }
    }
}
