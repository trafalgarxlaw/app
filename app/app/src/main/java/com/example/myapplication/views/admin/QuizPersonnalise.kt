package com.example.myapplication.views.admin

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.asynctask.GetPersonalQuiz
import com.example.myapplication.asynctask.creerQuizPersonelle
import com.example.myapplication.asynctask.uploadAudio
import com.example.myapplication.main.MainActivity
import com.example.myapplication.models.Question
import com.example.myapplication.models.QuizPersonal
import kotlinx.android.synthetic.main.activity_quiz_personnalise.*
import com.example.myapplication.retrofitInterface.IImageUpload
import com.example.myapplication.retrofitInterface.Response
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileInputStream
import java.io.IOException


class QuizPersonnalise : AppCompatActivity() {
    private lateinit var mediacontroller: MediaController
    private lateinit var mediaButton: Button
    private lateinit var media : Uri
    private lateinit var submit: Button
    private lateinit var question: EditText
    private lateinit var reponse1: EditText
    private lateinit var reponse2: EditText
    private lateinit var reponse3: EditText
    private lateinit var bonneReponse : EditText
    private lateinit var addQuestion: Button
    private lateinit var quizPersonel: QuizPersonal
    private var fichierChoisi = false
    private lateinit var uploadaudio:uploadAudio
    private lateinit var uploadQuestion: creerQuizPersonelle
    private lateinit var description: EditText
    private lateinit var nom : EditText
    private var id =-1
    private var fichierAudio = false

    private var QuizId=0;
    private var QuestionID=0;
    private var resident_name=" "


    //for uploading img
    private lateinit var imageView: ImageView
    private lateinit var imageButton: Button
    private lateinit var sendButton: Button
    private var imageData: ByteArray? = null
    private val postimageURL: String = "http://10.0.2.2:8080/api/residents/uploadImg/"// remember to use your own api

    private var mImageUrl = ""


