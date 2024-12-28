package com.deepseadevs.fisheatfish.widgets.labels;

import com.deepseadevs.fisheatfish.widgets.GameStyles;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class GeneralLabel extends Label {
    public GeneralLabel(String text, Font font, String color) {
        super(text);
        setLabelStyle(font, color);
    }

    private void setLabelStyle(Font font, String color) {
        setFont(font);
        setStyle(String.format("""
                -fx-text-fill: %s;
                -fx-alignment: center;
                """, color));

    }
}
