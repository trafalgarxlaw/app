package com.example.myapplication.views.user.personalViews

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.asynctask.GetPersonalQuiz
import com.example.myapplication.recyclerview.recyclerViewAdapter

class PersonalQuizList : AppCompatActivity(),
    recyclerViewAdapter.OnItemClickListener {


    var userid=-1
    var Resident_Name="";
    var lastQuiz_id = 0;




    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list)

        //recycler View Object
        lateinit var recyclerView: RecyclerView

        val title=findViewById<TextView>(R.id.RecycleViewPageTitle);
        title.text = "Liste des Quiz personnels"

        userid = intent.getIntExtra("UserId",-1);
        Resident_Name=intent.getStringExtra("Resident_Name");


        val network = GetPersonalQuiz();
        network.setResidentName(Resident_Name);
        val personalQuiz = network.execute().get();



        recyclerView=findViewById(R.id.recyclerView);
        //initialise myadapterObject and passing the elements to display
        val myAdapter=
            recyclerViewAdapter(
                this,
                personalQuiz,
                this,
                1
            );
        recyclerView.adapter = myAdapter;
        recyclerView.layoutManager =  LinearLayoutManager(this);

    }

    //when the user choose a quiz
    override fun onItemClick(position: Int) {
        Log.d("TAG", "onItemClick: "+ position)

        //creating Intent
        val quizIntent = Intent(this, PersonalQuizView::class.java)
        quizIntent.putExtra("QuizID",position);
        quizIntent.putExtra("UserId",userid);
        quizIntent.putExtra("Resident_Name",Resident_Name);


        startActivity(quizIntent);

    }
}