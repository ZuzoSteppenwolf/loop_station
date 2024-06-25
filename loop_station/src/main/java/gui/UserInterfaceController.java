package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UserInterfaceController {

    @FXML
    private Text questionTxt;
    @FXML
    private Text correctAnswersCounter;
    @FXML
    private Text funFactTxt;
    @FXML
    private Button button1;
    @FXML
    private Button button2;

    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;
    private final int requiredCorrectAnswers = 5;

    private List<Question> questions = new ArrayList<>(List.of(
            new Question("Is the sky blue?", "Yes", "No", "Yes"),
            new Question("Is grass green?", "Yes", "No", "Yes"),
            new Question("Do cats meow?", "Yes", "No", "Yes"),
            new Question("Is 5 greater than 3?", "Yes", "No", "Yes"),
            new Question("Is 2 less than 4?", "Yes", "No", "Yes"),
            new Question("Is 7 greater than 10?", "Yes", "No", "No"),
            new Question("Do birds fly?", "Yes", "No", "Yes"),
            new Question("Is water wet?", "Yes", "No", "Yes"),
            new Question("Do fish swim?", "Yes", "No", "Yes"),
            new Question("Is fire hot?", "Yes", "No", "Yes"),
            new Question("Which is faster?", "Car", "Bicycle", "Car"),
            new Question("Which is taller?", "Tree", "House", "Tree"),
            new Question("Which is heavier?", "Elephant", "Mouse", "Elephant")
    ));

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

    @FXML
    public void initialize() {
        // Shuffle the questions list
        Collections.shuffle(questions, new Random());

        updateQuestion();
        button1.setOnAction(e -> handleAnswer(button1.getText()));
        button2.setOnAction(e -> handleAnswer(button2.getText()));

        button1.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
            }
        });
    }

    private void updateQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionTxt.setText(currentQuestion.getText());
            button1.setText(currentQuestion.getOptionA());
            button2.setText(currentQuestion.getOptionB());
            correctAnswersCounter.setText(correctAnswers + "/" + requiredCorrectAnswers);
            funFactTxt.setText(funFacts.get(new Random().nextInt(funFacts.size())));
        } else {
            showRewardScreen();
        }
    }

    private void handleAnswer(String answer) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        if (answer.equals(currentQuestion.getCorrectAnswer())) {
            correctAnswers++;
        }
        currentQuestionIndex++;

        correctAnswersCounter.setText(correctAnswers + "/" + requiredCorrectAnswers);

        if (correctAnswers >= requiredCorrectAnswers) {
            showRewardScreen();
        } else {
            updateQuestion();
        }
    }

    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case Y -> handleAnswer(button1.getText());
            case N -> handleAnswer(button2.getText());
            default -> {}
        }
    }

    private void showRewardScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/RewardScreen.fxml"));
            Parent root = loader.load();
            RewardScreenController controller = loader.getController();

            Scene scene = new Scene(root, 1080, 1500);
            Stage rewardStage = new Stage();
            rewardStage.setScene(scene);
            rewardStage.setTitle("Choose Your Reward");

            controller.setScene(scene);

            // Close the current game stage
            Stage gameStage = (Stage) questionTxt.getScene().getWindow();
            gameStage.close();

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

        public Question(String text, String optionA, String optionB, String correctAnswer) {
            this.text = text;
            this.optionA = optionA;
            this.optionB = optionB;
            this.correctAnswer = correctAnswer;
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
    }
}
