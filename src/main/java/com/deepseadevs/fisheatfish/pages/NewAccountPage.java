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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class NewAccountPage extends BasePage {
    // Define UI components
    VBox mainBox;
    Label userIDLabel;
    Label displayNameLabel;
    Label passwordLabel;
    Label confirmPasswordLabel;
    Button createAccountButton;
    Button backToLoginButton;
    TextField userIDField;
    TextField displayNameField;
    PasswordField passwordField;
    PasswordField confirmPasswordField;
    Text feedbackText;
    VBox successOverlay;

    public NewAccountPage(UIController uiController, SessionManager sessionManager) {
        super(uiController, sessionManager);
    }

    protected Scene createScene() {
        // Welcome label
        Label welcomeLabel = new Label("CREATE ACCOUNT");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #b5c7eb;");

        // Main form layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #1a202c;");

        // Widgets and design
        userIDLabel = new Label("Account name:");
        userIDLabel.setFont(new Font("Arial", 16));
        userIDLabel.setStyle("""
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-text-fill: #b5c7eb;
        """);

        userIDField = new TextField();
        userIDField.setFont(new Font("Arial", 14));

        displayNameLabel = new Label("Displayed name:");
        displayNameLabel.setFont(new Font("Arial", 16));
        displayNameLabel.setStyle("""
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-text-fill: #b5c7eb;
        """);

        displayNameField = new TextField();
        displayNameField.setFont(new Font("Arial", 14));

        passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", 16));
        passwordLabel.setStyle("""
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-text-fill: #b5c7eb;
        """);

        passwordField = new PasswordField();
        passwordField.setFont(new Font("Arial", 14));

        confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Arial", 16));
        confirmPasswordLabel.setStyle("""
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-text-fill: #b5c7eb;
        """);

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setFont(new Font("Arial", 14));

        createAccountButton = new MainButton("Create Account");
        backToLoginButton = new MainButton("Back to Login");
        feedbackText = new Text();
        feedbackText.setFont(new Font("Arial", 12));
        feedbackText.setFill(Color.RED);

        // Hooks
        createAccountButton.setOnAction(e -> this.attemptCreateAccount());
        backToLoginButton.setOnAction(e -> uiController.gotoLogin());

        // events
        userIDField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                displayNameField.requestFocus();
            }
        });
        displayNameField.setOnKeyPressed(event -> {
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

        // Add nodes to grid
        gridPane.add(userIDLabel, 0, 0);
        gridPane.add(userIDField, 1, 0);
        gridPane.add(displayNameLabel, 0, 1);
        gridPane.add(displayNameField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(confirmPasswordLabel, 0, 3);
        gridPane.add(confirmPasswordField, 1, 3);
        gridPane.add(createAccountButton, 1, 4);
        gridPane.add(backToLoginButton, 0, 4);
        gridPane.add(feedbackText, 1, 5);

        // Main box for new account form
        mainBox = new VBox(20, welcomeLabel, gridPane);
        mainBox.setPadding(new Insets(30));
        mainBox.setStyle("-fx-background-color: #1a202c;");
        mainBox.setAlignment(Pos.CENTER);

        // Success overlay
        successOverlay = new VBox();
        successOverlay.setSpacing(20);
        successOverlay.setPadding(new Insets(20));
        successOverlay.setAlignment(Pos.CENTER);
        successOverlay.setStyle("-fx-background-color: #F0F8FF;");

        Text successMessage = new Text("Account created successfully!");
        successMessage.setFont(new Font("Arial", 18));
        successMessage.setStyle("-fx-font-weight: bold;");
        successMessage.setFill(Color.GREEN);

        Button overlayBackToLoginButton = new Button("Back to Login");
        overlayBackToLoginButton.setFont(new Font("Arial", 14));
        overlayBackToLoginButton.setStyle("-fx-background-color: #66ccff; -fx-text-fill: white;");
        overlayBackToLoginButton.setOnAction(e -> uiController.gotoLogin());

        successOverlay.getChildren().addAll(successMessage, overlayBackToLoginButton);
        successOverlay.setVisible(false);
        successOverlay.setDisable(true);

        // Wrap everything in a StackPane for overlay functionality
        StackPane root = new StackPane();
        root.getChildren().addAll(mainBox, successOverlay);

        return new Scene(root, 600, 400);
    }

    private void attemptCreateAccount() {
        String userID = userIDField.getText();
        String displayedName = displayNameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        String invalidNamePattern = ".*,.*";
        String userIDPattern = "[a-zA-Z0-9_]+";

        if (userID.isEmpty() || displayedName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            feedbackText.setText("All fields are required.");
        } else if (userID.length() > 20){
            feedbackText.setText("Account Name cannot be\nlonger than 20 characters");
        } else if (!userID.matches(userIDPattern)) {
            feedbackText.setText("Account Name can only\ncontain letters, numbers,\nand underscores.");
        } else if (displayedName.matches(invalidNamePattern)) {
            feedbackText.setText("Display name cannot contain\ncomma ',' character.");
        } else if (!password.equals(confirmPassword)) {
            feedbackText.setText("Passwords do not match.");
        } else if (password.length() < 4) {
            feedbackText.setText("Password must be at least 4 characters long");
        } else if (DatabaseManager.getInstance().userExists(userID)) {
            feedbackText.setText("Username already taken.");
        } else {
            disableCreateAccountInputs();
            String hashedPassword = LoginUtils.hashString(password);
            DatabaseManager.getInstance().createNewUser(userID, displayedName, hashedPassword);
            successOverlay.setDisable(false);
            successOverlay.setVisible(true);
            backToLoginButton.requestFocus();
        }
    }

    private void disableCreateAccountInputs() {
        mainBox.setDisable(true);
    }
}
