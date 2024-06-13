module com.loop_station {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    opens com.loop_station to javafx.fxml;
    exports com.loop_station;
}