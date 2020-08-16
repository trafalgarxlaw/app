package ca.uqam.info.inf600g.data;
import ca.uqam.info.inf600g.model.Question;
import ca.uqam.info.inf600g.model.Quiz;
import ca.uqam.info.inf600g.model.QuizPersonnel;

import java.util.ArrayList; // import the ArrayList class


public class PersonalQuizCollection {
    ArrayList<QuizPersonnel> personalQuizs; //represents all the Quiz Collectifs available

    private static PersonalQuizCollection database;

    public static PersonalQuizCollection getAccess() {
        if(database == null) {
            database = new PersonalQuizCollection();}
        return database;

    }
    private PersonalQuizCollection() {
        this.personalQuizs = new ArrayList<QuizPersonnel>();
        this.initialize();
    }

    private void initialize(){
        //creating a question Collection
        QuestionCollection Fredquestions1 = new QuestionCollection();
        QuestionCollection FredsQuestions2 =new QuestionCollection();
        QuestionCollection FredsQuestions3 =new QuestionCollection();
        QuestionCollection Camquestions1 =new QuestionCollection();
        QuestionCollection Camquestions2 =new QuestionCollection();





        //question for fred - in his Quiz 1
        Question Fredq1 = new Question(0, "Question personnel 1 for fred ", "1", "4", "6", "4", "aaudio.mp3");
        Question Fredq2 = new Question(1, "question personnel 2 for fred", "3", "4", "5", "3");
        Question Fredq3 = new Question(2, "question personnel 3 for fred", "3", "4", "5", "3");
        Question Fredq4 = new Question(3, "question personnel 4 for fred", "3", "4", "5", "3");
        Question Fredq5 = new Question(4, "question personnel 5 for fred", "3", "4", "5", "3");
        Fredquestions1.addQuestion(Fredq1);
        Fredquestions1.addQuestion(Fredq2);
        Fredquestions1.addQuestion(Fredq3);
        Fredquestions1.addQuestion(Fredq4);
        Fredquestions1.addQuestion(Fredq5);


        //question for fred - in his Quiz 2
        Question personalQuestions0 = new Question(0, "this is a question personal1 question for fred", "3", "4", "5", "3");
        Question personalQuestions1 = new Question(1, "this is a question personal2 question for fred", "3", "4", "5", "3");
        Question personalQuestions2 = new Question(2, "this is a question personal3 question for fred", "3", "4", "5", "3");
        Question personalQuestions3 = new Question(3, "this is a question personal4 question for fred", "3", "4", "5", "3");
        Question personalQuestions4 = new Question(4, "this is a question personal5 question for fred", "3", "4", "5", "3");
        FredsQuestions2.addQuestion(personalQuestions0);
        FredsQuestions2.addQuestion(personalQuestions1);
        FredsQuestions2.addQuestion(personalQuestions2);
        FredsQuestions2.addQuestion(personalQuestions3);
        FredsQuestions2.addQuestion(personalQuestions4);


        //question for fred - in his Quiz 3
        Question anotherPersonalQuestion1 = new Question(0, "this is a question1 personal question for fred concerning his past...", "3", "4", "5", "3");
        Question anotherPersonalQuestion2 = new Question(1, "this is a question2 personal question for fred concerning his past...", "3", "4", "5", "3");
        Question anotherPersonalQuestion3 = new Question(2, "this is a question3 personal question for fred concerning his past...", "3", "4", "5", "3");
        FredsQuestions3.addQuestion(anotherPersonalQuestion1);
        FredsQuestions3.addQuestion(anotherPersonalQuestion2);
        FredsQuestions3.addQuestion(anotherPersonalQuestion3);


        Question Camq1 = new Question(0, "this is a question personal question1 for Camille", "3", "4", "5", "3");
        Question Camq2 = new Question(1, "this is a question personal question2 for Camille", "3", "4", "5", "3");
        Question Camq3 = new Question(2, "this is a question personal question3 for Camille", "3", "4", "5", "3");
        Camquestions1.addQuestion(Camq1);
        Camquestions1.addQuestion(Camq2);
        Camquestions1.addQuestion(Camq3);


        Question newCamq1 = new Question(0, "this is another question personal question1 for Camille", "3", "4", "5", "3");
        Question newCamq2 = new Question(1, "this is another question personal question2 for Camille", "3", "4", "5", "3");
        Question newCamq3 = new Question(2, "this is another question personal question3 for Camille", "3", "4", "5", "3");
        Camquestions2.addQuestion(newCamq1);
        Camquestions2.addQuestion(newCamq2);
        Camquestions2.addQuestion(newCamq3);



        //Creating  Quizz
        //fred
        QuizPersonnel quizFred1=new QuizPersonnel(0,"Fred",1,"Quiz Personnel de Fred - Été 2018","Ce quiz amusant parle des souvenirs que vous avez eu l'été dernier avec votre famille",Fredquestions1);
        QuizPersonnel quizFred2=new QuizPersonnel(1,"Fred",1,"Fred's second Personal Quiz","Quiz Description...",FredsQuestions2);
        QuizPersonnel quizFred3=new QuizPersonnel(2,"Fred",1,"Fred's third Personal Quiz","Quiz Description...",FredsQuestions3);

        //camille
        QuizPersonnel quizCam1=new QuizPersonnel(0,"Camille",2,"Quiz familial de Camille","Il est temps de se rememorer le temps passé avec votre famille!",Camquestions1);
        QuizPersonnel quizCam2=new QuizPersonnel(1,"Camille",2,"Quiz Personnel de Camille - Votre Hiver 2019!   ","Il est temps de se rememorer votre meilleur hiver!",Camquestions2);



        //adding those quiz to the personal quiz collection

        //les quiz de fred
        this.personalQuizs.add(quizFred1);
        this.personalQuizs.add(quizFred2);
        this.personalQuizs.add(quizFred3);

        // les quiz de camille
        this.personalQuizs.add(quizCam1);
        this.personalQuizs.add(quizCam2);




    }

    public  ArrayList<QuizPersonnel> getPersonalQuizs() {
        return personalQuizs;
    }
    public  QuizPersonnel getQuizById(int id) {
        return personalQuizs.get(id);
    }
    public  ArrayList<QuizPersonnel> getQuizByName(String name) {
        ArrayList<QuizPersonnel> quiz= new ArrayList<QuizPersonnel>();
        for (int i = 0; i <personalQuizs.size() ; i++) {
            if (personalQuizs.get(i).residentName.equals(name)){
                quiz.add(personalQuizs.get(i));
            }

        }

        return quiz;
    }

    public void ajouterQuizPersonnel(int id,QuizPersonnel quiz){
                this.personalQuizs.add(quiz);
    }

    public int getSize(){
        return this.personalQuizs.size();
    }
}
