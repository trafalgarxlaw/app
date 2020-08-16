package com.example.myapplication.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.collections.PersonalQuizCollection;
import com.example.myapplication.collections.QuizCollection;
import com.example.myapplication.collections.ResidentsCollection;
import com.example.myapplication.models.Quiz;
import com.example.myapplication.models.Resident;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.MyViewHolder> {

    private boolean UserList=false;//if we are displaying a UserList
    private boolean QuizList=false; //if we are displaying a QuizList
    private boolean personalQuiz=false; //if we are displaying PersonalQuiz
    private boolean QuestionList=false; //if we are displaying the current Quiz Questions



    private QuizCollection Quizs;
    private PersonalQuizCollection personalQuizCollection;
    private ResidentsCollection residents;
    private Resident currentResident;
    private Quiz currentQuiz;
    private Context context;
    private int availableQuiz;
//    private int residentsNum;
    private OnItemClickListener mOnItemClickListener;

    //constructor 1
    public recyclerViewAdapter(Context ct,QuizCollection collection ,OnItemClickListener listner,int mode){
        this.context=ct;
        this.Quizs=collection;
        this.availableQuiz=collection.QuizArray.size();
        this.mOnItemClickListener=listner;
        if (mode==1){
            QuizList=true;
        }else if (mode==2){
            UserList=true;
        }
        else if (mode==3){
            QuestionList=true;
        }
    }
    // constructor 2
    public recyclerViewAdapter(Context ct,Quiz quiz ,Resident currentResident,OnItemClickListener listner,int mode){
        this.context=ct;
        this.currentQuiz=quiz;
        this.currentResident=currentResident;
        this.mOnItemClickListener=listner;
        if (mode==1){
            QuizList=true;
        }else if (mode==2){
            UserList=true;
        }
        else if (mode==3){
            QuestionList=true;
        }
    }
    //constructor 3

    public recyclerViewAdapter(Context ct,ResidentsCollection collection, OnItemClickListener listner,int mode){
        this.context=ct;
        this.residents=collection;
        this.mOnItemClickListener=listner;
        if (mode==1){
            QuizList=true;
        }else if (mode==2){
            UserList=true;
        }
    }

    //constructor 4
    public recyclerViewAdapter(Context ct, PersonalQuizCollection collection , OnItemClickListener listner, int mode){
        this.context=ct;
        this.personalQuizCollection=collection;
        this.availableQuiz=collection.QuizArray.size();
        this.mOnItemClickListener=listner;
        this.personalQuiz=true;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view,mOnItemClickListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(QuizList){
            // Will loop through every Quiz in QuizArray through position to dynamically generate a list
            holder.Title.setText(Quizs.QuizArray.get(position).Title);
            holder.Description.setText(Quizs.QuizArray.get(position).Description);
        } else if (personalQuiz){
            holder.Title.setText(personalQuizCollection.QuizArray.get(position).Title);
            holder.Description.setText(personalQuizCollection.QuizArray.get(position).Description);
        }
        else if (UserList) {
            holder.Title.setText(residents.getResidentById(position).getFirstname()+" "+ residents.getResidentById(position).getLastname());
            holder.Description.setText(residents.getResidentById(position).getAge() +" ans");
        }else if(QuestionList){
            //displaying the questions sentences for the selected Quiz
            holder.Title.setText("Question : "+currentQuiz.Questions.questions.get(position).questionSentence);

            // Displaying the answer of the current resident for this question
            if (!currentResident.UserAnswersIsEmpty()){
                if (currentResident.getAnswersByQuizId(currentQuiz.id).getAnswerByQuestionId(position)!=null){
                    holder.Description.setText("Reponse du resident: "+currentResident.getAnswersByQuizId(currentQuiz.id).getAnswerByQuestionId(position).getAnswer());
                }else {
                    holder.Description.setText("Pas de reponse...");
                }
            }else {
                holder.Description.setText("Pas de reponse...");
            }

        }


    }

    @Override
    public int getItemCount() {
        int count=0;
        if (QuizList){
            count=availableQuiz;
        }else if (personalQuiz){
            count=availableQuiz;
        }
        else if(UserList){
            count=residents.getSize();
        }else if(QuestionList){
            count=currentQuiz.Questions.questions.size();
        }

        return count;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title,Description;
        ImageView QuizThumbnail;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(@NonNull View itemView,OnItemClickListener _onItemClickListener) {
            super(itemView);
            Title = itemView.findViewById(R.id.CardTitile);
            Description = itemView.findViewById(R.id.CardDescription);
            QuizThumbnail = itemView.findViewById(R.id.CardImage);
            this.onItemClickListener=_onItemClickListener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        int position = getAdapterPosition();
                        onItemClickListener.onItemClick(position);

                }
            });
        }

    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
