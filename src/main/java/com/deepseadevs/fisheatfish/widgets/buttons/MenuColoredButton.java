package com.deepseadevs.fisheatfish.widgets.buttons;

import com.deepseadevs.fisheatfish.widgets.GameStyles;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class MenuColoredButton extends Button {
    public MenuColoredButton(String text, String color) {
        super(text);
        setFont(Font.font("Arial", 14));
        setButtonStyle(color);
    }

    private void setButtonStyle(String color) {
        String defaultStyle = getStyleWithColor(color);
        String hoverStyle = getStyleWithColor(String.format("derive(%s, -20%%)", color));
        setStyle(defaultStyle);

        setOnMouseEntered(e -> setStyle(hoverStyle));
        setOnMouseExited(e -> setStyle(defaultStyle));
    }

    private String getStyleWithColor(String color) {
        return String.format("""
                -fx-background-color: %s;
                -fx-text-fill: %s;
                -fx-font-size: 16px;
                -fx-padding: 12px 24px;
                -fx-background-radius: 8px;
                -fx-cursor: hand;
                -fx-min-width: 300px;
                """, color, GameStyles.TEXT_COLOR);
    }
}
