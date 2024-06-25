package gui;

import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class RewardScreenController {

    @FXML
    private ImageView reward1;
    @FXML
    private ImageView reward2;

    @FXML
    private Text CongratsText;

    @FXML
    private Text ChooseRewardText;

    private static final String QR_CODE_1 = "/photos/QRcode.png";
    private static final String QR_CODE_2 = "/photos/qr_code2.png";

    private boolean rewardChosen = false;

    public void setScene(Scene scene) {
        // Add key event handler to select the reward
        scene.addEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyPress);
    }

    private void handleKeyPress(KeyEvent event) {
        if (!rewardChosen) {
            switch (event.getCode()) {
                case Y:
                    selectReward(1);
                    break;
                case N:
                    selectReward(2);
                    break;
                default: {}
            }
        } else {
            // Any key press after reward is chosen goes to screensaver
            goToScreenSaver();
        }
    }

    private void selectReward(int rewardNumber) {
        try {
            String imagePath;
            if (rewardNumber == 1) {
                imagePath = QR_CODE_1;
                reward1.setImage(loadImage(imagePath));
            } else if (rewardNumber == 2) {
                imagePath = QR_CODE_1;
                reward2.setImage(loadImage(imagePath));
            }

            // Change text to indicate next step
            CongratsText.setText("Press any button to go to the home screen");
            ChooseRewardText.setVisible(false);

            rewardChosen = true;
        } catch (Exception e) {
            System.err.println("Error selecting reward " + rewardNumber + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Image loadImage(String path) {
        InputStream imageStream = getClass().getResourceAsStream(path);
        if (imageStream == null) {
            throw new IllegalArgumentException("Image not found: " + path);
        }
        return new Image(imageStream);
    }

    private void goToScreenSaver() {
        Stage stage = (Stage) CongratsText.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ScreenSaver.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1080, 1500);

            Stage screenSaverStage = new Stage();
            screenSaverStage.setScene(scene);
            screenSaverStage.setTitle("Screen Saver");

            // Position the window at the top of the screen
            screenSaverStage.setX((Screen.getPrimary().getBounds().getWidth() - screenSaverStage.getWidth()) / 2);
            screenSaverStage.setY(0);

            ScreenSaverController controller = loader.getController();
            controller.setScene(scene);

            screenSaverStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
