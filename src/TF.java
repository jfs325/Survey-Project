import java.io.Serializable;
import java.util.ArrayList;

public class TF extends Question implements Serializable {
    public TF(String question) {
        this.question = question;
        this.choice = "Enter T or F";
        this.output = new Output();
    }

    public RCA askQuestion() {
        RCA rca = new RCA();
        while (1 == 1) {
            output.output("Enter you answer: ");
            String input = this.input.getString();
            if (!input.equals("T") && !input.equals("F")) {
                output.output("Please enter T or F");
            } else {
                rca.getResponseCorrectAnswer().add(input);
                break;
            }
        }
        return rca;
    }
}
