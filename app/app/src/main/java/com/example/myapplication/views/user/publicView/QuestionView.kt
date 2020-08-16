package com.example.myapplication.views.user.publicView

import android.annotation.SuppressLint
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
//<<<<<<< HEAD:Quiz/app/src/main/java/com/example/myapplication/QuestionView.kt
import kotlinx.android.synthetic.main.activity_questionview.*

//class QuestionView : AppCompatActivity(){

//=======
import com.example.myapplication.R
import com.example.myapplication.asynctask.*
import com.example.myapplication.datatypes.TremblingAverage
import com.example.myapplication.main.MainActivity
import com.example.myapplication.models.Question
import com.example.myapplication.sensors.DeviceSensorManager


class QuestionView : AppCompatActivity(), SensorEventListener {

    private var questionRepondu : Boolean = false

    var userid : String = "0"
    var Quiz_id=-1;
    var Question_id=-1;

    private var myDeviceManager =  DeviceSensorManager();
    private var quizPersonnel = false

    private var adaptationEstActive: Boolean = false
    private var adaptationTexte: Boolean = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionview)

        myDeviceManager.initialiseSensorManager(this);


        //Here i get the Quiz Id from the recucle View
        Quiz_id = intent.getIntExtra("Quiz_id",-1);
        Question_id=intent.getIntExtra("Question_id",-1);
        userid = intent.getIntExtra("UserId",-1).toString();
        System.out.println("the bugged id : "+this.userid)
        getAdaptation(intent.getIntExtra("UserId",-1))
        //Permet d'identifier si on est dans un quiz personnel ou bien un quiz collectif


        var bundle =  intent.extras;
        if (bundle != null && Question_id !=0 ) {
            myDeviceManager.list= (bundle.getSerializable("list") as? ArrayList<TremblingAverage>)!!
            myDeviceManager.time =  intent.getFloatExtra("time",-1f);
        }

        val QuizzList = Network().execute().get();
        val SelectedQuiz=QuizzList.QuizArray.get(Quiz_id);
        val CurrentQuestion = SelectedQuiz.Questions.questions[Question_id] as Question;

        //The group button
        val groupButton = findViewById<RadioGroup>(R.id.radioGroup)

        //the question sentence
        val questionview = findViewById<TextView>(R.id.question)
        questionview.text = CurrentQuestion.questionSentence
        findViewById<TextView>(R.id.choix_reponse).setText("Choisissez une réponse")

        //Button Choices
        val choice1=findViewById<Button>(R.id.radioButton);
        val choice2=findViewById<Button>(R.id.radioButton2);
        val choice3=findViewById<Button>(R.id.radioButton3);

        choice1.text =CurrentQuestion.choixUn;
        choice2.text = CurrentQuestion.choixDeux;
        choice3.text = CurrentQuestion.choixTrois;
        if (this.adaptationTexte){
            questionview.textSize = 30F
            choice1.textSize = 30F
            choice2.textSize = 30F
            choice3.textSize = 30F
            findViewById<TextView>(R.id.choix_reponse).textSize = 30F
        }else{
            questionview.textSize =20F
            choice1.textSize = 20F
            choice2.textSize = 20F
            choice3.textSize = 20F
            findViewById<TextView>(R.id.choix_reponse).textSize = 20F
        }
        val residentsList = UserNetwork().execute().get();

        findViewById<Button>(R.id.NextQuestionButton).setOnClickListener(){
            if (radioGroup.checkedRadioButtonId != -1){
                sendUserAnswer()
                print("\n Dans le bon choix : \n")
                val bonneReponse = verifierReponse(CurrentQuestion)
                if (!questionRepondu){
                    val putResult = PutResult()
                    putResult.setBonneReponse(bonneReponse)
                    putResult.setId(this.userid)
                    putResult.execute()
                    this.questionRepondu = true
                    if(!bonneReponse){
                        residentsList.getResidentById(Integer.parseInt(this.userid)).addwrongAnswer();
                        if(this.adaptationEstActive){
                            desactiverReponse(radioGroup.checkedRadioButtonId)
                        }else{
                            startNewIntent(Integer.parseInt(userid),Quiz_id, Question_id+1, SelectedQuiz.Questions.questions.size,myDeviceManager.time)
                        }


                    }else{
                        startNewIntent(Integer.parseInt(userid),Quiz_id, Question_id+1, SelectedQuiz.Questions.questions.size,myDeviceManager.time)
                        residentsList.getResidentById(Integer.parseInt(this.userid)).addCorrectAnswer();
                    }
                }else{

                    if(!bonneReponse){
                        desactiverReponse(radioGroup.checkedRadioButtonId)
                    }else{
                        startNewIntent(Integer.parseInt(userid),Quiz_id, Question_id+1, SelectedQuiz.Questions.questions.size,myDeviceManager.time)
                    }
                }
            }else{
                afficherChoixReponse()
            }
        }

        }
    fun getAdaptation(userId: Int){
        val GetAdaptationService = GetAdaptationAlzheimer()
        GetAdaptationService.setId(userId)
        this.adaptationEstActive = GetAdaptationService.execute().get() as Boolean
        val GetAdaptationTexteService = GetAdaptationTexte()
        GetAdaptationTexteService.setId(userId)
        this.adaptationTexte = GetAdaptationTexteService.execute().get() as Boolean
        print("LA VALEUR ADAPTATION TEXTE : " + this.adaptationTexte)
        //this.adaptationEstActive = GetAdaptationService.activerAdaptationAlzheimer
       // print("LE ADAPTER ADAPTATION VALUE : " + this.adaptationEstActive + " LA VALEUR " + GetAdaptationService.activerAdaptationAlzheimer)
    }
    fun afficherChoixReponse(){
        Toast.makeText(this, "Une option doit être sélectionné!", Toast.LENGTH_LONG).show()
    }

    fun desactiverReponse(id:Int){
        findViewById<RadioButton>(id).visibility = View.INVISIBLE
    }

    fun verifierReponse(questionActuelle: Question): Boolean {
        return findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.equals(questionActuelle.reponse)
    }

    fun sendUserAnswer(){

        val sendAnswer=SendUserAnswer();
        sendAnswer.setUser_id(Integer.parseInt(this.userid))
        sendAnswer.setQuiz_id(this.Quiz_id)
        sendAnswer.setQuestion_id(this.Question_id)
        sendAnswer.setAnswer(Integer.parseInt(findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text as String))
        sendAnswer.execute()

    }


    fun traiterReponse(questionActuelle: Question){
        val putResult = PutResult()
        putResult.setBonneReponse(verifierReponse(questionActuelle))
    }

    fun startNewIntent(userId:Int,QuizId: Int, question_id:Int, size:Int,time: Float) {
        print("Le id de la questions : $question_id et le size : $size")
        if (question_id == size) {
            val menuIntent = Intent(this, MainActivity::class.java)
            //sending the data before returnng to menu
            sendTremblingData()
            startActivity(menuIntent)
        } else {
            val questionsIntent = Intent(this, QuestionView::class.java)
            var bundle =  Bundle();
            bundle.putSerializable("list",myDeviceManager.list)
            questionsIntent.putExtras(bundle)


            questionsIntent.putExtra("Quiz_id", QuizId);
            questionsIntent.putExtra("Question_id", question_id);
            questionsIntent.putExtra("UserId",userId)
            questionsIntent.putExtra("time",time)

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

    fun sendTremblingData(){

        for(tremblingData in myDeviceManager.list){
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


}


