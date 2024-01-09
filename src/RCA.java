import java.io.Serializable;
import java.util.ArrayList;

public class RCA implements Serializable {

    Output output = new Output();
    ArrayList<String> responseCorrectAnswer;

    public RCA() {
        this.responseCorrectAnswer = new ArrayList<>();
    }
    public RCA(ArrayList<String> rca) {
        this.responseCorrectAnswer = rca;
    }

    public ArrayList<String> getResponseCorrectAnswer() {
        return this.responseCorrectAnswer;
    }

    public void setResponseCorrectAnswer(ArrayList<String> responseCorrectAnswer) {
        this.responseCorrectAnswer = responseCorrectAnswer;
    }

//    public boolean isEqual(RCA other) {
//        if (other.getResponseCorrectAnswer().equals(getResponseCorrectAnswer())) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    public void displayAnswer() {
        for (int i = 0; i < responseCorrectAnswer.size(); i++) {
            output.output("Correct Answer(s)");
            output.output((i + 1) + ": " + getResponseCorrectAnswer().get(i));
        }
    }

    public boolean isEqual(RCA rca) {
        if (rca.getResponseCorrectAnswer().size() != getResponseCorrectAnswer().size()) {
            return false;
        }
        int i = 0;
        for (String string : getResponseCorrectAnswer()) {
            if (string.equals(rca.getResponseCorrectAnswer().get(i))) {
                i++;
            } else {
                return false;
            }
        }

        return true;
    }
}
