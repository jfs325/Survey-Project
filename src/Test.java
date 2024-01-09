import java.io.Serializable;
import java.util.ArrayList;

public class Test extends Survey implements Serializable {

    private ArrayList<RCA> correctAnswers;
    float grade;

    Integer numQuestions = 0;

    public Test(String surveyName) {
        super(surveyName);
        this.correctAnswers = new ArrayList<>();
    }

    public ArrayList<RCA> getCorrectAnswers() {
        return this.correctAnswers;
    }

    public Integer getNumQuestions() {
        return this.numQuestions;
    }

    public void setNumQuestions(Integer numQuestions) {
        this.numQuestions = numQuestions;
    }

    public void setCorrectAnswers(ArrayList<RCA> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public float getGrade() {
        return this.grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    @Override
    public void createTF() {
        super.createTF();
        output.output("Now enter the correct answer: ");
        RCA correctAnswer = questions.get(questions.size() - 1).askQuestion();
        getCorrectAnswers().add(correctAnswer);
    }

    @Override
    public void createShortAnswer() {
        super.createShortAnswer();
        output.output("Now enter the correct answer: ");
        RCA correctAnswer = questions.get(questions.size() - 1).askQuestion();
        getCorrectAnswers().add(correctAnswer);
    }

    @Override
    public void createMatching() {
        super.createMatching();
        output.output("Now enter the correct answer: ");
        RCA correctAnswer = questions.get(questions.size() - 1).askQuestion();
        getCorrectAnswers().add(correctAnswer);
    }

    @Override
    public void createEssay() {
        super.createEssay();
        RCA rca = new RCA();
        rca.getResponseCorrectAnswer().add("Essay is not graded");
        this.correctAnswers.add(rca);
    }

    @Override
    public void createMultipleChoice() {
        super.createMultipleChoice();
        output.output("Now enter the correct answer: ");
        RCA correctAnswer = getQuestions().get(questions.size() - 1).askQuestion();
        getCorrectAnswers().add(correctAnswer);
        // ask for answers until they're done
    }

    @Override
    public void createValidDate() {
        super.createValidDate();
        output.output("Now enter the correct answer: ");
        RCA correctAnswer = getQuestions().get(questions.size() - 1).askQuestion();
        getCorrectAnswers().add(correctAnswer);
    }


    public void displayTestWithCorrectAnswers() {
        for (int i = 0; i < questions.size(); i++) {
            questions.get(i).displayQuestion(i + 1);
            correctAnswers.get(i).displayAnswer();
        }
    }

    public void takeSurvey() {
        super.takeSurvey();
    }

    public void modifyCorrectAnswers() {
        while (1 == 1) {
            try {
                displayTestWithCorrectAnswers();
                output.output("Enter question number to modify its correct answer, enter 0 to exit modifying answers");
                String choice = input.getString();
                Integer choiceNum = Integer.parseInt(choice);
                if (choiceNum > 0 && choiceNum < questions.size() + 1) {
                    RCA correctAnswer = questions.get(questions.size() - 1).askQuestion();
                    getCorrectAnswers().add(correctAnswer);
                } else if (choiceNum.equals(0)) {
                    break;
                } else {
                    output.output("Error. Please enter a number in range");
                }
            } catch (Exception e) {
                output.output("Error. Please enter an integer");
            }
        }
    }

    public void gradeTest() {
        float questionWeight = (float) 100 / getNumQuestions();
        float percentUngraded = 0;
        Integer numEssay = 0;
        for (int i = 0; i < getQuestions().size(); i++) {
            if (Essay.class.isInstance(getQuestions().get(i))) {
                percentUngraded += questionWeight;
                numEssay++;
            } else if (getQuestions().get(i).getAnswers().isEqual(getCorrectAnswers().get(i))) {
                setGrade(getGrade() + questionWeight);
            }
        }
        output.output("Your grade is " + getGrade() + "%.");
        if (numEssay > 1) {
            output.output(percentUngraded + "% is ungraded because you have " + numEssay + " essay questions that cannot be graded because my algorithm isn't that good. The singularity is farther away than we think . . .");
        } else if (numEssay > 0) {
            output.output(percentUngraded + "% is ungraded because you have " + numEssay + " essay question that cannot be graded because my algorithm isn't that good. The singularity is farther away than we think . . .");
        }

    }
}
