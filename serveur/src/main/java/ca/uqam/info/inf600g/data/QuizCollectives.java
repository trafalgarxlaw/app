package ca.uqam.info.inf600g.data;
import ca.uqam.info.inf600g.model.Question;
import ca.uqam.info.inf600g.model.Quiz;

import java.util.ArrayList; // import the ArrayList class


public class QuizCollectives {
    ArrayList<Quiz> CollectiveQuizs; //represents all the Quiz Collectifs available

    private static QuizCollectives database;

    public static QuizCollectives getAccess() {
        if(database == null) {
            database = new QuizCollectives();}
        return database;

    }
        private QuizCollectives() {
        this.CollectiveQuizs = new ArrayList<Quiz>();
        this.initialize();
    }

    private void initialize(){
        //creating a question Collection
        QuestionCollection questions = new QuestionCollection();
        QuestionCollection questions2 =new QuestionCollection();

        Question q1 = new Question(0, "Combien de cote contient un carre?", "1", "4", "6", "4");
        questions.addQuestion(q1);
        Question q2 = new Question(1, "Combien de cote contient un triangle", "3", "4", "5", "3");
        questions.addQuestion(q2);

    Question q3 = new Question(2, "this is a question for the Quiz two", "3", "4", "5", "3");
        questions2.addQuestion(q3);

        //Creating  Quizz
        Quiz quiz1=new Quiz(0,"Quiz One","Quiz Description...",questions);
        Quiz quiz2=new Quiz(1,"Quiz Two","Quiz Description...",questions2);

        this.CollectiveQuizs.add(quiz1);
        this.CollectiveQuizs.add(quiz2);

    }

    public  ArrayList<Quiz> getCollectiveQuizs() {
        return CollectiveQuizs;
    }
    public  Quiz getQuizById(int id) {
        return CollectiveQuizs.get(id);
    }
}
