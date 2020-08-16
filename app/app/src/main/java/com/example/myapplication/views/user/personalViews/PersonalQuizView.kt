package com.example.myapplication.views.user.personalViews

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.asynctask.GetPersonalQuiz
import com.example.myapplication.views.user.minigame.Memorygame

class PersonalQuizView: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizview)

        //Here i get the Quiz Id from the recucle View
        var Quiz_id = intent.getIntExtra("QuizID",-1);
        var userid = intent.getIntExtra("UserId",-1);
        var Resident_Name=intent.getStringExtra("Resident_Name");



        val network = GetPersonalQuiz();
        network.setResidentName(Resident_Name);
        val QuizzList = network.execute().get();
        val SelectedQuiz=QuizzList.QuizArray.get(Quiz_id);

        //setting up the title
        val TitleView = findViewById<TextView>(R.id.TitleTextView)
        TitleView.setText(SelectedQuiz.Title);

        //When play is pressed
        val playQuizButton=findViewById<Button>(R.id.playquiz);
        playQuizButton.setOnClickListener{
            //creating Intent
            val questionsIntent = Intent(this, PersonalQuestionView::class.java)
            var Question_id=0;
            questionsIntent.putExtra("Quiz_id",Quiz_id);
            questionsIntent.putExtra("Question_id",Question_id);
            questionsIntent.putExtra("UserId",userid);
            questionsIntent.putExtra("Resident_Name",Resident_Name);
            questionsIntent.putExtra("quizPersonnel", true);

            startActivity(questionsIntent);
        }

    }
}