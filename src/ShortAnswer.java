import java.io.Serializable;

public class ShortAnswer extends Question implements Serializable {


    public ShortAnswer(String question) {
        this.question = question;
        this.choice = "Please answer in Short Answer format";
        this.output = new Output();
    }

    public void modifyQuestion() {
        while (1 == 1) {
            output.output("Enter q to modify question or a modify answer");
            String input = this.input.getString();
            if (input.equals("q")) {
                output.output("Enter new question: ");
                String newQuestion = this.input.getString();
                setQuestion(newQuestion);
            } else if (input.equals("a")) {
                output.output("Enter new answer: ");
                RCA rca = new RCA();
                String newAnswer = this.input.getString();
                rca.getResponseCorrectAnswer().add(newAnswer);
            } else {
                output.output("Invalid input. Try again");
            }
        }
    }

}
