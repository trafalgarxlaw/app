package ca.uqam.info.inf600g.model;

import ca.uqam.info.inf600g.data.QuestionCollection;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuizPersonnel {
    private static QuizPersonnel database;

    public static QuizPersonnel getAccess() {
        if(database == null) {
            database = new QuizPersonnel();
        }
        return database;

    }

    @JsonProperty("id")
    public int id;
    @JsonProperty("residentName")
    public String residentName;
    @JsonProperty("Title")
    public String Title;
    @JsonProperty("Description")
    public String Description;
    @JsonProperty("numberQuestion")
    public int numberQuestion;
    @JsonProperty("questions")
    public QuestionCollection questions;

    public int getResidentId() {
        return residentId;
    }

    public void setResidentId(int residentId) {
        this.residentId = residentId;
    }

    @JsonProperty("residentId")
    private int residentId;

    QuizPersonnel() {
        this.id = 1;
        this.residentName="Fred";
        this.Title="Personal Quiz Title";
        this.Description="This is the description of a personal Quiz";
        this.questions = new QuestionCollection();
        initialize();
    }

    public QuizPersonnel(int id,String residentName , int residentid, String Title, String Description ,QuestionCollection questions){
        this.id = id;
        this.residentName=residentName;
        this.Title=Title;
        this.Description=Description;
        this.questions=questions;
        this.residentId = residentid;
    }

    public QuizPersonnel(int id,String residentName ,String Title, String Description ,QuestionCollection questions){
        this.id = id;
        this.residentName=residentName;
        this.Title=Title;
        this.Description=Description;
        this.questions=questions;
        this.residentId = 0;
    }

    // adding some question to a quizz when initialized
    private void initialize() {
        Question q1 = new Question(0, "Personal Question #1", "1", "4", "6", "4", "aaudio.mp3.mp3");
        this.questions.addQuestion(q1);
        Question q2 = new Question(1, "Personal Question #2", "3", "4", "5", "3");
        this.questions.addQuestion(q2);
    }


    public Question getOneQuestion(int id){
        return this.questions.getOneSpecificQuestion(id);
    }

    public boolean containsKey(String id){
        return this.questions.containsKey(id);
    }

    public void ajouterQuestion(Question question){
        this.questions.addQuestion(question);
    }

    @Override
    public String toString() {
        return "QuizPersonnel{" +
                "id=" + id +
                ", residentName='" + residentName + '\'' +
                ", Title='" + Title + '\'' +
                ", Description='" + Description + '\'' +
                ", numberQuestion=" + numberQuestion +
                ", questions=" + questions.toString() +
                ", residentId=" + residentId +
                '}';
    }
}
