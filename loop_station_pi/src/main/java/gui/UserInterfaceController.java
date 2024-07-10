/*
 * This file is the controller for the main game screen.
 * 
 * @author Klaus Xhoxhi, Marvin Wollbrück
 */
package gui;

import java.io.IOException;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserInterfaceController {

    @FXML
    private VBox questionScene;
    @FXML
    private VBox factScene;
    @FXML
    private HBox questionSceneBottom;
    @FXML
    private VBox factSceneBottom;
    @FXML
    private Text questionTxt;
    @FXML
    private Text correctAnswersCounter;
    @FXML
    private Text funFactTxt;
    @FXML
    private Text funFactField;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Text confirmText;

    /* The Index of the current Question */
    private int currentQuestionIndex = 0;
    /* Counter for correct answers */
    private int correctAnswers = 0;
    /* const Number of correct answers required to win the game */
    private final int requiredCorrectAnswers = 3;
    /* const Number of total questions to ask */
    private final int totalQuestionsToAsk = 5;
    /* Boolean to check if the game has ended */
    private boolean gameEnded = false;
    /* Boolean to check if the current scene is a fact scene */
    private boolean isFactScene = false;
    /* The current question */
    private Pools.Question currentQuestion = null;

    @FXML
    public void initialize() {


        // sets the visibility of the question and fact scenes
        isFactScene = false;
        setFactScene(isFactScene);

        updateQuestion();
        button1.setOnAction(e -> handleAnswer(button1.getText()));
        button2.setOnAction(e -> handleAnswer(button2.getText()));

        button1.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyPressed);
            }
        });

    }

    /*
     * Set the visibility of the question and fact scenes.
     * 
     * @param isScene True if the fact scene should be visible, false otherwise.
     */
    private void setFactScene(boolean isScene) {
        questionScene.setVisible(!isScene);
        questionSceneBottom.setVisible(!isScene);
        factScene.setVisible(isScene);
        factSceneBottom.setVisible(isScene);
    }

    /*
     * Update the question and the answers.
     */
    private void updateQuestion() {
        // Check if there are more questions to ask
        if (currentQuestionIndex < Pools.getNumberOfTopics()) {
            
            currentQuestion = Pools.getQuestion(currentQuestionIndex);
            questionTxt.setText(currentQuestion.getQuestion());

            // Randomly set the correct and wrong answers
            Random random = new Random();
            int randomInt = random.nextInt(2);
            String[] options = {currentQuestion.getCorrectAnswer(), currentQuestion.getWrongAnswer()};
            button1.setText(options[randomInt++]);
            button2.setText(options[randomInt % 2]);

            // show the number of correct answers
            correctAnswersCounter.setText(correctAnswers + "/" + requiredCorrectAnswers);

        } else {
            endGame();
        }
    }

    /*
     * Show the description of the question.
     */
    private void showDescription() {
        funFactTxt.setText(currentQuestion.getDescription());
        button1.setText("Next");
        button2.setText("Next");
    }

    /*
     * Handle the answer of the user.
     * If the answer is correct, increment the 'correctAnswers' counter.
     * Show a message if the answer is correct or incorrect.
     * Increment the 'currentQuestionIndex' counter.
     * 
     * @param answer The answer of the user.
     */
    private void handleAnswer(String answer) {
        if (gameEnded) {
            return;
        }

        // Check if the answer is correct
        if (answer.equals(currentQuestion.getCorrectAnswer())) {
            correctAnswers++;
            confirmText.setText("Correct!");
        } else {
            confirmText.setText("Incorrect!");
        }
        currentQuestionIndex++;

        correctAnswersCounter.setText(correctAnswers + "/" + requiredCorrectAnswers);

        // Check if the game has ended
        if (currentQuestionIndex >= totalQuestionsToAsk) {
            endGame();
        } else {
            showDescription();
        }
    }

    /*
     * Handle the key pressed event.
     * Valid keys are Y and N.
     * 
     * @param event The key event.
     */
    private void handleKeyPressed(KeyEvent event) {
        if (gameEnded) {
            switchToScreenSaver();
            return;
        }

        // Check if the user has answered the question
        if (event.getCode() == KeyCode.Y || event.getCode() == KeyCode.N)
            // Check which scene is Active
            if (isFactScene) {
                isFactScene = false;
                setFactScene(isFactScene);
                updateQuestion();

            } else {
                isFactScene = true;
                setFactScene(isFactScene);

                switch (event.getCode()) {
                    case Y:
                        handleAnswer(button1.getText());
                        break;
                    case N:
                        handleAnswer(button2.getText());
                        break;
                    default: {
                    }
                }
            }
    }

    /*
     * End the game.
     * If the user has answered enough questions correctly, show the reward screen.
     * Otherwise, show a message that the user has lost.
     */
    private void endGame() {
        gameEnded = true;
        // Check if the user has answered enough questions correctly
        if (correctAnswers >= requiredCorrectAnswers) {
            showRewardScreen();
        } else {
            factSceneBottom.setVisible(false);
            funFactTxt.setText("Sorry, you lost :(");
            funFactField.setText("Press any button to go back to the home screen");
        }
    }

    /*
     * Switch to the screen saver.
     * Current Stage is closed and the screen saver is shown.
     */
    private void switchToScreenSaver() {
        try {
            Stage stage = (Stage) questionTxt.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ScreenSaver.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1080, 1500);

            Stage screenSaverStage = new Stage();
            screenSaverStage.setScene(scene);
            screenSaverStage.getScene().setCursor(Cursor.NONE);
            screenSaverStage.setTitle("Screen Saver");

            screenSaverStage.getScene().getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

            screenSaverStage.setFullScreenExitHint("");
            screenSaverStage.setFullScreen(true);

            ScreenSaverController controller = loader.getController();
            controller.setScene(scene);

            screenSaverStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Show the reward screen.
     * Current Stage is closed and the reward screen is shown.
     */
    private void showRewardScreen() {
        try {
            Stage gameStage = (Stage) questionTxt.getScene().getWindow();
            gameStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/RewardScreen.fxml"));
            Parent root = loader.load();
            RewardScreenController controller = loader.getController();

            Scene scene = new Scene(root, 1080, 1920);
            Stage rewardStage = new Stage();
            rewardStage.setScene(scene);
            rewardStage.getScene().setCursor(Cursor.NONE);
            rewardStage.setTitle("Choose Your Reward");

            rewardStage.setFullScreenExitHint("");
            rewardStage.setFullScreen(true);

            controller.setScene(scene);

            rewardStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
