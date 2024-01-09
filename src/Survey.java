import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
//TODO max multiple choice choices?
public class Survey implements Serializable {

    ArrayList<Question> questions;
    String surveyName;
    transient Output output = new Output();

    private Integer surveyNumber = 1;

    transient Input input = new Input();
    public Survey(String surveyName) {
        this.surveyName = surveyName;
        this.questions = new ArrayList<Question>();
    }

    public void setSurveyNumber(Integer surveyNumber) {
        this.surveyNumber = surveyNumber;
    }

    public Integer getSurveyNumber() {
        return this.surveyNumber;
    }

    public void createTF() {
        output.output("Enter True/False prompt: ");
        String question = input.getLine();
        Question TF = new TF(question);
        this.questions.add(TF);
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void createMultipleChoice() {
        boolean done = false;
        ArrayList<String> choices = new ArrayList<>();
        output.output("Enter multiple choice prompt: ");
        String question = input.getLine();
        while (done == false) {
        output.output("Enter number of choices");
            try {
                String choicesString = input.getString();
                Integer numChoices = Integer.parseInt(choicesString);
                for (int i = 1; i < numChoices + 1; i++) {
                    output.output("Enter choice " + i);
                    String choice = input.getLine();
                    choices.add(choice);
                }
                Question multipleChoice = new MultipleChoice(question, choices);
                this.questions.add(multipleChoice);
                done = true;
            } catch (Exception e) {
                output.output("Invalid Input. Please enter an integer ");
            }
        }
    }

    public void createShortAnswer() {
        output.output("Enter Short Answer prompt: ");
        String question = input.getLine();
        ShortAnswer shortAnswer = new ShortAnswer(question);
        this.questions.add(shortAnswer);

    }

    public void createEssay() {
        output.output("Enter Essay prompt: ");
        String question = input.getLine();
        Essay essay = new Essay(question);
        this.questions.add(essay);
    }

    public void createValidDate() {
        output.output("Enter ValidDate prompt: ");
        String question = input.getLine();
        ValidDate validDate = new ValidDate(question);
        this.questions.add(validDate);
    }


    public void createMatching() {
        Integer pairs;
        output.output("Enter Matching prompt: ");
        String question = input.getLine();
        while (1 == 1) {
            try {
                output.output("Enter number of matching pairs: ");
                String pairsString = input.getString();
                pairs = Integer.parseInt(pairsString);
                ArrayList<String> termList = new ArrayList<>();
                ArrayList<String> definitionList = new ArrayList<>();
                for (int i = 1; i < pairs + 1; i++) {
                    output.output("Enter term " + i + ": ");
                    String term = this.input.getLine();
                    termList.add(term);
                    output.output("Enter definition " + ((char) (64 + i)) + ": ");
                    String definition = this.input.getLine();
                    definitionList.add(definition);
                }
                Matching matching = new Matching(question, termList, definitionList);
                this.questions.add(matching);
                break;
            } catch (Exception e) {
                output.output("Error. Please enter an integer");
            }
        }
    }


    public void modifySurvey() {
        while (1 == 1) {
            output.output("Enter the question number you wish to modify. Type 0 to quit modifying");
            Integer questionNum = input.getInteger();
            if (questionNum == 0) {
                break;
            } else if (questionNum > 0 && questionNum < questions.size() + 1) {
                this.questions.get(questionNum - 1).displayQuestion(questionNum);
                this.questions.get(questionNum - 1).modifyQuestion();
            } else {
            }

        }
    }

    public void takeSurvey() {
        Integer i = 1;
        for (Question question : this.questions) {
            question.displayQuestion(i);
            RCA response = question.askQuestion();
            question.setAnswers(response);
            i++;
        }
    }

    // reinitialize all transient attributes for questions
    public void deserializeQuestions() {
        //loop through questions.initialize
        for (Question question : this.questions) {
            question.deserialize();
        }
    }

    public String getSurveyName() {
        return this.surveyName;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public void setOutput(Output output) {
        this.output = output;
    }
}
