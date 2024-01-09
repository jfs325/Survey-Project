import java.io.Serializable;

//output class for easier refactoring if I wanted to make application in GUI format
public class Output implements Serializable {
    public void output(String output) {
        System.out.println(output);
    }

}
