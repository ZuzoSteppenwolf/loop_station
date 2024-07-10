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
        "Life Cycle of A Product", 
        "Why do we need circular economy?", 
        "Different Industries", 
        "Trash Seperation", 
        "Circular Consumption Options" };

    /* The Pools of Questions */
    private static final Map<Integer, List<Question>> POOLS = new HashMap<>( Map.of(
        0, new ArrayList<>( List.of(
            new Question("How can you extend the life of a product and its materials?", "Many people think that to extend the life of a product, they should simply reuse it as long as possible and recycle the materials. However the “9Rs” are the modern standards for sustainable production and consumption, where producers and consumers can have a greater positive impact.", 
            "Refuse, Rethink, Reduce, Reuse, Repair, Refurbish, Remanufacture, Repurpose, Recycle and Recover (9 Rs –  depending on the product)", new String[]{"Reuse and Recycle (2 Rs)", "Repurpose and Remanufacture (2 Rs)"}),                        
            new Question("Which of the following products is more environmentally friendly/sustainable?", "Composable paper cups might seem more sustainable at first in comparison to (reusable!) plastic cups, but in fact it’s the other way around. One-way paper cups contribute to deforestation, pollution and indirectly climate change.", 
            "Reusable Plastic Cup", new String[]{"Compostable Paper Cup"})
            
        )),
        1, new ArrayList<>( List.of(
            new Question("Where can you find microplastics in your day-to-day-life?", "Microplastics (tiny plastic particles) can be found in many everyday products such as toothpaste, facial scrubs, and synthetic clothing. We even consume 5g of (micro)plastics weekly, which is equivalent to a whole credit card.", 
            "Almost everywhere", new String[]{"On the beach, in the oceans and landfills (Mülldeponie)"}),
            new Question("Three big problems of the linear economy are…?", "A linear economy refers to a traditional economic model where resources are extracted, used to create products, and eventually disposed of as waste after their lifecycle ends. This approach prioritizes consumption and economic growth without sufficient consideration for resource conservation or environmental impacts.", 
            "extraction of natural resources, pollution and the production of enormous amounts of waste", new String[]{"No space for innovation, no interest in modernization, slow digitalization"})

        )),
        2, new ArrayList<>( List.of(
            new Question("How much water can you save if you buy a T-Shirt in a secondhand store?", "17 bathtubs or 2495 liters of water. That is the amount needed to produce one single cotton t-shirt. Instead of buying a new one, take a look in one of the many secondhand stores in Hamburg. Secondhand Shopping is one of the more sustainable ways of consuming fashion.", 
            "17 bathtubs full of water", new String[]{"a box with 20 water bottles", "a bathtub full of water"}),
            new Question("How much money can a single person save per month if this person uses alternatives like public transportation, the bike or car sharing services instead of using their own car?", "Explanation: Have you thought that a single person can save up to 400 Euros per month if this person uses public transportation and car sharing services instead of driving their own car? By the way, if you cycle instead of taking the car, you can save several hundred kilos of CO2 every year. The further the journey to work, the greater the savings potential.", 
            "300-400 Euro", new String[]{"50-100 Euro", "20-30 Euro"})

        )),
        3, new ArrayList<>( List.of(
            new Question("How many households could be supplied with energy, if all people in Germany separated their trash correctly?", "Currently 22% of organic waste is used for producing energy, but the potential is much higher. The problem is that most people do not separate their trash correctly, and often throw their organic waste in the residual waste bin (Restmüll), where the trash simply gets burned.", 
            "600000", new String[]{"50000", "100000", "200"}),
            new Question("How much percent of plastic waste are recycled in Germany per year?", "Plastic is a disposable product. Most of it is still burned, ends up in landfills or in the countryside and sea. In Germany, the \"official\" recycling rate for plastic is 39%, but the actual figure is far lower, experts say.", 
            "39%", new String[]{"75%", "56%", "64%"}),
            new Question("How many tons of packaging waste per day do German citizens produce by buying food and drinks to go?", "Huge amounts of plastic waste damage the environment and consume a lot of valuable resources. But did you know that there are six different providers of reusable take away packaging in Hamburg so you can save a lot of single-used plastic when you order food and drinks to go? They offer a diverse set of options - either for free or with a deposit system which refunds money in full.", 
            "< 700 tons", new String[]{"< 200 tons", "< 100 tons", "< 50 tons"})

        )),
        4, new ArrayList<>( List.of(
            new Question("How many “second hand stores” & eco-friendly clothes stores exist in Hamburg?", "Many people have heard about the harms of the fast-fashion industry, but don’t know where they should get more sustainable clothes, preferably affordable price otherwise. Luckily for the people of Hamburg there are many alternatives to the conventional fast-fashion stores, which consist of 33 sustainable clothing stores, and 117 second hand stores!!", 
            "150", new String[]{"35", "50", "70"})

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
     * Get the number of topics.
     * 
     * @return The number of topics.
     */
    static int getNumberOfTopics() {
        return TOPICS.length;
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
        String getDescription() {
            return description;
        }
    }
}
