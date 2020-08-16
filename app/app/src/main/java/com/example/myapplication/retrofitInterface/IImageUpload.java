package com.example.myapplication.retrofitInterface;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface IImageUpload {

    @Multipart
    @POST("/api/residents/uploadImg")
    Call<Response> uploadImage(@Part MultipartBody.Part image,
                               @Part("Resident_name") RequestBody r_name,
                               @Part("PersonalQuiz_id") RequestBody PersonalQuiz_id,
                               @Part("Question_id") RequestBody Q_id);
}



