module com.deepseadevs.fisheatfish {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.deepseadevs.fisheatfish to javafx.fxml;
    exports com.deepseadevs.fisheatfish;
}