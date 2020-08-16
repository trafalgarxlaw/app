package com.example.myapplication.views.user.personalViews

//<<<<<<< HEAD:Quiz/app/src/main/java/com/example/myapplication/QuestionView.kt

//class QuestionView : AppCompatActivity(){

//=======

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import android.graphics.BitmapFactory
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide.with
import com.example.myapplication.R
import com.example.myapplication.asynctask.*
import com.example.myapplication.datatypes.TremblingAverage
import com.example.myapplication.main.MainActivity
import com.example.myapplication.models.Question
import com.example.myapplication.sensors.DeviceSensorManager
import com.example.myapplication.views.user.minigame.Memorygame
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_questionview.*
import java.net.URL


class PersonalQuestionView : AppCompatActivity(), SensorEventListener {
    //private lateinit var player:MediaPlayer

    private var questionRepondu: Boolean = false

    var userid: String = "0"
    var Quiz_id = -1;
    var Question_id = -1;

    private var myDeviceManager = DeviceSensorManager();


    private var adaptationEstActive: Boolean = false
    private var adaptationTexte: Boolean = false
    private var quizPersonnel: Boolean = false
    private var Resident_Name= ""
    lateinit private var  audioName : String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionview)
        myDeviceManager.initialiseSensorManager(this);
        //Here i get the Quiz Id from the recucle View
        Quiz_id = intent.getIntExtra("Quiz_id", -1);
        Question_id = intent.getIntExtra("Question_id", -1);
        userid = intent.getIntExtra("UserId", -1).toString();
        Resident_Name = intent.getStringExtra("Resident_Name");
        quizPersonnel = intent.getBooleanExtra("quizPersonnel", false)

        var personalMedia= "http://10.0.2.2:8080/static/"+Resident_Name+"/PersonalQuiz"+Quiz_id+"/Question"+Question_id+"/image.jpg"
        var imageview=findViewById<ImageView>(R.id.personalImageView)
        System.out.println("static path : "+personalMedia)

        if (personalMedia !== null) {
            Picasso.get().load(personalMedia).into(imageview);
        }else{
            System.out.println("error on loading img")
        }


        System.out.println("the bugged id : " + this.userid)
        getAdaptation(intent.getIntExtra("UserId", -1))


        var bundle = intent.extras;
        if (bundle != null && Question_id != 0) {
            myDeviceManager.list = (bundle.getSerializable("list") as? ArrayList<TremblingAverage>)!!
            myDeviceManager.time = intent.getFloatExtra("time", -1f);
        }

        val network = GetPersonalQuiz();
        network.setResidentName(Resident_Name);
        val QuizzList = network.execute().get();
        val SelectedQuiz = QuizzList.QuizArray.get(Quiz_id);
        val CurrentQuestion = SelectedQuiz.questions.questions[Question_id] as Question;

        println("Current question media :" + CurrentQuestion.media)
        if(!CurrentQuestion.media.toString().equals("null")){
             //A déboguer! par ouss, le son ne veut plus jouer..
           // createMedia()
            createButtonAudio()
            this.audioName = CurrentQuestion.media
        }
        //The group button
        val groupButton = findViewById<RadioGroup>(R.id.radioGroup)

        //the question sentence
        val questionview = findViewById<TextView>(R.id.question)
        questionview.text = CurrentQuestion.questionSentence
        findViewById<TextView>(R.id.choix_reponse).setText("Choisissez une réponse")

        //Button Choices
        val choice1 = findViewById<Button>(R.id.radioButton);
        val choice2 = findViewById<Button>(R.id.radioButton2);
        val choice3 = findViewById<Button>(R.id.radioButton3);

        choice1.text = CurrentQuestion.choixUn;
        choice2.text = CurrentQuestion.choixDeux;
        choice3.text = CurrentQuestion.choixTrois;
        if (this.adaptationTexte) {
            questionview.textSize = 30F
            choice1.textSize = 30F
            choice2.textSize = 30F
            choice3.textSize = 30F
            findViewById<TextView>(R.id.choix_reponse).textSize = 30F
        } else {
            questionview.textSize = 20F
            choice1.textSize = 20F
            choice2.textSize = 20F
            choice3.textSize = 20F
            findViewById<TextView>(R.id.choix_reponse).textSize = 20F
        }
        val residentsList = UserNetwork().execute().get();

        findViewById<Button>(R.id.NextQuestionButton).setOnClickListener() {
            if (radioGroup.checkedRadioButtonId != -1) {
                sendUserAnswer()
                val bonneReponse = verifierReponse(CurrentQuestion)
                if (!questionRepondu) {
                    val putResult = PutResult()
                    if(this.quizPersonnel) {
                        putResult.quizPersonnel = true;
                    }
                    putResult.setBonneReponse(bonneReponse)
                    putResult.setId(this.userid)
                    putResult.execute()
                    this.questionRepondu = true
                    if (!bonneReponse) {
                        residentsList.getResidentById(Integer.parseInt(this.userid)).addwrongAnswer();
                        if (this.adaptationEstActive) {
                            desactiverReponse(radioGroup.checkedRadioButtonId)
                        } else {
                            // If the question id == 2 , we play the mini memory game
                            if(Question_id==0){
                                runMemoryGame(Integer.parseInt(userid), Quiz_id, Question_id + 1, SelectedQuiz.questions.questions.size, myDeviceManager.time, Resident_Name,myDeviceManager)
                            }else{
                                startNewIntent(Integer.parseInt(userid), Quiz_id, Question_id + 1, SelectedQuiz.questions.questions.size, myDeviceManager.time, Resident_Name)

                            }
                        }


                    } else {
                        // If the question id == 2 , we play the mini memory game
                        if(Question_id==0){
                            runMemoryGame(Integer.parseInt(userid), Quiz_id, Question_id + 1, SelectedQuiz.questions.questions.size, myDeviceManager.time, Resident_Name,myDeviceManager)
                        }else{
                            residentsList.getResidentById(Integer.parseInt(this.userid)).addCorrectAnswer();
                            startNewIntent(Integer.parseInt(userid), Quiz_id, Question_id + 1, SelectedQuiz.questions.questions.size, myDeviceManager.time, Resident_Name)
                        }

                    }
                } else {

                    if (!bonneReponse) {
                        desactiverReponse(radioGroup.checkedRadioButtonId)
                    } else {
                        // If the question id == 2 , we play the mini memory game
                        if(Question_id==0){
                            runMemoryGame(Integer.parseInt(userid), Quiz_id, Question_id + 1, SelectedQuiz.questions.questions.size, myDeviceManager.time, Resident_Name,myDeviceManager)
                        }else{
                            startNewIntent(Integer.parseInt(userid), Quiz_id, Question_id + 1, SelectedQuiz.questions.questions.size, myDeviceManager.time, Resident_Name)
                        }
                    }
                }
            } else {
                afficherChoixReponse()
            }
        }

    }

    fun runMemoryGame(userId: Int, QuizId: Int, question_id: Int, size: Int, time: Float, Resident_Name: String,myDeviceManager: DeviceSensorManager){

        // If the question id == 2 , we play the mini memory game

            val memGameintent = Intent(this, Memorygame::class.java)
            var bundle = Bundle();
            bundle.putSerializable("list", myDeviceManager.list)
            memGameintent.putExtras(bundle)
            memGameintent.putExtra("Quiz_id", QuizId);
        memGameintent.putExtra("sizeQuestions", size);
        memGameintent.putExtra("Question_id", question_id);
            memGameintent.putExtra("UserId", userId)
            memGameintent.putExtra("time", time)
            memGameintent.putExtra("Resident_Name", Resident_Name);
            memGameintent.putExtra("quizPersonnel", this.quizPersonnel)
            startActivity(memGameintent);
    }

    fun getAdaptation(userId: Int) {
        val GetAdaptationService = GetAdaptationAlzheimer()
        GetAdaptationService.setId(userId)
        this.adaptationEstActive = GetAdaptationService.execute().get() as Boolean
        val GetAdaptationTexteService = GetAdaptationTexte()
        GetAdaptationTexteService.setId(userId)
        this.adaptationTexte = GetAdaptationTexteService.execute().get() as Boolean
    }

    fun afficherChoixReponse() {
        Toast.makeText(this, "Une option doit être sélectionné!", Toast.LENGTH_LONG).show()
    }

    fun desactiverReponse(id: Int) {
        findViewById<RadioButton>(id).visibility = View.INVISIBLE
    }

    fun verifierReponse(questionActuelle: Question): Boolean {
        return findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.equals(questionActuelle.reponse)
    }

    fun sendUserAnswer() {

        val sendAnswer = SendUserAnswer();
        sendAnswer.setUser_id(Integer.parseInt(this.userid))
        sendAnswer.setQuiz_id(this.Quiz_id)
        sendAnswer.setQuestion_id(this.Question_id)
        sendAnswer.setAnswer(Integer.parseInt(findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text as String))
        sendAnswer.execute()

    }


    fun traiterReponse(questionActuelle: Question) {
        val putResult = PutResult()
        putResult.setBonneReponse(verifierReponse(questionActuelle))
    }

    fun startNewIntent(userId: Int, QuizId: Int, question_id: Int, size: Int, time: Float, Resident_Name: String) {
        if (question_id >= size) {
            val menuIntent = Intent(this, MainActivity::class.java)
            //sending the data before returnng to menu
            sendTremblingData()
            startActivity(menuIntent)
        } else {
            val questionsIntent = Intent(this, PersonalQuestionView::class.java)
            var bundle = Bundle();
            bundle.putSerializable("list", myDeviceManager.list)
            questionsIntent.putExtras(bundle)


            questionsIntent.putExtra("Quiz_id", QuizId);
            questionsIntent.putExtra("Question_id", question_id);
            questionsIntent.putExtra("UserId", userId)
            questionsIntent.putExtra("time", time)
            questionsIntent.putExtra("Resident_Name", Resident_Name);
            questionsIntent.putExtra("quizPersonnel", this.quizPersonnel)
            val formLayout = findViewById(R.id.AudioLayout) as LinearLayout
            formLayout.removeAllViews()
            startActivity(questionsIntent);
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onPause() {
        super.onPause()
        if (myDeviceManager.is_accelerometerSensor_available) {
            myDeviceManager.sensorManager!!.unregisterListener(this)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        myDeviceManager.onSensorChanged(event);
    }

    fun sendTremblingData() {

        for (tremblingData in myDeviceManager.list) {
            var Data = SendTremblingData();
            Data.setId(Integer.parseInt(userid));
            Data.setAvg(tremblingData.average);
            Data.setTime(tremblingData.time);
            Data.execute();
        }

    }

    override fun onPostResume() {
        super.onPostResume()
        if (myDeviceManager.is_accelerometerSensor_available) {
            myDeviceManager.sensorManager!!.registerListener(
                    this, myDeviceManager.accelerometerSensor,
                    SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    /*fun playAudio() {
        player.start()
    }*/

    fun createButtonAudio() {
        val myButton = Button(this)
        myButton.text = "Jouer"
        val ll = findViewById(R.id.AudioLayout) as LinearLayout
        val lp = ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
        ll.addView(myButton, lp)
        myButton.setOnClickListener {
            playAudio()
        }
    }
    fun playAudio(){
        try{
            var uri = Uri.parse ("http://10.0.2.2:8080/static/" + this.audioName);
            println("Le uri string " + uri.toString())
            val player = MediaPlayer()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                player.setAudioAttributes(AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                        .build())
            }else{
                player.setAudioStreamType(AudioManager.STREAM_MUSIC)
            }
            player.setOnCompletionListener {
                player.release()
            }
            player.setDataSource(this, uri);
            player.prepare();
            player.start()
        } catch(e: Exception)
        {
            println(e.toString());
            Toast.makeText(this, "Une erreur est survenu avec l'audio. ", Toast.LENGTH_LONG).show()
        }
    }
}


