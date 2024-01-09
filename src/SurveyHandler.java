import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//TODO may need to add controller class beyond Main
//TODO get currentSurvey as an attribute
public class SurveyHandler {

    static Survey currentSurvey;
    static Output output = new Output();

    static Input input = new Input();

    static String loadSurveyName = "";

    static File file = new File("survey");

    static String[] surveyFiles = file.list();

    public void SurveyHandler() {
        this.currentSurvey = new Survey("StartingSurvey");
    }

    public void setCurrentSurvey(Survey currentSurvey) {
        this.currentSurvey = currentSurvey;
    }

    public Survey getCurrentSurvey() {
        return currentSurvey;
    }

    public static void displayMenu1() {
        output.output("1. Create a new Survey");
        output.output("2. Display an existing Survey");
        output.output("3. Load an existing Survey");
        output.output("4. Save the current Survey");
        output.output("5. Take the current Survey");
        output.output("6. Modify the current Survey");
        output.output("7. Tabulate a Survey");
        output.output("8. Return to previous menu");
    }

    public static void displayMenu2() {
        output.output("Create Survey:");
        output.output("1. Add a new T/F question");
        output.output("2. Add a new multiple-choice question");
        output.output("3. Add a new short answer question");
        output.output("4. Add a new essay question");
        output.output("5. Add a new date question");
        output.output("6. Add a new matching question");
        output.output("7. Return to previous menu");
    }

    public static void createSurvey() {
        currentSurvey = nameSurvey();
        while (1 == 1) {
            SurveyHandler.displayMenu2();
            output.output("Enter choice: ");
            String questionChoice = input.getString();
            if (questionChoice.equals("1")) {
                currentSurvey.createTF();
            } else if (questionChoice.equals("2")) {
                currentSurvey.createMultipleChoice();
            } else if (questionChoice.equals("3")) {
                currentSurvey.createShortAnswer();
            } else if (questionChoice.equals("4")) {
                currentSurvey.createEssay();
            } else if (questionChoice.equals("5")) {
                currentSurvey.createValidDate();
            } else if (questionChoice.equals("6")) {
                currentSurvey.createMatching();
            } else if (questionChoice.equals("7")) {
                break;
            } else {
                output.output("Invalid Input: please enter 1 - 6 to create a question, or 7 to return to previous menu");
            }
        }
    }

    //for loadSurvey() function
    public static void displayFiles(String[] files) {
        Integer i = 1;
        for (String file: files) {
            output.output(i + " " + file);
            i++;
        }
    }

    public static void displaySurvey() {
        if (currentSurvey == null) {
            output.output("You must have a survey loaded in order to display it.");
        } else {
            Integer i = 1;
            output.output("Displaying " + currentSurvey.getSurveyName() + ":" + "\n");
            for (Question question : currentSurvey.questions) {
                question.displayQuestion(i);
                i++;
            }
        }
        output.output("");
    }

