package com.example.myapplication.views.user.usermenu

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.asynctask.UserNetwork
import com.example.myapplication.recyclerview.recyclerViewAdapter

class UserSelectionView : AppCompatActivity(), recyclerViewAdapter.OnItemClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val residentsList = UserNetwork().execute().get();
        var recyclerView: RecyclerView

        var PageTitle = findViewById<TextView>(R.id.RecycleViewPageTitle)
        PageTitle.setText("Je suis ...")


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
        val UserResultsIntent = Intent(this, UserMenu::class.java)
        UserResultsIntent.putExtra("UserId",position);
        startActivity(UserResultsIntent);

    }
}