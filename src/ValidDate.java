import java.io.Serializable;
import java.util.ArrayList;

public class ValidDate extends Question implements Serializable {

    Integer month;
    Integer day;
    Integer year;

    public ValidDate(String question) {
        this.question = question;
        this.choice = "Date will be in MO/DY/YR format";
    }

    public Integer getDay() {
        return day;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void modifyQuestion() {
        output.output("Enter new date question: ");
        String date = this.input.getLine();
        setQuestion(date);
    }

    @Override
    public RCA askQuestion() {
        RCA rca = new RCA();
        askMonth();
        askDay();
        askYear();
        String date = getMonth() + "/" + getDay() + "/" + getYear();
        rca.getResponseCorrectAnswer().add(date);
        return rca;
    }
    public void askMonth() {
        while (1 == 1) {
            try {
                this.output.output("Enter month in format 01 for January, 02 for February, etc.");
                String inputString = this.input.getString();
                Integer input = Integer.parseInt(inputString);
                if (input > 0 && input <= 12) {
                    setMonth(input);
                    break;
                } else {
                  output.output("Please enter a number between 1 and 12 inclusive");
                }
            } catch (Exception e) {
                output.output("Invalid input. Please enter an integer");
            }
        }
    }

    private void askDay() {
        while (1 == 1) {
            this.output.output("Enter day: should be between 1 and 31");
            String inputString = this.input.getString();
            Integer input = Integer.parseInt(inputString);
            if (input > 0 && input <= 31) {
                setDay(input);
                break;
            } else {
                this.output.output("Invalid input. Try again");
            }
        }
    }

    private void askYear() {
        while (1 == 1) {
            this.output.output("Enter year: should be between 1 and 3000");
            String inputString = this.input.getString();
            Integer input = Integer.parseInt(inputString);
            if (input > 0 && input <= 3000) {
                setYear(input);
                break;
            } else {
                this.output.output("Invalid input. Try again");
            }
        }
    }


}



