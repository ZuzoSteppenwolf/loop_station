/*
 * This class is the controller for the reward screen.
 * It handles the selection of a reward and the transition to the screen saver.
 * 
 * @author Klaus Xhoxhi, Marvin Wollbrück
 */
package gui;

import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
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

    /* Path for the first QR-Code */
    private static final String QR_CODE_1 = "/photos/QRcode.png";
    /* Path for the second QR-Code */
    private static final String QR_CODE_2 = "/photos/qr_code2.png";

    /* if a reward was chosen */
    private boolean rewardChosen = false;

     /*
     * Set the eventHandler of the Scene
     * 
     * @param scene The Scene to set the eventHandler to
     */
    public void setScene(Scene scene) {
        // Add key event handler to select the reward
        scene.addEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyPress);
    }

    /*
     * Handle the key press event
     * By pressing 'Y' the first reward is selected
     * and by pressing 'N' the second reward is selected
     * 
     * @param event The KeyEvent to handle
     */
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

    /*
     * Select a reward by its number
     * 
     * @param rewardNumber The number of the reward to select
     */
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

    /*
     * Load an image from the resources folder
     * 
     * @param path The path to the image
     * 
     * @return The loaded image
     */
    private Image loadImage(String path) {
        InputStream imageStream = getClass().getResourceAsStream(path);
        if (imageStream == null) {
            throw new IllegalArgumentException("Image not found: " + path);
        }
        return new Image(imageStream);
    }

    /*
     * Go to the screen saver
     */
    private void goToScreenSaver() {
        Stage stage = (Stage) CongratsText.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ScreenSaver.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1080, 1500);

            Stage screenSaverStage = new Stage();
            screenSaverStage.setScene(scene);
            screenSaverStage.getScene().setCursor(Cursor.NONE);
            screenSaverStage.setTitle("Screen Saver");

            screenSaverStage.getScene().getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

            // Position the window at the top of the screen
            screenSaverStage.setX((Screen.getPrimary().getBounds().getWidth() - screenSaverStage.getWidth()) / 2);
            screenSaverStage.setY(0);

            screenSaverStage.setFullScreenExitHint("");
            screenSaverStage.setFullScreen(true);

            ScreenSaverController controller = loader.getController();
            controller.setScene(scene);

            

            screenSaverStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
