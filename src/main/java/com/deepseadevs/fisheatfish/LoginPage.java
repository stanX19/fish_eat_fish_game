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
import javafx.scene.layout.HBox;
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

        // widgets and design
        HBox usernamePane = new HBox();
        usernamePane.setPadding(new Insets(10));
        usernamePane.setStyle(
                "-fx-background-color: #1a202c;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;"
        );

        userIDLabel = new Label("Username:");
        userIDLabel.setFont(new Font("Arial", 16));
        userIDLabel.setTextFill(Color.WHITE); // White label text
        userIDField = new TextField();
        userIDField.setFont(new Font("Arial", 14));
        usernamePane.getChildren().addAll(userIDLabel, userIDField);
        usernamePane.setSpacing(10);

        // Password Pane
        VBox passwordPane = new VBox();
        passwordPane.setPadding(new Insets(10));
        passwordPane.setStyle(
                "-fx-background-color: #1a202c;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;"
        );
        passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", 16));
        passwordLabel.setTextFill(Color.WHITE); // White label text
        passwordField = new PasswordField();
        passwordField.setFont(new Font("Arial", 14));
        passwordPane.getChildren().addAll(passwordLabel, passwordField);
        passwordPane.setSpacing(10);

        // Login Button
        loginButton = new Button("Login");
        loginButton.setFont(new Font("Arial", 14));
        loginButton.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #1a202c;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 20;"
        );

        // New Account Button
        newAccountButton = new Button("New Account");
        newAccountButton.setFont(new Font("Arial", 14));
        newAccountButton.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #1a202c;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 20;"
        );

        // feedback text
        feedbackText = new Text();
        feedbackText.setFont(new Font("Arial", 12));
        // added by cdy
        feedbackText.setFill(Color.RED); // Feedback text in red for errors
        feedbackText.setStyle("-fx-padding: 10;");

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
        outerBox.setStyle("-fx-background-color: #1a202c;");
        outerBox.setAlignment(Pos.CENTER);

        return new Scene(outerBox, 400, 300);
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

// you can try this below one. I know is bad.
// but the username and password labels inside the gridPane maybe we can implement.
// i think it is cool hehehe

//package com.deepseadevs.fisheatfish;
//
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.input.KeyCode;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;
//
//public class LoginPage extends BasePage {
//    Label userIDLabel;
//    Label passwordLabel;
//    Button loginButton;
//    Button newAccountButton;
//    TextField userIDField;
//    PasswordField passwordField;
//    Text feedbackText;
//
//    public LoginPage(UIController uiController, SessionManager sessionManager) {
//        super(uiController, sessionManager);
//    }
//
//    protected Scene createScene() {
//        GridPane gridPane = new GridPane();
//        gridPane.setPadding(new Insets(20, 20, 20, 20));
//        gridPane.setVgap(20);
//        gridPane.setHgap(20);
//        gridPane.setAlignment(Pos.CENTER);
//
//        // Styling for the gridPane background
//        gridPane.setStyle("-fx-background-color: #1a202c;");
//
//        // Username Input
//        userIDField = new TextField();
//        userIDField.setPromptText("Enter your username");
//        userIDField.setFont(new Font("Arial", 14));
//        userIDField.setStyle(
//                "-fx-background-color: #1a202c;" +
//                        "-fx-border-color: white;" +
//                        "-fx-border-radius: 10;" +
//                        "-fx-text-fill: white;" +
//                        "-fx-prompt-text-fill: gray;" +
//                        "-fx-padding: 10;"
//        );
//
//        // Password Input
//        passwordField = new PasswordField();
//        passwordField.setPromptText("Enter your password");
//        passwordField.setFont(new Font("Arial", 14));
//        passwordField.setStyle(
//                "-fx-background-color: #1a202c;" +
//                        "-fx-border-color: white;" +
//                        "-fx-border-radius: 10;" +
//                        "-fx-text-fill: white;" +
//                        "-fx-prompt-text-fill: gray;" +
//                        "-fx-padding: 10;"
//        );
//
//        // Login Button
//        loginButton = new Button("Login");
//        loginButton.setFont(new Font("Arial", 14));
//        loginButton.setStyle(
//                "-fx-background-color: white;" +
//                        "-fx-text-fill: #1a202c;" +
//                        "-fx-font-weight: bold;" +
//                        "-fx-border-radius: 10;" +
//                        "-fx-background-radius: 10;" +
//                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 2, 2);" +
//                        "-fx-padding: 10 20;"
//        );
//        loginButton.setOnMouseEntered(e -> loginButton.setStyle(
//                "-fx-background-color: #e6e6e6;" +
//                        "-fx-text-fill: #1a202c;" +
//                        "-fx-border-radius: 10;" +
//                        "-fx-background-radius: 10;" +
//                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 8, 0, 2, 2);" +
//                        "-fx-padding: 10 20;"
//        ));
//        loginButton.setOnMouseExited(e -> loginButton.setStyle(
//                "-fx-background-color: white;" +
//                        "-fx-text-fill: #1a202c;" +
//                        "-fx-border-radius: 10;" +
//                        "-fx-background-radius: 10;" +
//                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 2, 2);" +
//                        "-fx-padding: 10 20;"
//        ));
//
//        // New Account Button
//        newAccountButton = new Button("New Account");
//        newAccountButton.setFont(new Font("Arial", 14));
//        newAccountButton.setStyle(
//                "-fx-background-color: white;" +
//                        "-fx-text-fill: #1a202c;" +
//                        "-fx-font-weight: bold;" +
//                        "-fx-border-radius: 10;" +
//                        "-fx-background-radius: 10;" +
//                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 2, 2);" +
//                        "-fx-padding: 10 20;"
//        );
//
//        // Feedback Text
//        feedbackText = new Text();
//        feedbackText.setFont(new Font("Arial", 12));
//        feedbackText.setFill(Color.RED); // Feedback text in red for errors
//        feedbackText.setStyle("-fx-padding: 10;");
//
//        // Hooks
//        loginButton.setOnAction(e -> this.attemptLogin());
//        newAccountButton.setOnAction(e -> uiController.gotoNewAccount());
//        userIDField.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                passwordField.requestFocus();
//            }
//        });
//        passwordField.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                loginButton.fire();
//            }
//        });
//
//        // Add components to grid
//        gridPane.add(new Label("Username:"), 0, 0);
//        gridPane.add(userIDField, 1, 0);
//        gridPane.add(new Label("Password:"), 0, 1);
//        gridPane.add(passwordField, 1, 1);
//        gridPane.add(loginButton, 1, 2);
//        gridPane.add(newAccountButton, 1, 3);
//        gridPane.add(feedbackText, 1, 4);
//
//        // Outer container
//        VBox outerBox = new VBox(gridPane);
//        outerBox.setPadding(new Insets(30));
//        outerBox.setStyle("-fx-background-color: #1a202c;");
//        outerBox.setAlignment(Pos.CENTER);
//
//        return new Scene(outerBox, 400, 350);
//    }
//
//    private void attemptLogin() {
//        String userID = userIDField.getText();
//        String password = passwordField.getText();
//
//        String hashedPassword = LoginUtils.hashString(password);
//
//        if (DatabaseManager.getInstance().isCorrectPassword(userID, hashedPassword)) {
//            sessionManager.setUser(userID);
//            uiController.gotoMainMenu();
//        } else {
//            feedbackText.setText("Invalid username or password");
//            feedbackText.setFill(Color.RED);
//        }
//    }
//}
