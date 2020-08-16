package com.example.myapplication.views.admin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.asynctask.Network
import com.example.myapplication.R
import com.example.myapplication.asynctask.UserNetwork
import com.example.myapplication.recyclerview.recyclerViewAdapter

class UserAnswersView: AppCompatActivity(),
    recyclerViewAdapter.OnItemClickListener {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_answers_view)

        val residentsList = UserNetwork().execute().get();
        val QuizzList = Network().execute().get();
        //recycler View Object
        lateinit var recyclerView: RecyclerView


        var user_id = intent.getIntExtra("UserId",-1);
        var Quiz_id = intent.getIntExtra("QuizID",-1);

        val SelectedQuiz=QuizzList.QuizArray.get(Quiz_id);
        System.out.println("FOR THE USER ID :"+user_id+" THE JSONARRAY");


        residentsList.getResidentById(user_id).generateUserAnswers();


        val PageTitle = findViewById<TextView>(R.id.userAnswersText_Title)

        PageTitle.text="Les reponses de "+residentsList.getResidentById(user_id).firstname+ " Pour le Quiz: "+SelectedQuiz.Title;


        recyclerView=findViewById(R.id.QuestionAnswersRecycleView);
        //initialise myadapterObject and passing the elements to display
        val myAdapter=
            recyclerViewAdapter(
                this,
                SelectedQuiz,
                residentsList.getResidentById(user_id),
                this,
                3
            );
        recyclerView.adapter = myAdapter;
        recyclerView.layoutManager =  LinearLayoutManager(this);


    }

    override fun onItemClick(position: Int) {
        //creating Intent
//            val myintent = Intent(this, UserAnswersView::class.java)
//
//        startActivity(myintent);

    }
}