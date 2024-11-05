package com.deepseadevs.fisheatfish;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPage {
    private final UIController uiController;
    private final SessionManager sessionManager;
    private Scene scene;

    Label usernameLabel;
    Label passwordLabel;
    Button loginButton;
    TextField usernameField;
    PasswordField passwordField;
    Text feedbackText;

    public LoginPage(UIController uiController, SessionManager sessionManager) {
        this.uiController = uiController;
        this.sessionManager = sessionManager;
        createScene();
    }

    public void show(Stage stage) {
        stage.setScene(scene);
    }

    private void attempt_login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (DatabaseManager.getInstance().isCorrectPassword(username, password)) {
            sessionManager.setUser(username);
            uiController.gotoMainMenu();
        } else {
            feedbackText.setText("Invalid username or password");
        }
    }

    private void createScene() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        usernameLabel = new Label("Username:");
        usernameField = new TextField();
        passwordLabel = new Label("Password:");
        passwordField = new PasswordField();
        loginButton = new Button("Login");
        feedbackText = new Text();

        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 1, 2);
        gridPane.add(feedbackText, 1, 3);

        loginButton.setOnAction(e -> this.attempt_login());

        scene = new Scene(gridPane, 400, 300);
    }
}
