package com.example.myapplication.views.user.publicView

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.asynctask.Network
import com.example.myapplication.R

class QuizView: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizview)

        //Here i get the Quiz Id from the recucle View
        var Quiz_id = intent.getIntExtra("QuizID",-1);
        var userid = intent.getIntExtra("UserId",-1);



        val QuizzList = Network().execute().get();
        val SelectedQuiz=QuizzList.QuizArray.get(Quiz_id);

        //setting up the title
        val TitleView = findViewById<TextView>(R.id.TitleTextView)
        TitleView.setText(SelectedQuiz.Title);

        //When play is pressed
        val playQuizButton=findViewById<Button>(R.id.playquiz);
        playQuizButton.setOnClickListener{
            //creating Intent
            val questionsIntent = Intent(this, QuestionView::class.java)
            var Question_id=0;
            questionsIntent.putExtra("Quiz_id",Quiz_id);
            questionsIntent.putExtra("Question_id",Question_id);
            questionsIntent.putExtra("UserId",userid);

            startActivity(questionsIntent);
        }



    }
}