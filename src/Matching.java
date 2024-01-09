import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Matching extends Question implements Serializable {
    ArrayList<String> termList;
    ArrayList<String> definitionList;

    public Matching(String question, ArrayList<String> termList, ArrayList<String> definitionList) {
        this.question = question;
        this.termList = termList;
        this.definitionList = definitionList;
    }

    public ArrayList<String> getTermList() {
        return this.termList;
    }

    public ArrayList<String> getDefinitionList() {return this.definitionList;}
    @Override
    public void displayQuestion(Integer questionNumber) {

        this.output.output("Question " + questionNumber);
        this.output.output(getQuestion());
        this.output.output("Matching options: ");
        Integer j = 1;
        for (int i = 0, a = 97; i < getTermList().size();i++, a++) {
            char def = (char) a;
            this.output.output(j + ": " + getTermList().get(i) + "                       " + (def) + ": " + getDefinitionList().get(i));
            j++;
        }
    }

    public RCA askQuestion() {
        //create arrays to show what pairs are remaining to choose from
        RCA rca = new RCA();
        ArrayList<String> Aremaining = new ArrayList<>();
        ArrayList<String> Bremaining = new ArrayList<>();

        for (Integer i = 0; i < getTermList().size(); i++) {
            Aremaining.add(String.valueOf(i + 1));
            Bremaining.add(String.valueOf((char)(65 + i)));
        }
        while (Aremaining.size() != 0 && Bremaining.size() != 0) {
            this.output.output("Enter term option number: ");
            String sideA = this.input.getString();
            if (!Aremaining.contains(sideA)) {
                output.output("That option has been taken or is out of range");
                continue;
            }
            this.output.output("Enter definition option letter: ");
            String sideB = this.input.getString();
            if (!Bremaining.contains(sideB.toUpperCase())) {
                output.output("That option has been taken or is out of range");
                continue;
            }
            rca.getResponseCorrectAnswer().add(sideA + sideB.toUpperCase());
            Aremaining.remove(sideA);
            Bremaining.remove(sideB);
        }

        Collections.sort(rca.getResponseCorrectAnswer());
        return rca;
    }

//    public void displayAnswer() {
//        if (answers == null) {
//            return;
//        } else {
//            answer.displayAnswers
//            }
//        }
//    }

    public void modifyQuestion() {
        while (1 == 1) {
            output.output("Enter q to modify question, t for terms, d for definitions, or 0 to quit modifying");
            String input = this.input.getString();
            if (input.equals("q")) {
                output.output("Enter new prompt: ");
                String newQuestion = this.input.getString();
                setQuestion(newQuestion);
            }  else if (input.equals("t")) {
                modifyTermList();
            } else if (input.equals("d")) {
               modifyDefinitionList();
            } else if (input.equals("0")) {
                break;
            } else {
                output.output("Invalid input. Try again");
            }
        }
    }

    private void modifyTermList() {
        Integer i = 1;
        for (String choice : getTermList()) {
            output.output(i + ". " + choice);
            i++;
        }
        while (1 == 1) {
            try {
                output.output("Enter number to modify choice and 0 to quit modifying.");
                Integer choice = input.getInteger();
                if (choice > 0 && choice < getTermList().size() + 1) {
                    output.output("Enter new matching choice: ");
                    String newChoice = input.getString();
                    this.getTermList().set(choice - 1, newChoice);
                } else if (choice.equals(0)) {
                    break;
                } else {
                    output.output("Invalid input. Try again");
                }
            } catch (Exception e) {
                output.output("Please enter integer input.");
            }


        }
    }

    private void modifyDefinitionList() {
        Integer i = 1;
        for (String choice : getDefinitionList()) {
            output.output(i + ". " + choice);
            i++;
        }
        while (1 == 1) {
            try {
                output.output("Enter number to modify choice and 0 to quit modifying.");
                Integer choice = input.getInteger();
                if (choice > 0 && choice < getDefinitionList().size() + 1) {
                    output.output("Enter new matching choice: ");
                    String newChoice = input.getString();
                    this.getDefinitionList().set(choice - 1, newChoice);
                } else if (choice.equals(0)) {
                    break;
                } else {
                    output.output("Invalid input. Try again");
                }
            } catch (Exception e) {
                output.output("Please enter integer input.");
            }


        }
    }
}
