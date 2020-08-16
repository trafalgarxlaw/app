package com.example.myapplication.views.admin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.asynctask.Network
import com.example.myapplication.R
import com.example.myapplication.recyclerview.recyclerViewAdapter

class ResultsDetailsView : AppCompatActivity(),
    recyclerViewAdapter.OnItemClickListener {

    var user_id =-1;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detailsresults)

        //recycler View Object
        var recyclerView: RecyclerView


        val QuizzList = Network().execute().get();
        this.user_id = intent.getIntExtra("UserId",-1);


        recyclerView=findViewById(R.id.ConsultUserAnswers_recycleView);

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
        System.out.println("MYY USER ID "+this.user_id )
        //creating Intent
        val myintent = Intent(this, UserAnswersView::class.java)
        myintent.putExtra("QuizID",position);
        myintent.putExtra("UserId",this.user_id );

        startActivity(myintent);

    }

}
