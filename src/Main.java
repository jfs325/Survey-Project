import java.io.*;
import java.util.ArrayList;

//Main handler Class
public class Main {

    static File file = new File("survey");
    static String[] surveyFiles = file.list();
    static SurveyHandler surveyHandler = new SurveyHandler();

    static TestHandler testHandler = new TestHandler();
    static Output output = new Output();

    static Input input = new Input();

    private static void displayMenu1() {
        output.output("Select to work on either a Survey or a Test");
        output.output("1. Survey");
        output.output("2. Test");
        output.output("0. Quit");
    }

    private static void SurveyMenu() {
        String choice = "";
        while (1 == 1) {
            SurveyHandler.displayMenu1();
            output.output("Enter choice: ");
            choice = input.getLine();
            if (choice.equals("1")) {
                SurveyHandler.createSurvey();
            } else if (choice.equals("2")) {
                SurveyHandler.displaySurvey();
            } else if (choice.equals("3")) {
                SurveyHandler.loadSurveyWithInput();
            } else if (choice.equals("4")) {
                SurveyHandler.saveSurvey();
            } else if (choice.equals("5")) {
                SurveyHandler.takeSurvey();
            } else if (choice.equals("6")) {
                SurveyHandler.modifySurvey();
            } else if (choice.equals("7")) {
                SurveyHandler.tabulateSurvey();
            } else if (choice.equals("8")) {
                break;
            } else {
                output.output("Invalid input. Try again");
            }
        }
    }

    private static void TestMenu() {
        String choice = "";
        while (1 == 1) {
            TestHandler.displayMenu1();
            output.output("Enter choice: ");
            choice = input.getString();
            if (choice.equals("1")) {
                TestHandler.createTest();
            } else if (choice.equals("2")) {
                TestHandler.displayTest();
            } else if (choice.equals("3")) {
                TestHandler.displayTestWithCorrectAnswers();
            } else if (choice.equals("4")) {
                TestHandler.loadTestWithInput();
            } else if (choice.equals("5")) {
                TestHandler.saveTest();
            } else if (choice.equals("6")) {
                TestHandler.takeTest();
            } else if (choice.equals("7")) {
                TestHandler.modifyTest();
            } else if (choice.equals("8")) {
                TestHandler.tabulateTest();
            } else if (choice.equals("9")) {
                TestHandler.gradeTest();
            } else if (choice.equals("10")) {
                break;
            } else {
                output.output("Invalid input. Try again");
            }
        }
    }

    public static void main(String []args) {
        ArrayList<String> choices = new ArrayList<>();
        ArrayList<String> termList = new ArrayList<>();
        ArrayList<String> definitionList = new ArrayList<>();
        choices.add("blue");
        choices.add("green");
        termList.add("sky");
        definitionList.add("blue");
        Question tf = new TF("The sky is blue");
        Question mult = new MultipleChoice("The sky is what: ", choices);
        Question shortAnswer = new ShortAnswer("Briefly explain why the sky is blue");
        Question essay = new Essay("Write in around 1000 words why the sky is blue");
        Question validDate = new ValidDate("Please enter the date that the sky turned blue");
        Question matching = new Matching("Connect the sky with it's correct color", termList, definitionList);
        surveyHandler.setCurrentSurvey(new Survey("newSurvey"));
        surveyHandler.getCurrentSurvey().questions.add(tf);
        surveyHandler.getCurrentSurvey().questions.add(mult);
        surveyHandler.getCurrentSurvey().questions.add(shortAnswer);
        surveyHandler.getCurrentSurvey().questions.add(essay);
        surveyHandler.getCurrentSurvey().questions.add(validDate);
        surveyHandler.getCurrentSurvey().questions.add(matching);

        while (1 == 1) {
            displayMenu1();
            output.output("Enter choice: ");
            String choice = input.getString();
            if (choice.equals("1")) {
                SurveyMenu();
            } else if (choice.equals("2")) {
                TestMenu();
            } else if (choice.equals("0")) {
                break;
            } else {
                output.output("Invalid Input. Please try again");
            }
        }
    }
}
