package com.deepseadevs.fisheatfish.pages;

import com.deepseadevs.fisheatfish.database.DatabaseManager;
import com.deepseadevs.fisheatfish.database.SessionManager;
import com.deepseadevs.fisheatfish.UIController;
import com.deepseadevs.fisheatfish.pages.utils.LoginUtils;
import com.deepseadevs.fisheatfish.widgets.buttons.MainButton;
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
        gridPane.setStyle("-fx-background-color: #1a202c;");

        // Username Input
        userIDField = new TextField();
        userIDField.setPromptText("Enter your username");
        userIDField.setFont(new Font("Arial", 14));
        userIDField.setStyle(
                "-fx-background-color: #1a202c;" +
                        "-fx-border-color: white;" +
                        "-fx-border-radius: 10;" +
                        "-fx-text-fill: white;" +
                        "-fx-prompt-text-fill: gray;" +
                        "-fx-padding: 10;"
        );

        userIDLabel = new Label("Username:");
        userIDLabel.setStyle("""
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-text-fill: #b5c7eb;
        """);

        // Password Input
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setFont(new Font("Arial", 14));
        passwordField.setStyle(
                "-fx-background-color: #1a202c;" +
                        "-fx-border-color: white;" +
                        "-fx-border-radius: 10;" +
                        "-fx-text-fill: white;" +
                        "-fx-prompt-text-fill: gray;" +
                        "-fx-padding: 10;"
        );

        passwordLabel = new Label("Password:");
        passwordLabel.setStyle("""
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-text-fill: #b5c7eb;
        """);

        // Styled Buttons
        loginButton = new MainButton("Login");
        newAccountButton = new MainButton("New Account");

        // Feedback Text
        feedbackText = new Text();
        feedbackText.setFont(new Font("Arial", 12));
        feedbackText.setFill(Color.RED);
        feedbackText.setStyle("-fx-padding: 10;");

        // Hooks
        loginButton.setOnAction(e -> this.attemptLogin());
        newAccountButton.setOnAction(e -> uiController.gotoNewAccount());
        userIDField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        });

        // Add components to grid
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
        outerBox.setStyle("-fx-background-color: #1a202c;");
        outerBox.setAlignment(Pos.CENTER);

        return new Scene(outerBox, 400, 350);

    }


    //  Apply password hashing using LoginUtils.hashString done
    private void attemptLogin() {
        String userID = userIDField.getText();
        String password = passwordField.getText();

        String hashedPassword = LoginUtils.hashString(password);

        if (DatabaseManager.getInstance().isCorrectPassword(userID, hashedPassword)) {
            sessionManager.setUser(userID);
            uiController.gotoMainMenu();
        } else {
            feedbackText.setText("Invalid username or password");
            feedbackText.setFill(Color.RED);
        }
    }
}


