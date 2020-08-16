package com.example.myapplication.views.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class AdminMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_menu)

        val ListUserFoildersButton = findViewById<Button>(R.id.ListUserFolders)
        ListUserFoildersButton.setOnClickListener {

            startActivity(Intent(this, UserListView::class.java))

        }

        val quizPersonnaliseBouton = findViewById<Button>(R.id.quizPersonnaliseButton)
        quizPersonnaliseBouton.setOnClickListener{
            intent = Intent(this, UserListView::class.java)
            intent.putExtra("quizPersonnalise", true);
            startActivity(intent)

        }
    }
}