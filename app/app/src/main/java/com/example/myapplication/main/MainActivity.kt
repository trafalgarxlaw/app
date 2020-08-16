package com.example.myapplication.main

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import com.example.myapplication.views.admin.AdminMenu
import com.example.myapplication.asynctask.Network;
import com.example.myapplication.R
import com.example.myapplication.views.user.usermenu.UserSelectionView

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val utilisateur = findViewById<Button>(R.id.AsUser)
        val administrateur = findViewById<Button>(R.id.AsAdmin)

        // this is either 1(proceed as a user) or 2 (proceed as a admin)
        var choice=0;
        System.out.println("start");

        val QuizzList = Network().execute().get();





        utilisateur.setOnClickListener{
            utilisateur.setBackgroundColor(Color.parseColor("#FFFFFF"));
            administrateur.setBackgroundColor(Color.parseColor("#343434"));
            choice=1;
        }
        administrateur.setOnClickListener{
            utilisateur.setBackgroundColor(Color.parseColor("#343434"));
            administrateur.setBackgroundColor(Color.parseColor("#FFFFFF"));
            choice=2;
        }

        //proceed depending on the choice
        val proceed = findViewById<Button>(R.id.NextButton)
        proceed.setOnClickListener{
            if (choice==1){
                //user activity
                startActivity(Intent(this, UserSelectionView::class.java))
            }else if(choice==2){
                //admin activity
                startActivity(Intent(this, AdminMenu::class.java))
            }
        }


    }
}