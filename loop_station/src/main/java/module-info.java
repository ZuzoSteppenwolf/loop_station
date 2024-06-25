module com.loop_station {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.pi4j;
    requires com.pi4j.plugin.raspberrypi;

    uses com.pi4j.extension.Extension;
    uses com.pi4j.provider.Provider;
    requires java.logging;
    opens com.loop_station to javafx.fxml;
    exports com.loop_station;
    
}