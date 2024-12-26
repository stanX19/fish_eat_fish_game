package com.deepseadevs.fisheatfish.widgets;

import javafx.scene.text.Font;

public class GameStyles {
    public static final String MAIN_COLOR = "#3b82f6"; // Ocean Blue
    public static final String SECONDARY_COLOR = "#00d1d1"; // Turquoise
    public static final String NEUTRAL_COLOR = "#f3f4f6"; // Light Gray
    public static final String TEXT_COLOR = "#ffffff"; // White
    public static final String ACCENT_COLOR = "#f97316"; // Coral Orange
    public static final String BACKGROUND_COLOR = "#1e293b"; // Deep Sea Navy

    public static final Font MAIN_FONT = Font.font("Arial", 16);
    public static final Font TITLE_FONT = Font.font("Arial", 24);
    public static final Font LABEL_FONT = Font.font("Arial", 14);

    public static Font loadFont(String path, int size) {
        return Font.loadFont(GameStyles.class.getResourceAsStream(path), size);
    }
}
