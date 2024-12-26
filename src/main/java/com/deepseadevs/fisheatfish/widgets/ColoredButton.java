package com.deepseadevs.fisheatfish.widgets;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class ColoredButton extends Button {

    public ColoredButton(String text) {
        super(text);
        setFont(new Font("Arial", 14));
        setStyle(defaultStyle());
        setupHoverEffects();
    }

    private String defaultStyle() {
//        return "-fx-background-color: white;" +
//                "-fx-text-fill: #1a202c;" +
//                "-fx-font-weight: bold;" +
//                "-fx-border-radius: 10;" +
//                "-fx-background-radius: 10;" +
//                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 2, 2);" +
//                "-fx-padding: 10 20;";
        return """
            -fx-background-color: #3b82f6;
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-padding: 10px 20px;
            -fx-background-radius: 8px;
            -fx-cursor: hand;
            """;
    }

    private String hoverStyle() {
        return """
        -fx-background-color: derive(#3b82f6, -20%);
        -fx-text-fill: white;
        -fx-font-size: 16px;
        -fx-padding: 10px 20px;
        -fx-background-radius: 8px;
        -fx-cursor: hand;
        """;
}

private void setupHoverEffects() {
    setOnMouseEntered(e -> setStyle(hoverStyle()));
    setOnMouseExited(e -> setStyle(defaultStyle()));
}


public class MainButton extends ColoredButton {
    public MainButton(String text) {
        super(text);
        // Override the style of ColoredButton with MainButton-specific styles
        setFont(new Font("Arial", 16)); // Larger font size for MainButton
        setStyle(mainButtonStyle());
        setupHoverEffects(); // Ensure hover effects still work
    }

    // Define MainButton-specific default style
    private String mainButtonStyle() {
        return """
            -fx-background-color: #1d4ed8; /* Darker blue */
            -fx-text-fill: white;
            -fx-font-size: 18px;
            -fx-font-weight: bold;
            -fx-padding: 12px 24px; /* Slightly larger padding */
            -fx-background-radius: 10px;
            -fx-cursor: hand;
            -fx-border-color: white;
            -fx-border-width: 2px;
            -fx-border-radius: 10px;
            """;
    }

    // Define MainButton-specific hover style
    protected String hoverStyle() {
        return """
            -fx-background-color: derive(#1d4ed8, -20%);
            -fx-text-fill: white;
            -fx-font-size: 18px;
            -fx-padding: 12px 24px;
            -fx-background-radius: 10px;
            -fx-border-color: white;
            -fx-border-width: 2px;
            -fx-border-radius: 10px;
            -fx-cursor: hand;
            """;
    }
}
}





