import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static jdk.dynalink.linker.support.Guards.isInstance;

public class TestHandler extends SurveyHandler {

    static Test currentTest;
    static Output output = new Output();

    static String loadTestName = "";

    static File testFile = new File("test");

    static String[] testFiles = testFile.list();

    public TestHandler() {}

    public TestHandler(String name) {
        this.currentTest = new Test(name);
    }

    public static void displayMenu1() {
        output.output("1. Create a new Test");
        output.output("2. Display an existing Test without correct answers");
        output.output("3. Display an existing Test with correct answers");
        output.output("4. Load an existing Test");
        output.output("5. Save the current test");
        output.output("6. Take the current test");
        output.output("7. Modify the current test");
        output.output("8. Tabulate a test");
        output.output("9. Grade a test");
        output.output("10. Return to the previous menu\n");
    }

    public static void displayTest() {
        if (currentTest == null) {
            output.output("You must have a Test loaded in order to display it.");
        } else {
            Integer i = 1;
            output.output("Displaying " + currentTest.getSurveyName() + ":" + "\n");
            for (Question question : currentTest.questions) {
                question.displayQuestion(i);
                i++;
            }
        }
        output.output("");
    }

    public static void displayTestDirectories() {
        int i = 1;
        for (String test : testFiles) {
            output.output(i + ": " + test);
            i++;
        }
    }



    public static void displayTestWithCorrectAnswers() {
        if (currentTest == null) {
            output.output("You must have a Test loaded in order to display it.");
        } else {
            output.output("Displaying " + currentTest.getSurveyName() + ":" + "\n");
            currentTest.displayTestWithCorrectAnswers();
        }
        output.output("");
    }

    static public Test nameTest() {
        Test newTest;
        boolean original = false;
        while (original == false) {

            output.output("Enter Test name: no spaces please");
            String name = input.getString();
            if (contains(testFiles, name) == true) {
                output.output("There is already a test with that name. Please enter another name or delete that directory");
                continue;
            } else {
                newTest = (new Test(name));
                return newTest;
            }
        }
        return null;
    }
    public static void createTest() {
        currentTest = nameTest();
        while (1 == 1) {
            SurveyHandler.displayMenu2();
            output.output("Enter choice: ");
            String questionChoice = input.getString();
            if (questionChoice.equals("1")) {
                currentTest.createTF();
                currentTest.setNumQuestions(currentTest.getNumQuestions() + 1);
            } else if (questionChoice.equals("2")) {
                currentTest.createMultipleChoice();
                currentTest.setNumQuestions(currentTest.getNumQuestions() + 1);
            } else if (questionChoice.equals("3")) {
                currentTest.createShortAnswer();
                currentTest.setNumQuestions(currentTest.getNumQuestions() + 1);
            } else if (questionChoice.equals("4")) {
                currentTest.createEssay();
                currentTest.setNumQuestions(currentTest.getNumQuestions() + 1);
            } else if (questionChoice.equals("5")) {
                currentTest.createValidDate();
                currentTest.setNumQuestions(currentTest.getNumQuestions() + 1);
            } else if (questionChoice.equals("6")) {
                currentTest.createMatching();
                currentTest.setNumQuestions(currentTest.getNumQuestions() + 1);
            } else if (questionChoice.equals("7")) {
                break;
            } else {
                output.output("Invalid Input: please enter 1 - 6 to create a question, or 7 to return to previous menu");
            }
        }
    }

    static public boolean contains(String[] testFiles, String name) {
        for (int i = 0; i < testFiles.length; i++) {
            if (testFiles[i].equals(name)) {
                return true;
            }
        }

        return false;
    }

    static public String[] getTestDirectoryFiles() {
        if (testFiles == null) {
            output.output("There are no tests no load");
        } else {
            boolean contains = false;

            while (contains == false) {
                try {
                    output.output("Enter the test directory you wish to open: ");
                    String numString = input.getString();
                    Integer testNum = Integer.parseInt(numString);
                    if (testNum > 0 && testNum <= testFiles.length) {
                        loadTestName = testFiles[testNum - 1];
                        File testFile = new File("test" + File.separator + testFiles[testNum - 1]);
                        String[] collectedTests = testFile.list();

                        return collectedTests;
                    } else {
                        output.output("Error: Test directory not found");
                    }
                } catch (Exception e) {
                    output.output("Error: Please enter an integer");
                }

            }
        }
        return null;
    }

    static public ArrayList<Test> collectTests() {
            ArrayList<Test> testList = new ArrayList<>();
            displayTestDirectories();
            String[] collectedTests = getTestDirectoryFiles();
                for (String test : collectedTests) {
                    Test newTest = loadTest(loadTestName + File.separator + test);
                    testList.add(newTest);
                }
                    return testList;

    }

