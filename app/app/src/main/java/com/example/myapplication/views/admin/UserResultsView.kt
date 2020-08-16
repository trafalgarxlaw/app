package com.example.myapplication.views.admin

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.BLACK
import android.graphics.Color.red
import android.os.Bundle
import android.util.Half.toFloat
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.asynctask.*
import com.example.myapplication.models.Resultat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_user_results.*

class UserResultsView: AppCompatActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_results)



        //Here i get the User Id from the recucle View
        var user_id = intent.getIntExtra("UserId",-1);

        val residentsList = UserNetwork().execute().get();
        val FolderTitle= findViewById<TextView>(R.id.FolderTitle);

        FolderTitle.setText("Dossier de Suivis de "+residentsList.getResidentById(user_id).firstname )
        val userResults= residentsList.getResidentById(user_id).result;
        val userGoodAnswers= residentsList.getResidentById(user_id).results.getcorrectAnswers();
        val userBadAnswers= residentsList.getResidentById(user_id).results.wrongAnswers;
        val totalQuestionsAnswered= residentsList.getResidentById(user_id).results.questionAnswered;

        //text views
        val userGoodAnswersview= findViewById<TextView>(R.id.userGoodAnswersText);
        val userBadAnswersview= findViewById<TextView>(R.id.userBadAnswersText);
        val totalQuestionsAnsweredview= findViewById<TextView>(R.id.totalQuestionsAnsweredText);
        val userResultsview= findViewById<TextView>(R.id.userResultstext);
        val activerQuestionAdaptation = findViewById<Button>(R.id.activerQuestionAdaptation);


        userGoodAnswersview.text = "Bonne reponses :" +userGoodAnswers ;
        userBadAnswersview.text="Mauvaise reponses :" +userBadAnswers;
        totalQuestionsAnsweredview.text="Questions repondus :"+totalQuestionsAnswered;
        userResultsview.text="Taux de bonne Reponses :"+userResults;


        val ResultsDetailsButton = findViewById<Button>(R.id.ResultsDetailsButton)
        ResultsDetailsButton.setOnClickListener{
            val myintent = Intent(this, ResultsDetailsView::class.java)
            myintent.putExtra("UserId",user_id );
            startActivity(Intent(myintent));
        }


        //fetching trembing data of the user
        val currentUser= residentsList.getResidentById(user_id);
        currentUser.fetchTremblingData();
        val tremblingData=currentUser.getTremblingData();

        //creating the graph

        val lineChart=findViewById<LineChart>(R.id.TremblingAVGgraph)

        val entries = ArrayList<Entry>()


        for (i in tremblingData.indices){
            entries.add(Entry(tremblingData.get(i).time,tremblingData.get(i).average))
        }
//        entries.add(Entry(1f, 10f))
//        entries.add(Entry(2f, 2f))
//        entries.add(Entry(3f, 7f))
//        entries.add(Entry(4f, 20f))
//        entries.add(Entry(5f, 16f))

        val vl = LineDataSet(entries, "Trembling Avreage")

        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.colorPrimaryDark
        vl.fillAlpha = R.color.green

        lineChart.xAxis.labelRotationAngle = 0f

        lineChart.data = LineData(vl)

        lineChart.axisRight.isEnabled = false
        if (!entries.isEmpty()){
            lineChart.xAxis.axisMaximum = entries.last().x+0.1f
        }else{
            lineChart.xAxis.axisMaximum = 0.1f
        }

        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)

        lineChart.description.text = "Temps"
        lineChart.setNoDataText("Aucune donnee!")

        lineChart.animateX(1800, Easing.EaseInExpo)

        //Pie Chart for game score result

        var network= getMiniGameScore(user_id.toString())
        var score:Float = network.execute().get();

        val pieChart = findViewById<PieChart>(R.id.GameScoreChart)
        var Piescore = ArrayList<PieEntry>()
        Piescore.add( PieEntry(score,"match reussis"))
        Piescore.add( PieEntry(100-score,"match échoué"))

        var dataSet = PieDataSet(Piescore,"")
        dataSet.setColors(R.color.colorPrimaryDark,Color.rgb(200,0,0))
        dataSet.valueTextSize=20f

        var data = PieData(dataSet)

        pieChart.data = data
        pieChart.description.isEnabled=false
        pieChart.centerText="le score du resident (%)"
        pieChart.animate()

        val getResultat = GetPersonalScore()
        getResultat.id = user_id.toString();
        val resultat = getResultat.execute().get() as Resultat
        if(resultat.questionRepondu>0){
            val chartScoreLongTerme = findViewById<PieChart>(R.id.scoreLongTerme)
            Piescore = ArrayList<PieEntry>()
            //Piescore.add(PieEntry(resultat.bonneReponse.toFloat(), "Bonnes réponses"))
            //Piescore.add(PieEntry(resultat.mauvaiseReponse.toFloat(), "Mauvaises réponses"))
            Piescore.add(PieEntry(5.toFloat(), "Bonnes réponses"))
            Piescore.add(PieEntry(15.toFloat(), "Mauvaises réponses"))
            dataSet = PieDataSet(Piescore,"")
            dataSet.setColors(Color.RED, Color.GREEN, Color.BLUE)
            dataSet.valueTextSize=15f
            dataSet.valueTextColor= Color.BLACK
            chartScoreLongTerme.legend.textColor = Color.WHITE
            chartScoreLongTerme.legend.textSize=10f

            data = PieData(dataSet)
            chartScoreLongTerme.setEntryLabelColor(Color.BLACK);

            chartScoreLongTerme.data = data
            chartScoreLongTerme.description.text="Résultats Quiz Personnalise"
            chartScoreLongTerme.description.textColor = Color.WHITE
            chartScoreLongTerme.description.textSize=15F
            chartScoreLongTerme.centerText="le score du resident: "+ resultat.resultat.toString()
            chartScoreLongTerme.animate()
        }else{
            val chartScoreLongTerme = findViewById<PieChart>(R.id.scoreLongTerme)
            chartScoreLongTerme.visibility = View.GONE
        }








        val adaptationAlzheimer = GetAdaptationAlzheimer()
        if (adaptationAlzheimer.execute().get() == true){
            val activerAdptationButton = findViewById<Button>(R.id.activerQuestionAdaptation)
            activerAdptationButton.text = "Desactiver l'adaptation pour l'alzheimer"
            activerAdptationButton.textSize = 10F
        }
        val adaptationTexte = GetAdaptationTexte()
        if (adaptationTexte.execute().get()== true){
            val activerAdptationButton = findViewById<Button>(R.id.activerTailleTexte)
            activerAdptationButton.text = "Desactiver l'augmentation du Texte"
            activerAdptationButton.textSize = 10F
        }
        activerQuestionAdaptation.setOnClickListener(){
            val putAdaptation = PutAdaptationAlzheimer()
            putAdaptation.setId(user_id);
            putAdaptation.execute();
            startActivity(Intent(this, UserListView::class.java))
        }

        activerTailleTexte.setOnClickListener(){
            val putAdaptation = PutAdaptationTexte()
            putAdaptation.setId(user_id);
            putAdaptation.execute();
            startActivity(Intent(this, UserListView::class.java))
        }
    }
}