package gui;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ScreenSaverController implements Initializable {

    @FXML
    private Text screenSaverText;

    private final List<String> funFacts = List.of(
            "Honey never spoils.",
            "Bananas are berries.",
            "Koalas sleep around 18 hours a day.",
            "Octopuses have three hearts.",
            "There are more stars in the universe than grains of sand on Earth.",
            "A day on Venus is longer than a year on Venus.",
            "Humans share 50% of their DNA with bananas.",
            "A group of flamingos is called a 'flamboyance'.",
            "Bees can recognize human faces.",
            "Wombat poop is cube-shaped."
    );

    private Timeline factTimeline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the Timeline to change facts every 5 seconds
        factTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            screenSaverText.setText(funFacts.get(new Random().nextInt(funFacts.size())));
        }));
        factTimeline.setCycleCount(Timeline.INDEFINITE);
        factTimeline.play();

        // Display the initial fun fact
        screenSaverText.setText(funFacts.get(new Random().nextInt(funFacts.size())));
    }

    public void setScene(Scene scene) {
        // Add key event handler to switch to the game screen
        scene.addEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyPress);
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case Y: 
            case N: {
                factTimeline.stop();
                Stage stage = (Stage) screenSaverText.getScene().getWindow();
                stage.close();

                try {
                    // Load and show the game stage
                    Stage gameStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/gui/UserInterface.fxml"));
                    Scene scene = new Scene(root, 1080, 1920);
                    gameStage.setScene(scene);
                    gameStage.getScene().setCursor(Cursor.NONE);
                    gameStage.setTitle("Quiz Game");

                    gameStage.getScene().getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

                    // Set the size of the window
                    gameStage.setWidth(1080);
                    gameStage.setHeight(1920);

                    // Position the window at the top of the screen
                    gameStage.setX((Screen.getPrimary().getBounds().getWidth() - gameStage.getWidth()) / 2);
                    gameStage.setY(0);

                    gameStage.setFullScreenExitHint("");
                    gameStage.setFullScreen(true);

                    gameStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
            default: {}
        }
    }
}
