import java.io.Serializable;
import java.util.Scanner;

public class Input implements Serializable {

    Output output = new Output();

    Scanner scanner = new Scanner(System.in);
    public String getString() {
        String input = scanner.nextLine();
        return input;
    }

    public Integer getInteger() {
        try {
            Integer input = 0;
            input += scanner.nextInt();
            scanner.nextLine();
            return input;
        } catch (Exception e) {
            output.output("Error. Please enter an integer");
        }
        return null;
    }

    public String getLine() {
        String input ="";
        input+=scanner.nextLine();
        return input;
    }
}
