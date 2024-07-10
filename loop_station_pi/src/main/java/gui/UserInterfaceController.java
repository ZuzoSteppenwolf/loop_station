//TODO delete dead code
package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;
    private final int requiredCorrectAnswers = 3;
    private final int totalQuestionsToAsk = 5;
    private boolean gameEnded = false;
    private boolean isFactScene = false;
    private Pools.Question currentQuestion = null;
    private Pools.Question lastQuestion = null;

    private List<Question> questions = new ArrayList<>(List.of(
            new Question("Is the sky blue?", "Yes", "No", "Yes",
                    "The sky appears blue because of the way sunlight interacts with Earth's atmosphere."),

            new Question("Is grass green?", "Yes", "No", "Yes",
                    "Grass is green due to the presence of chlorophyll."),

            new Question("Do cats meow?", "Yes", "No", "Yes",
                    "Cats meow as a way to communicate with humans."),

            new Question("Is 5 greater than 3?", "Yes", "No", "Yes",
                    "In basic arithmetic, 5 is greater than 3."),

            new Question("Is 2 less than 4?", "Yes", "No", "Yes",
                    "In basic arithmetic, 2 is less than 4."),

            new Question("Is 7 greater than 10?", "Yes", "No", "No",
                    "In basic arithmetic, 7 is less than 10."),

            new Question("Do birds fly?", "Yes", "No", "Yes",
                    "Most birds have the ability to fly."),

            new Question("Is water wet?", "Yes", "No", "Yes",
                    "Water makes things wet and is itself wet."),

            new Question("Do fish swim?", "Yes", "No", "Yes",
                    "Fish swim by moving their tails and fins."),

            new Question("Is fire hot?", "Yes", "No", "Yes",
                    "Fire is a source of heat."),

            new Question("Which is faster?", "Car", "Bicycle", "Car",
                    "Cars generally travel faster than bicycles."),

            new Question("Which is taller?", "Tree", "House", "Tree",
                    "Many trees grow taller than houses."),

            new Question("Which is heavier?", "Elephant", "Mouse", "Elephant",
                    "Elephants are significantly heavier than mice.")));

    private List<Question> selectedQuestions;

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

    private void updateQuestion() {
        if (currentQuestionIndex < Pools.getNumberOfTopics()) {
            
            currentQuestion = Pools.getQuestion(currentQuestionIndex);
            questionTxt.setText(currentQuestion.getQuestion());
            Random random = new Random();
            int randomInt = random.nextInt(2);
            String[] options = {currentQuestion.getCorrectAnswer(), currentQuestion.getWrongAnswer()};
            button1.setText(options[randomInt++]);
            button2.setText(options[randomInt % 2]);
            correctAnswersCounter.setText(correctAnswers + "/" + requiredCorrectAnswers);

        } else {
            endGame();
        }
    }

    private void showDescription() {
        funFactTxt.setText(currentQuestion.getDescription());
        button1.setText("Next");
        button2.setText("Next");
    }

    private void handleAnswer(String answer) {
        if (gameEnded) {
            return;
        }

        //Question currentQuestion = selectedQuestions.get(currentQuestionIndex);
        if (answer.equals(currentQuestion.getCorrectAnswer())) {
            correctAnswers++;
            confirmText.setText("Correct!");
        } else {
            confirmText.setText("Incorrect!");
        }
        currentQuestionIndex++;

        correctAnswersCounter.setText(correctAnswers + "/" + requiredCorrectAnswers);

        if (currentQuestionIndex >= totalQuestionsToAsk) {
            endGame();
        } else {
            showDescription();
        }
    }

    private void handleKeyPressed(KeyEvent event) {
        if (gameEnded) {
            switchToScreenSaver();
            return;
        }
        if (event.getCode() == KeyCode.Y || event.getCode() == KeyCode.N)
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

    private void endGame() {
        gameEnded = true;
        if (correctAnswers >= requiredCorrectAnswers) {
            showRewardScreen();
        } else {
            factSceneBottom.setVisible(false);
            funFactTxt.setText("Sorry, you lost :(");
            funFactField.setText("Press any button to go back to the home screen");
        }
    }

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

    private static class Question {
        private final String text;
        private final String optionA;
        private final String optionB;
        private final String correctAnswer;
        private final String funFact;

        public Question(String text, String optionA, String optionB, String correctAnswer, String funFact) {
            this.text = text;
            this.optionA = optionA;
            this.optionB = optionB;
            this.correctAnswer = correctAnswer;
            this.funFact = funFact;
        }

        public String getText() {
            return text;
        }

        public String getOptionA() {
            return optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public String getFunFact() {
            return funFact;
        }
    }

}
