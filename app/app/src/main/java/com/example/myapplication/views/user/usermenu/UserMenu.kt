package com.example.myapplication.views.user.usermenu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.asynctask.UserNetwork
import com.example.myapplication.views.user.personalViews.PersonalQuizList
import com.example.myapplication.views.user.publicView.CollectiveQuizList

class UserMenu : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_menu)

        val residentsList = UserNetwork().execute().get();


        var title=findViewById<TextView>(R.id.UserMenuText)

        var userid = intent.getIntExtra("UserId",-1);

        title.setText("Menu Utilisateur - " +residentsList.getResidentById(userid).firstname)



        // when this button is pressed, all the all the collective quiz are listed in a page.
        val listQuizButton = findViewById<Button>(R.id.ListUserFolders)
        val listPersonalQuizButton = findViewById<Button>(R.id.ListPersonalQuizButton)


        listQuizButton.setOnClickListener{
            startActivity(Intent(this,
                CollectiveQuizList::class.java).putExtra("UserId",userid))
        }

        listPersonalQuizButton.setOnClickListener{
            startActivity(Intent(this,
                PersonalQuizList::class.java).putExtra("UserId",userid).putExtra("Resident_Name",residentsList.getResidentById(userid).firstname))
        }
    }
}