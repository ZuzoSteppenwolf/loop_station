/*
 * Class to provide 5 pools of Topics. Each Topic has erveral questions.
 * The questions have one correct answer and x incorrect answers.
 * The questions are randomly selected from the pool.
 * 
 * @author Marvin Wollbrück
 */
package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class Pools {
    /* The Topics */
    private static final String[] TOPICS = new String[] { 
        "Life cycle of a product", 
        "Why do we need circular economy?", 
        "Industries", 
        "Trash separation", 
        "Circular consumption options" };
    // TODO Questions may are not in correct topic.
    // TODO add more questions
    /* The Pools of Questions */
    private static final Map<Integer, List<Question>> POOLS = new HashMap<>( Map.of(
        1, new ArrayList<>( List.of(
            new Question("How many tons of packaging waste per day do German citizens produce by buying food and drinks to go?", "Huge amounts of plastic waste damage the environment and consume a lot of valuable resources. But did you know that there are six different providers of reusable take away packaging in Hamburg so you can save a lot of single-used plastic when you order food and drinks to go? They offer a diverse set of options - either for free or with a deposit system which refunds money in full.", 
            "< 700 tons", new String[]{"< 200 tons", "< 100 tons"}),                        
            new Question("Q1", "D1", 
            "cA1", new String[]{"wA1"})
            
        )),
        2, new ArrayList<>( List.of(
            new Question("Three big problems of the linear economy are…?", "A linear economy refers to a traditional economic model where resources are extracted, used to create products, and eventually disposed of as waste after their lifecycle ends. This approach prioritizes consumption and economic growth without sufficient consideration for resource conservation or environmental impacts.", 
            "extraction of natural resources, pollution and the production of enormous amounts of waste", new String[]{"No space for innovation, no interest in modernization, slow digitalization"}),
            new Question("Q2", "D2", 
            "cA2", new String[]{"wA2"})

        )),
        3, new ArrayList<>( List.of(
            new Question("How much water can you save if you buy a T-Shirt in a secondhand store?", "17 bathtubs or 2495 liters of water. That is the amount needed to produce one single cotton t-shirt. Instead of buying a new one, take a look in one of the many secondhand stores in Hamburg. Secondhand Shopping is one of the more sustainable ways of consuming fashion.", 
            "17 bathtubs full of water", new String[]{"a box with 20 water bottles"}),
            new Question("Q3", "D3", 
            "cA3", new String[]{"wA3"})

        )),
        4, new ArrayList<>( List.of(
            new Question("How much percent of plastic waste are recycled in Germany per year?", "Plastic is a disposable product. Most of it is still burned, ends up in landfills or in the countryside and sea. In Germany, the \"official\" recycling rate for plastic is 39%, but the actual figure is far lower, experts say.", 
            "39%", new String[]{"75%"}),
            new Question("Q4", "D4", 
            "cA4", new String[]{"wA4"})

        )),
        5, new ArrayList<>( List.of(
            new Question("How much money can a single person save per month if this person uses alternatives like public transportation, the bike or car sharing services instead of using their own car?", "Have you thought that a single person can save up to 400 Euros per month if this person uses public transportation and car sharing services instead of driving their own car? By the way, if you cycle instead of taking the car, you can save several hundred kilos of CO2 every year. The further the journey to work, the greater the savings potential.", 
            "300-400 Euro ", new String[]{"50-100 Euro", "200-300 Euro"}),
            new Question("Q5", "D5", 
            "cA5", new String[]{"wA5"})

        ))	
        
    ));

    /*
     * Get a random question from the pool.
     * 
     * @param pool The pool to get the question from.
     * @return A random question from the pool.
     */
    static Question getQuestion(int pool) {
        Random random = new Random();
        List<Question> questions = POOLS.get(pool);

        assert questions != null;
        return questions.get(random.nextInt(questions.size()));
    }

    /*
     * Get a topic by index.
     * 
     * @param index The index of the topic.
     * 
     * @return The topics.
     */
    static String getTopic(int index) {
        assert index >= 0 && index < TOPICS.length;
        return TOPICS[index];
    }



    /*
     * Class to represent a question.
     * Wrong answers are randomly selected from the pool of wrong answers.
     */
    static class Question {
        private final String question;
        private final String[] wrongAnswers;
        private final String correctAnswer;
        private final String description;

        /*
         * Create a new question.
         * 
         * @param question The question.
         * @param description The description of the question.
         * @param correctAnswer The correct answer.
         * @param wrongAnswers The wrong answers.
         */
        Question(String question, String description, String correctAnswer, String[] wrongAnswers) {
            this.question = question;
            this.wrongAnswers = wrongAnswers;
            this.correctAnswer = correctAnswer;
            this.description = description;
        }

        /*
         * Get the question.
         * 
         * @return The question.
         */
        String getQuestion() {
            return question;
        }

        /*
         * Get a wrong answer.
         * 
         * @return A wrong answer.
         */
        String getWrongAnswer() {
            Random random = new Random();
            
            assert wrongAnswers.length > 0;
            return wrongAnswers[random.nextInt(wrongAnswers.length)];
        }

        /*
         * Get the correct answer.
         * 
         * @return The correct answer.
         */
        String getCorrectAnswer() {
            return correctAnswer;
        }

        /*
         * Get the description of the question.
         * 
         * @return The description of the question.
         */
        String description() {
            return description;
        }
    }
}
