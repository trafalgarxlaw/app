package com.example.myapplication.views.user.minigame

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.R.drawable.*
import com.example.myapplication.asynctask.updateMiniGameScore
import com.example.myapplication.datatypes.TremblingAverage
import com.example.myapplication.main.MainActivity
import com.example.myapplication.sensors.DeviceSensorManager
import com.example.myapplication.views.user.personalViews.PersonalQuestionView
import kotlinx.android.synthetic.main.memory_game.*
import java.util.*
import kotlin.collections.ArrayList


class Memorygame : AppCompatActivity() {

    var cardBack = treasure
    var images: MutableList<Int> = mutableListOf(face_mask, no_flight, no_handshake, no_touch,
        protection, washing_hands,face_mask, no_flight, no_handshake, no_touch,
        protection, washing_hands)


    private var myDeviceManager = DeviceSensorManager();
    private var Quiz_id=-1;
    private var Question_id=-1;
    private var userid=-1;
    private var Resident_Name=" "
    private var sizeQuestions=0;

    //timer
    private val START_TIME_IN_MILLIS: Long = 10000
    private var mTimerRunning = false
    private var mTimeLeftInMillis = START_TIME_IN_MILLIS



    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.memory_game)
        myDeviceManager.initialiseSensorManager(this);

        var buttons = arrayOf(button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,button11,button12)

        cardBack = treasure
        images= mutableListOf(face_mask, no_flight, no_handshake, no_touch,
            protection, washing_hands,face_mask, no_flight, no_handshake, no_touch,
            protection, washing_hands)

        val mTextViewCountDown = findViewById<TextView>(R.id.text_view_countdown);
        val mButtonStartPause = findViewById<Button>(R.id.button_start_pause);

        mButtonStartPause.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (mTimerRunning) {
//                    pauseTimer()
                } else {
                    startTimer(mButtonStartPause,mTextViewCountDown,buttons)
                }
            }
        })
        updateCountDownText(mTextViewCountDown);


        Quiz_id = intent.getIntExtra("Quiz_id", -1);
         Question_id = intent.getIntExtra("Question_id", -1);
         userid = intent.getIntExtra("UserId", -1);
        sizeQuestions = intent.getIntExtra("sizeQuestions", 0);

        Resident_Name = intent.getStringExtra("Resident_Name");
        var bundle = intent.extras;
        if (bundle != null && Question_id != 0) {
            myDeviceManager.list = (bundle.getSerializable("list") as? ArrayList<TremblingAverage>)!!
            myDeviceManager.time = intent.getFloatExtra("time", -1f);
        }

    }

    fun startNewIntent() {

        System.out.println("id"+Question_id)
        System.out.println("size"+sizeQuestions)

        if (Question_id>=sizeQuestions){
            val menuIntent = Intent(this, MainActivity::class.java)
            startActivity(menuIntent)
        }else{
            val questionsIntent = Intent(this, PersonalQuestionView::class.java)
            var bundle = Bundle();
            bundle.putSerializable("list", myDeviceManager.list)
            questionsIntent.putExtras(bundle)


            questionsIntent.putExtra("Quiz_id", Quiz_id);
            questionsIntent.putExtra("Question_id", Question_id);
            questionsIntent.putExtra("UserId", userid)
            questionsIntent.putExtra("time", myDeviceManager.time)
            questionsIntent.putExtra("Resident_Name", Resident_Name);
            questionsIntent.putExtra("quizPersonnel", true)
            startActivity(questionsIntent);

        }

    }

    private fun startTimer(mButtonStartPause: Button,mTextViewCountDown:TextView,buttons:Array<Button>) {
        //randomize the images
        images.shuffle()


        var mCountDownTimer: CountDownTimer;
        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText(mTextViewCountDown)
                for (i:Int in 0..11) {
                    buttons[i].setBackgroundResource(images[i])
                    buttons[i].setText(images[i])
                    buttons[i].textSize = 0.0F
                }
            }

            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            override fun onFinish() {
                mTimerRunning = false
                mButtonStartPause!!.text = "Start"
                mButtonStartPause.visibility = View.INVISIBLE

                for (i:Int in 0..11) {
                    buttons[i].setBackgroundResource(cardBack)
                    buttons[i].text = "cardBack"
                    buttons[i].textSize = 0.0F
                }
                startGame(buttons)
            }
        }.start()
        mTimerRunning = true
        mButtonStartPause!!.text = "pause"
        mButtonStartPause.visibility = View.INVISIBLE

    }
    private fun updateCountDownText(mTextViewCountDown:TextView) {
        val minutes = (mTimeLeftInMillis / 1000).toInt() / 60
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60
        val timeLeftFormatted: String =
            java.lang.String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        mTextViewCountDown!!.text = timeLeftFormatted
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun startGame(buttons:Array<Button>){
        val alertDialogBuilder = AlertDialog.Builder(this)



        var clicked = 0
        var turnOver = false
        var lastClicked = -1
        var totalcardsturned=0;

        //score
        var score = 0f;
        var goodmatchs=0f;
        var wrongmatchs=0f;
        var totalmatchs= 0f;


        for (i:Int in 0..11){
            buttons[i].setBackgroundResource(cardBack)
            buttons[i].text = "cardBack"
            buttons[i].textSize = 0.0F
            buttons[i].setOnClickListener {
                if (buttons[i].text == "cardBack" && !turnOver) {
                    buttons[i].setBackgroundResource(images[i])
                    buttons[i].setText(images[i])
                    if (clicked == 0) {
                        lastClicked = i
                    }
                    clicked++
                } else if (buttons[i].text !in "cardBack") {
                    buttons[i].setBackgroundResource(cardBack)
                    buttons[i].text = "cardBack"
                    clicked--
                }

                if (clicked == 2) {
                    turnOver = true
                    totalmatchs++;
                    if (buttons[i].text == buttons[lastClicked].text) {
                        //its a match
                        goodmatchs++;
                        totalcardsturned += 2;

                        buttons[i].isClickable = false
                        buttons[lastClicked].isClickable = false
                        turnOver = false
                        clicked = 0

                        alertDialogBuilder.setTitle("C'est un match !")
                        alertDialogBuilder.setMessage("Continuez comme ca ...")
                        alertDialogBuilder.show()
                        alertDialogBuilder.setOnDismissListener { dialog ->
                            dialog.dismiss()
                        }
                    }else{
                        // its not a match
                        wrongmatchs++;
                    }
                } else if (clicked == 0) {
                    turnOver = false
                }

                // if all cards have been returned
                if (totalcardsturned==images.size){
                    alertDialogBuilder.setTitle("Felicitations !")
                    alertDialogBuilder.setMessage("Vous avez terminé le mini jeu")
                    alertDialogBuilder.show()
                    alertDialogBuilder.setOnDismissListener { dialog ->
                        dialog.dismiss()
                    }

                    score = ((goodmatchs / totalmatchs) * 100).toFloat();

                    // sending score to server
                    var network = updateMiniGameScore()
                    network.setId(userid)
                    network.setScore(score.toInt())
                    network.execute()

                    //continiue the quiz
                    startNewIntent()

                }
            }

        }
    }

}