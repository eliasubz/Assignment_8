import java.util.LinkedList;
import java.util.List;

public class Questions {
    private List<String> questions;
    private String name;

    public Questions (String name) {
        this.name = name;
    }




    public void addQuestion(int i){
        questions.add(name +": "+ i);
    }

}
