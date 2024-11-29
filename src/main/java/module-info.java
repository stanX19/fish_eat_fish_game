module com.deepseadevs.fisheatfish {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.deepseadevs.fisheatfish to javafx.fxml;
    exports com.deepseadevs.fisheatfish;
    exports com.deepseadevs.fisheatfish.game;
}