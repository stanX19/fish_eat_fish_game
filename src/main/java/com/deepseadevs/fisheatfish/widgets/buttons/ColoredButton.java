package com.deepseadevs.fisheatfish.widgets.buttons;

import com.deepseadevs.fisheatfish.widgets.GameStyles;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class ColoredButton extends Button {
    public ColoredButton(String text, String color) {
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
                -fx-font-weight: bold;
                -fx-padding: 5px 10px;
                -fx-background-radius: 8px;
                -fx-cursor: hand;
                """, color, GameStyles.TEXT_COLOR);
    }
}
