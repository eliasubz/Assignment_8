import java.util.ArrayList;
import java.util.List;

public class Category {

    private String topic;
    private List<Question> questions = new ArrayList<>();

    public Category(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void addQuestion(Question question){
        questions.add(question);
    }

    public String removeQuestion(){
        return questions.remove(0).getQue();
    }


}
