package com.example.chattogether.data.api;

import com.example.chattogether.data.auth.LoginRequest;
import com.example.chattogether.data.model.ConversationInfo;
import com.example.chattogether.data.model.MessageResponseItem;
import com.example.chattogether.data.model.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("authenticate")
    Call<String> loginRequest(@Body LoginRequest loginRequest);

    @GET("api/user")
    Call<UserResponse> getUserInfo(@Header("Authorization") String token);

    @GET("/api/message/conversation/")
    Call<List<MessageResponseItem>> getMessageByConversationId(@Query("conversationId") int conversationId);

    @GET("api/user/conversation")
    Call<List<ConversationInfo>> getConversationInfo(@Query("conversationId") String conversationId);


}
