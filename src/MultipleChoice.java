import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

//TODO: enter number of answers
public class MultipleChoice extends Question implements Serializable {
    String question;
    ArrayList<String> choices;

    public MultipleChoice(String question, ArrayList<String> choices) {
        this.question = question;
        this.choices = choices;
    }

    @Override
    public RCA askQuestion() {
        ArrayList<String> answers = new ArrayList<>();
        RCA rca = new RCA();
        Integer numAnswers = 0;
        while (1 == 1) {
            try {
                output.output("Enter your choice in integer format: enter 0 if you are done selecting");
                String choiceString = input.getLine();
                Integer choice = Integer.parseInt(choiceString);
                if (choice.equals(0)) {
                    break;
                } else if (numAnswers == this.choices.size()) {
                    output.output("You cannot give any more answers.");
                    break;
                }  else if (answers.contains(String.valueOf(choice))) {
                    output.output("You have already given that choice.");
                } else if (choice > 0 && choice < this.choices.size() + 1){
                rca.getResponseCorrectAnswer().add(String.valueOf(choice));
                answers.add(String.valueOf(choice));
                numAnswers++;
                } else {
                    output.output("Invalid input. Make sure choice number is within range of choices");
                }
            }  catch (Exception e) {
                output.output("Error. Please give integer input");
            }
        }

        Collections.sort(rca.getResponseCorrectAnswer());
        return rca;
    }
    public void displayQuestion(Integer questionNumber) {
        this.output.output("Question " + questionNumber);
        this.output.output(question);
        Integer i = 1;
        for (String choice : this.choices) {
            this.output.output(i + ". " + choice);
            i++;
        }
        this.output.output("");
    }

    public void modifyQuestion() {
        while (1 == 1) {
            output.output("Enter q to modify question, c to modify choices, enter 0 to exit modifying");
            String input = this.input.getString();
            if (input.equals("q")) {
                output.output("Enter new question: ");
                String newQuestion = this.input.getString();
                setQuestion(newQuestion);
            } else if (input.equals("c")) {
                modifyChoices();
            } else if (input.equals("0")) {
                break;
            } else {
                output.output("Invalid input. Try again");
            }
        }
    }

    public void modifyChoices() {
        while (1 == 1) {
            displayQuestion(0);
            output.output("Enter choice number to modify, enter 0 to exit modifying");
            Integer input = this.input.getInteger();
            if (input.equals(0)) {
                break;
            } else if (input > 0 && input < choices.size()) {
                output.output("Enter new choice: ");
                String newChoice = this.input.getString();
                this.choices.set(input - 1, newChoice);
            } else {
                output.output("Invalid input. Please enter a number in choices range");
            }
        }
    }

    @Override
    public ArrayList<String> getChoices() {
        return choices;
    }
}
