package com.deepseadevs.fisheatfish.widgets;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class StyledButton extends Button {

    public StyledButton(String text) {
        super(text);
        setFont(new Font("Arial", 14));
        setStyle(defaultStyle());
        setupHoverEffects();
    }

    private String defaultStyle() {
        return "-fx-background-color: white;" +
                "-fx-text-fill: #1a202c;" +
                "-fx-font-weight: bold;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 2, 2);" +
                "-fx-padding: 10 20;";
    }

    private String hoverStyle() {
        return "-fx-background-color: #e6e6e6;" +
                "-fx-text-fill: #1a202c;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 8, 0, 2, 2);" +
                "-fx-padding: 10 20;";
    }

    private void setupHoverEffects() {
        setOnMouseEntered(e -> setStyle(hoverStyle()));
        setOnMouseExited(e -> setStyle(defaultStyle()));
    }
}





