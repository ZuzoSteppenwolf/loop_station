package gui;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class QuizGame extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            showScreenSaverStage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showScreenSaverStage() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ScreenSaver.fxml"));
        Parent root = loader.load();
        ScreenSaverController controller = loader.getController();

        if (controller == null) {
            throw new IllegalStateException("Controller is null. Check your FXML file and ensure the fx:controller attribute is correctly set.");
        }

        Scene scene = new Scene(root, 1080, 1920);

        Stage screenSaverStage = new Stage();
        screenSaverStage.setScene(scene);
        screenSaverStage.getScene().setCursor(Cursor.NONE);
        screenSaverStage.setTitle("Screen Saver");
        
        // style the window
        URL style = getClass().getResource("/styles/style.css");
        if (style == null) {
            throw new IllegalArgumentException("Stylesheet not found: /styles/style.css");
        }
        screenSaverStage.getScene().getStylesheets().add(style.toExternalForm());

        // Set the size of the window
        screenSaverStage.setWidth(1080);
        screenSaverStage.setHeight(1920);

        // Position the window at the top of the screen
        screenSaverStage.setX((Screen.getPrimary().getBounds().getWidth() - screenSaverStage.getWidth()) / 2);
        screenSaverStage.setY(0);
        
        screenSaverStage.setFullScreenExitHint("");
        screenSaverStage.setFullScreen(true);

        // Set the scene in the controller to add the key event handler
        controller.setScene(scene);

        screenSaverStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