    public static Survey loadSurvey(String surveyName) {
        ObjectInputStream objectInputStream = null;
        FileInputStream fileInputStream = null;
        try {
//            displayFiles(files);
//            output.output("Enter file number you wish to load");
//            Integer path = input.getInteger();
            fileInputStream = new FileInputStream("survey" + File.separator +  surveyName);
            objectInputStream = new ObjectInputStream(fileInputStream);
            Survey loadedSurvey = (Survey) objectInputStream.readObject();
            //reinitialize transient attributes
            loadedSurvey.setOutput(new Output());
            loadedSurvey.setInput(new Input());
            loadedSurvey.deserializeQuestions();
            return loadedSurvey;
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

    public static void takeSurvey() {
        output.output("Taking survey");
        currentSurvey.takeSurvey();
        saveSurvey();
    }

    public static void modifySurvey() {
        if (currentSurvey == null) {
            output.output("You must have a survey loaded in order to modify it.");
        } else {
            output.output("Modifying Survey");
            displaySurvey();
            currentSurvey.modifySurvey();
        }
    }

    static public void tabulateSurvey() {
        ArrayList<HashMap<String, Integer>> surveyData = new ArrayList<>();
        ArrayList<Survey> surveyList = collectSurveys();
        //initialize list of question responses
        for (int i = 0; i < surveyList.get(0).getQuestions().size(); i++) {
            Question question = surveyList.get(0).getQuestions().get(i);
            HashMap<String, Integer> hash = new HashMap<>();
            if (MultipleChoice.class.isInstance(question)) {
                //then
                Integer numChoices = question.getChoices().size();
                for (int j = 0; j < numChoices; j++) {
                    hash.put("[" + String.valueOf(j+1) + "]", 0);
                }
                surveyData.add(hash);
            } else if (TF.class.isInstance(question)) {
                hash.put("[T]", 0);
                hash.put("[F]", 0);
                surveyData.add(hash);
            } else {
                surveyData.add(hash);
            }
        }

        //tallying the responses
        for (Survey survey : surveyList) {
            int i = 0;
            for (Question question : survey.getQuestions()) {
                String response = question.getAnswers().getResponseCorrectAnswer().toString();
                if (surveyData.get(i).get(response) == null) {
                    surveyData.get(i).put(response, 1);
                } else {
                    //increment
                    surveyData.get(i).put(response, surveyData.get(i).get(response) + 1);
                }
                i++;
            }
        }

        //output the results
        int k = 1;
        ArrayList<Question> questions = surveyList.get(0).getQuestions();
        for (HashMap<String, Integer> hash : surveyData) {
            output.output("Question " + k + ": " + questions.get(k - 1).getQuestion());
            for (Map.Entry<String, Integer> entry : hash.entrySet()) {
                output.output(entry.getKey() + ": " + entry.getValue());
            }
            k++;
        }
        output.output("");
    }

    public static void displaySurveyDirectories() {
        int i = 1;
        for (String survey : surveyFiles) {
            output.output(i + ": " + survey);
            i++;
        }
    }

    static public Survey nameSurvey() {
        Survey newSurvey;
        boolean original = false;
        while (original == false) {
            output.output("Enter Test name: ");
            String name = input.getString();
            if (TestHandler.contains(surveyFiles, name) == true) {
                output.output("There is already a test with that name. Please enter another name or delete that directory");
            } else {
                newSurvey = (new Survey(name));
                return newSurvey;
            }
        }
        return null;
    }

    static public String[] getSurveyDirectoryFiles() {
        if (surveyFiles == null) {
            output.output("There are no surveys to load");
        } else {
            boolean contains = false;

            while (contains == false) {
                try {
                    output.output("Enter the survey directory you wish to open: ");
                    String numString = input.getString();
                    Integer surveyNum = Integer.parseInt(numString);
                    if (surveyNum > 0 && surveyNum <= surveyFiles.length) {
                        loadSurveyName = surveyFiles[surveyNum - 1];
                        File testFile = new File("survey" + File.separator + surveyFiles[surveyNum - 1]);
                        String[] collectedSurveys = testFile.list();

                        return collectedSurveys;
                    } else {
                        output.output("Error: Survey directory not found");
                    }
                } catch (Exception e) {
                    output.output("Error: Please enter an integer");
                }

            }
        }
        return null;
    }

    static public ArrayList<Survey> collectSurveys() {
        ArrayList<Survey> surveyList = new ArrayList<>();
        displaySurveyDirectories();
        String[] collectedSurveys = getSurveyDirectoryFiles();
        for (String survey : collectedSurveys) {
            Survey newSurvey = loadSurvey(loadSurveyName + File.separator + survey);
            surveyList.add(newSurvey);
        }
        return surveyList;

    }

    static public void loadSurveyWithInput() {
        //ask for directory, then load the last test in that directory to work with
        displaySurveyDirectories();
        String[] surveyFiles = getSurveyDirectoryFiles();
        assert surveyFiles != null;
        currentSurvey = loadSurvey(loadSurveyName + File.separator + surveyFiles[surveyFiles.length - 1]);
        output.output("The current survey is now " + currentSurvey.getSurveyName());
    }

    public static void saveSurvey() {
        File surveyDirectory = new File("survey" + File.separator + currentSurvey.getSurveyName());
        File surveyFile;
        if (currentSurvey.getQuestions().get(0).getAnswers() == null) {
            surveyFile = new File(surveyDirectory + currentSurvey.getSurveyName() + ".ser");
        }
        else {
            boolean created = surveyDirectory.mkdir();
            surveyFile = new File(surveyDirectory + File.separator + currentSurvey.getSurveyName() + currentSurvey.getSurveyNumber() + ".ser");

        }
        currentSurvey.setSurveyNumber(currentSurvey.getSurveyNumber() + 1);
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream out = null;
        try {
            fileOutputStream = new FileOutputStream(surveyFile);
            out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(currentSurvey);
            output.output("Saved " + currentSurvey.getSurveyName());
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
}