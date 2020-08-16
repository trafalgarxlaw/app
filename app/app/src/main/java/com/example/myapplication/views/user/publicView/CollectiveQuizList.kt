package com.example.myapplication.views.user.publicView

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.asynctask.Network
import com.example.myapplication.R
import com.example.myapplication.recyclerview.recyclerViewAdapter


class CollectiveQuizList : AppCompatActivity(),
    recyclerViewAdapter.OnItemClickListener {

    var userid=-1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list)


        val QuizzList = Network().execute().get();

        //recycler View Object
        lateinit var recyclerView: RecyclerView

        userid = intent.getIntExtra("UserId",-1);



        recyclerView=findViewById(R.id.recyclerView);
        //initialise myadapterObject and passing the elements to display
        val myAdapter=
            recyclerViewAdapter(
                this,
                QuizzList,
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
        val quizIntent = Intent(this, QuizView::class.java)
        quizIntent.putExtra("QuizID",position);
        quizIntent.putExtra("UserId",userid);

        startActivity(quizIntent);

    }
}