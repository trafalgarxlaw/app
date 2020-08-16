package com.example.myapplication.views.admin

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.asynctask.UserNetwork
import com.example.myapplication.collections.ResidentsCollection
import com.example.myapplication.models.Quiz
import com.example.myapplication.models.QuizPersonal
import com.example.myapplication.recyclerview.recyclerViewAdapter


class UserListView : AppCompatActivity(),
    recyclerViewAdapter.OnItemClickListener {
    private var residentsList = UserNetwork().execute().get()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        //residentsList = UserNetwork().execute().get();

        //recycler View Object
         var recyclerView: RecyclerView



        var PageTitle = findViewById<TextView>(R.id.RecycleViewPageTitle)
        PageTitle.setText("Selectionnez un dossier de suivis à ouvrir")


        recyclerView=findViewById(R.id.recyclerView);
        //initialise myadapterObject and passing the elements to display
        val myAdapter=
            recyclerViewAdapter(
                this,
                residentsList,
                this,
                2
            );
        recyclerView.adapter = myAdapter;
        recyclerView.layoutManager =  LinearLayoutManager(this);

    }

    //when the user choose a quiz
    override fun onItemClick(position: Int) {
        //creating Intent
        if(intent.getBooleanExtra("quizPersonnalise", false)){
            val quizPersonnaliseIntent = Intent(this, QuizPersonnalise::class.java)
            quizPersonnaliseIntent.putExtra("UserId",position);
            var quiz = QuizPersonal(position, residentsList.getResidentById(position).firstname)
            quizPersonnaliseIntent.putExtra("quiz",quiz)
            quizPersonnaliseIntent.putExtra("resident_name",residentsList.getResidentById(position).firstname);
            startActivity(quizPersonnaliseIntent);
        }else{
            val UserResultsIntent = Intent(this, UserResultsView::class.java)
            UserResultsIntent.putExtra("UserId",position);
            startActivity(UserResultsIntent);
        }


    }
}