    companion object {
        private const val IMAGE_PICK_CODE = 999
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_personnalise)
        init()
        mediaButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1);
        }

        imageView = findViewById(R.id.uploadImgView)

        imageButton = findViewById(R.id.AddImage_Button)
        imageButton.setOnClickListener {
            sendButton = findViewById(R.id.uploadimg)
            sendButton.setOnClickListener {
                uploadImage()
            }
            launchGallery()
        }
        submit.setOnClickListener {
            if(verifierInfoQuiz()){
                if(this.fichierAudio){
                    this.uploadaudio.execute()
                }
                var question = Question(this.quizPersonel.questions.size.toString(), this.question.text.toString() , this.reponse1.text.toString(), this.reponse2.text.toString(), this.reponse3.text.toString(), this.bonneReponse.text.toString(), this.uploadaudio.fileName);
                this.quizPersonel.ajouterQuestion(question)
                this.quizPersonel.id=this.QuizId;
                var uploadQuiz = creerQuizPersonelle()
                uploadQuiz.id = this.QuizId
                uploadQuiz.quizPersonal = this.quizPersonel
                uploadQuiz.execute()
                startNewActivity()
            }else{
                Toast.makeText(this, "Il faut absolument remplir tous les champs et que la bonne réponse soit dans les choix et avoir choisi un fichier!", Toast.LENGTH_SHORT).show()
            }

        }
        addQuestion.setOnClickListener {
            if(verifierInfoQuiz()){
                if(this.fichierAudio){
                    this.uploadaudio.execute()
                }
                var question = Question(this.quizPersonel.questions.size.toString(), this.question.text.toString() , this.reponse1.text.toString(), this.reponse2.text.toString(), this.reponse3.text.toString(), this.bonneReponse.text.toString(), this.uploadaudio.fileName);
                this.quizPersonel.ajouterQuestion(question)
                nextQuestionActivity()
            }else{
                Toast.makeText(this, "Il faut absolument remplir tous les champs et que la bonne réponse soit dans les choix et avoir choisi un fichier!", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun init(){
          uploadaudio = uploadAudio()
          uploadQuestion = creerQuizPersonelle()

        this.mediaButton = findViewById(R.id.addAudio);
        this.mediacontroller = MediaController(this)
        this.submit = findViewById(R.id.submit)
        this.reponse1 = findViewById<EditText>(R.id.reponse1)
        this.reponse2 = findViewById<EditText>(R.id.reponse2)
        this.reponse3 = findViewById<EditText>(R.id.reponse3)
        this.question = findViewById(R.id.question)
        this.bonneReponse = findViewById(R.id.bonneReponse)
        this.media = Uri.EMPTY
        this.id = intent.getIntExtra("UserId",-1);
        this.addQuestion = findViewById(R.id.addQuestion)
        this.description = findViewById(R.id.descriptionQuiz)
        this.nom = findViewById(R.id.nomQuiz)

        // getting the current question Id we are creating in the Quiz
        this.quizPersonel = intent.getSerializableExtra("quiz") as QuizPersonal
        if(this.quizPersonel.Description.isNotEmpty() && this.quizPersonel.Title.isNotEmpty()){
            this.description.visibility= View.GONE
            this.nom.visibility = View.GONE
        }
        this.QuestionID=intent.getIntExtra("QuestionId",0);
        this.resident_name=intent.getStringExtra("resident_name");

        val network = GetPersonalQuiz();
        network.setResidentName(resident_name);
        val personalQuiz = network.execute().get();
        QuizId= personalQuiz.quizArray.size
        System.out.println("the current quiz we are adding have the id : "+QuizId)
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == RESULT_OK){
            println("DANS LE Resultcode == ok")
            if (data != null) {
                this.media = data.data!!
                this.uploadaudio.setId(this.id.toString())
                this.uploadaudio.fileName = contentResolver.getFileName(data.data!!)
                this.uploadaudio.file = File("/sdcard/Download/"+ contentResolver.getFileName(data.data!!))
                this.uploadaudio.fileInputStream = FileInputStream(this.uploadaudio.file)
                this.fichierChoisi = true
                this.fichierAudio = true
            }else{
                Toast.makeText(this, "Il faut absolument choisir un fichier audio!", Toast.LENGTH_SHORT).show()

            }
        }

        // pour l'upload d'image
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val uri = data?.data
            if (uri != null) {
                imageView.setImageURI(uri)
                createImageData(uri)
                this.fichierAudio = false
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun verifyForm() : Boolean {
        return (this.reponse1.toString().trim().length > 0) && (this.reponse2.toString().trim().length > 0) &&
    (this.reponse3.toString().trim().length>0) && (this.question.toString().trim().length>0) && this.fichierChoisi &&
    (this.reponse1.text.toString().equals(this.bonneReponse.text.toString()) || this.reponse2.text.toString().equals(this.bonneReponse.text.toString()) || this.reponse3.text.toString().equals(this.bonneReponse.text.toString()))
}

    fun ContentResolver.getFileName(fileUri: Uri):String{
        var name = ""
        val returnCursor = this.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }
    fun startNewActivity() {
        val UserResultsIntent = Intent(this, MainActivity::class.java)
        startActivity(UserResultsIntent);
    }
    fun nextQuestionActivity(){
        val quizPersonnaliseIntent = Intent(this, QuizPersonnalise::class.java)
        quizPersonnaliseIntent.putExtra("quiz", this.quizPersonel)
        quizPersonnaliseIntent.putExtra("UserId", this.id)
        quizPersonnaliseIntent.putExtra("QuestionId", this.QuestionID+1)
        quizPersonnaliseIntent.putExtra("resident_name",this.resident_name);
        startActivity(quizPersonnaliseIntent)
    }


    private fun launchGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }


    private fun uploadImage() {
        sendButton.setBackgroundColor(Color.parseColor("#35DF04"));

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitInterface: IImageUpload = retrofit.create(
            IImageUpload::class.java)

        val requestFile: RequestBody =
            RequestBody.create(MediaType.parse("image/jpeg"), imageData)

        val body =
            MultipartBody.Part.createFormData("file", "image.jpg", requestFile)

        val resident_name =
            RequestBody.create(MediaType.parse("text/plain"),this.resident_name);

        val Quiz_id =
            RequestBody.create(MediaType.parse("text/plain"),this.QuizId.toString())

        val Question_id =
            RequestBody.create(MediaType.parse("text/plain"),this.QuestionID.toString())

        val call: Call<Response> = retrofitInterface.uploadImage(body,resident_name,Quiz_id,Question_id)

        System.out.println("enque..")
        call.enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.d("TAG", "onFailure: "+t.localizedMessage);
            }

            override fun onResponse(
                call: Call<Response>,
                response: retrofit2.Response<Response>
            ) {

                if (response.isSuccessful) {
                    val responseBody =
                        response.body()
                    mImageUrl = postimageURL + responseBody!!.path
                } else {
                    val errorBody: ResponseBody? = response.errorBody()
                    System.out.println(response.message())
                    System.out.println(response.body())
                    System.out.println(response.code())
                    System.out.println(response.toString())
                }
            }

        })
    }

    @Throws(IOException::class)
    private fun createImageData(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.buffered()?.use {
            imageData = it.readBytes()
        }
    }

    fun verifierInfoQuiz(): Boolean {
        if(this.quizPersonel.Title.isEmpty() || this.quizPersonel.Description.isEmpty()){
            if(this.description.text.toString().trim().length >0 && this.nom.text.toString().trim().length>0){
                this.quizPersonel.Title = this.nom.text.toString()
                this.quizPersonel.Description = this.description.text.toString()
                return true;
            }else{
                return false;
            }
        }
        //Le quiz a deja une description et un nom.
        return true
    }
}