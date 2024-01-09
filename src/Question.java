import java.io.Serializable;
import java.util.ArrayList;

class Question implements Serializable {
    String question;
    String choice;

    RCA answers = new RCA();

    transient Output output = new Output();
    transient Input input = new Input();
    public void displayQuestion(Integer questionNumber) {
        this.output.output("Question " + questionNumber);
        this.output.output(question);
        this.output.output(choice);
        this.output.output("");

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(RCA rca) {
        this.answers = rca;
    }

    public void setAnswer(Integer index, String answer) {this.answers.getResponseCorrectAnswer().set(index, answer);}

    public void addAnswer(String answer) {
        this.answers.getResponseCorrectAnswer().add(answer);
    }

    public void modifyQuestion() {
        output.output("Enter new question: ");
        String newQuestion = input.getString();
        setQuestion(newQuestion);
    }


    public RCA askQuestion() {
        output.output("Enter you answer: ");
        String input = this.input.getString();
        RCA rca = new RCA();
        rca.getResponseCorrectAnswer().add(input);
        return rca;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public void deserialize() {
        setInput(new Input());
        setOutput(new Output());
    }

    public void displayAnswer() {
        if (getAnswers().getResponseCorrectAnswer().size() == 0) {}
        else {
            this.output.output("Given answer:\n" + getAnswers().getResponseCorrectAnswer().get(0));
        }
    }

    public RCA getAnswers() {
        return this.answers;
    }

    ArrayList<String> getChoices() {
        return null;
    }

}
