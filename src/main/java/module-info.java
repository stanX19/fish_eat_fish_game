module com.deepseadevs.fisheatfish {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.deepseadevs.fisheatfish;
    opens com.deepseadevs.fisheatfish.game;
    exports com.deepseadevs.fisheatfish;
}