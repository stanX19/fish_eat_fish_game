module com.deepseadevs.fisheatfish {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.deepseadevs.fisheatfish;
    exports com.deepseadevs.fisheatfish;
    opens com.deepseadevs.fisheatfish.game;
    exports com.deepseadevs.fisheatfish.game;
    opens com.deepseadevs.fisheatfish.game.fish;
    exports com.deepseadevs.fisheatfish.game.fish;
    opens com.deepseadevs.fisheatfish.game.level;
    exports com.deepseadevs.fisheatfish.game.level;
}