    static public void tabulateTest() {
        ArrayList<HashMap<String, Integer>> testData = new ArrayList<>();
        ArrayList<Test> testList = collectTests();
        //initialize list of question responses
        for (int i = 0; i < testList.get(0).getQuestions().size(); i++) {
            Question question = testList.get(0).getQuestions().get(i);
            HashMap<String, Integer> hash = new HashMap<>();
            if (MultipleChoice.class.isInstance(question)) {
                Integer numChoices = question.getChoices().size();
                for (int j = 0; j < numChoices; j++) {
                    hash.put("[" + String.valueOf(j+1) + "]", 0);
                }
                testData.add(hash);
            } else if (TF.class.isInstance(question)) {
                hash.put("[T]", 0);
                hash.put("[F]", 0);
                testData.add(hash);
            } else {
                testData.add(hash);
            }
        }

        //tallying the responses
        for (Test test : testList) {
            int i = 0;
            for (Question question : test.getQuestions()) {
                String response = question.getAnswers().getResponseCorrectAnswer().toString();
                if (testData.get(i).get(response) == null) {
                    testData.get(i).put(response, 1);
                } else {
                    //increment
                    testData.get(i).put(response, testData.get(i).get(response) + 1);
                }
                i++;
            }
        }

        //output the results
        int k = 1;
        ArrayList<Question> questions = testList.get(0).getQuestions();
        for (HashMap<String, Integer> hash : testData) {
            output.output("Question " + k + ": " + questions.get(k - 1).getQuestion());
            for (Map.Entry<String, Integer> entry : hash.entrySet()) {
                output.output(entry.getKey() + ": " + entry.getValue());
            }
            k++;
        }
        output.output("");
    }

    static public void gradeTest() {
        displayTestDirectories();
        String[] testList = getTestDirectoryFiles();
        int i = 1;
        for (String testFile : testList) {
            output.output(i + ": " + testFile);
            i++;
        }
        while (1 == 1) {
            try {
                output.output("Enter the taken test number you wish to grade: ");
                String choiceString = input.getLine();
                Integer choice = Integer.parseInt(choiceString);
                if (choice > 0 && choice <= testList.length) {
                    currentTest = loadTest(loadTestName + File.separator + testList[choice - 1]);
                    assert currentTest != null;
                    currentTest.gradeTest();
                    break;
                } else {
                    output.output("Error: Enter a number in range");
                }


            } catch (Exception e) {
                output.output("Error: Please input an integer in range");
            }
        }
    }

    public static Test loadTest(String testName) {
        ObjectInputStream objectInputStream = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("test" + File.separator +  testName);
            objectInputStream = new ObjectInputStream(fileInputStream);
            Test loadedTest = (Test) objectInputStream.readObject();
            //reinitialize transient attributes
            loadedTest.setOutput(new Output());
            loadedTest.setInput(new Input());
            loadedTest.deserializeQuestions();
            return loadedTest;
        } catch (FileNotFoundException f ) {
            output.output("File not found");
        } catch (IOException i) {
            output.output("I/O exception");
        } catch (ClassNotFoundException c) {
            output.output("Class not found exception");
        } finally {
            try{
                if(fileInputStream != null)
                    fileInputStream.close();
                if(objectInputStream != null)
                    objectInputStream.close();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return null;
    }
    public static void loadTestWithInput() {
        //ask for directory, then load the last test in that directory to work with
        displayTestDirectories();
        String[] testFiles = getTestDirectoryFiles();
        assert testFiles != null;
        currentTest = loadTest(loadTestName + File.separator + testFiles[testFiles.length - 1]);
        output.output("The current test is now " + currentTest.getSurveyName());
    }

    public static void saveTest() {
        File testDirectory = new File("test" + File.separator + currentTest.getSurveyName());
        boolean created = testDirectory.mkdir();
        File testFile = new File(testDirectory + File.separator + currentTest.getSurveyName() + currentTest.getSurveyNumber() + ".ser");
        currentTest.setSurveyNumber(currentTest.getSurveyNumber() + 1);
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream out = null;
        try {
            fileOutputStream = new FileOutputStream(testFile);
            out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(currentTest);
            output.output("Saved " + currentTest.getSurveyName());
            out.close();
            fileOutputStream.close();
        } catch (FileNotFoundException f) {
            output.output("File not found");
        } catch (IOException i) {
            output.output("IO Exception");
        } finally{
            try{
                if(fileOutputStream != null)
                    fileOutputStream.close();
                if(out != null)
                    out.close();
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void takeTest() {
        output.output("Taking test");
        currentTest.takeSurvey();
        saveTest();
    }
    public static void modifyTest() {
        if (currentTest == null) {
            output.output("You must have a Test loaded to modify it");
        } else {
            while (1 == 1) {
                output.output("1. Modify questions");
                output.output("2. Modify correct answers");
                output.output("Enter 0 to quit modifying");
                output.output("Enter your choice: ");
                String choice = input.getString();
                if (choice.equals("1")) {
                    displayTest();
                    currentTest.modifySurvey();
                } else if (choice.equals("2")) {
                    currentTest.modifyCorrectAnswers();
                } else if (choice.equals("0")) {
                    break;
                } else {
                    output.output("Invalid input. Try again");
                }
            }
        }
    }
 }
