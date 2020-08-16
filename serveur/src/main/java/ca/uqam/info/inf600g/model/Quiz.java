package ca.uqam.info.inf600g.model;

import ca.uqam.info.inf600g.data.QuestionCollection;

public class Quiz {
	
	private static Quiz database;

	public static Quiz getAccess() {
		if(database == null) {
			database = new Quiz();
		}
		return database;

	}



	public int id;
	public String Title;
	public String Description;
	public int numberQuestion;
	public QuestionCollection questions;


	Quiz() {
		this.id = 1;
		this.Title="Quiz Title";
		this.Description="Quiz Description";
		this.questions = new QuestionCollection();
	    initialize();
	}

	public Quiz(int id, String Title, String Description ,QuestionCollection questions){
		this.id = id;
		this.Title=Title;
		this.Description=Description;
		this.questions=questions;
	}

	// adding some question to a quizz when initialized
	private void initialize() {
		Question q1 = new Question(0, "Combien de cote contient un carre?", "1", "4", "6", "4");
		this.questions.addQuestion(q1);
		Question q2 = new Question(1, "Combien de cote contient un triangle", "3", "4", "5", "3");
		this.questions.addQuestion(q2);
	}


	public Question getOneQuestion(int id){
		return this.questions.getOneSpecificQuestion(id);
	}

	public boolean containsKey(String id){
		return this.questions.containsKey(id);
	}

}
