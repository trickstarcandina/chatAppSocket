package com.example.chattogether.ui.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chattogether.data.api.ApiService;
import com.example.chattogether.data.auth.TempClient;
import com.example.chattogether.data.model.Conversation;
import com.example.chattogether.data.model.ConversationInfo;
import com.example.chattogether.data.model.UserResponse;
import com.example.chattogether.ui.auth.LoginActivity;
import com.example.chattogether.ui.service.connection.TCPClient;
import com.server.chat.model.Message;
import com.server.chat.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    static Thread listen;
    MutableLiveData<List<User>> userList;
    MutableLiveData<UserResponse> user;
    MutableLiveData<List<Conversation>> conversation = new MutableLiveData<>();
    MutableLiveData<List<ConversationInfo>> conversationInfoList = new MutableLiveData<>();
    TCPClient tcpClient;
    MutableLiveData<Message> messageReceived;

    public MutableLiveData<List<User>> getUserList() {
        if (userList == null) {
            userList = new MutableLiveData<>();
        }
        return userList;
    }

    public MutableLiveData<List<ConversationInfo>> getConversationInfoList(String id) {
        getConversationInfo(id);
        return conversationInfoList;
    }

    private void getUser() {
        tcpClient = TCPClient.getInstance();
    }

    public MutableLiveData<UserResponse> getUserInfo() {
        if (user == null) {
            user = new MutableLiveData<>();
            getUserInfoApi();
        }
        return user;
    }

    public void updateUserInfo() {
        ApiService apiService = TempClient.getInstance();
        apiService.getUserInfo(LoginActivity.token).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.body() != null) {
                    user.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                String s = t.toString();
            }
        });
    }

    private void getConversationInfo(String id) {
        ApiService apiService = TempClient.getInstance();
        apiService.getConversationInfo(id).enqueue(new Callback<List<ConversationInfo>>() {
            @Override
            public void onResponse(Call<List<ConversationInfo>> call, Response<List<ConversationInfo>> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    conversationInfoList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ConversationInfo>> call, Throwable t) {
                Log.d("failed", t.toString());
            }
        });
    }

    public void getUserInfoApi() {
        ApiService apiService = TempClient.getInstance();
        apiService.getUserInfo(LoginActivity.token).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.body() != null) {
                    conversation.setValue(response.body().getConversations());
                    user.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                String s = t.toString();
            }
        });
    }

    public MutableLiveData<Message> getMessage() {
        if (messageReceived == null) {
            messageReceived = new MutableLiveData<>();
//            if (listen == null) {
//                listenMessage();
            Log.d("init get message", "true");
//            }
        }
        return messageReceived;
    }


    public void listenMessage() {
        tcpClient = TCPClient.getInstance();
        listen = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Object object = TCPClient.receiver.receiveObject();
                    if (object instanceof Message && (((Message) object).getUserId() != TCPClient.getUser().getId() || ((Message) object).getUrl() != null)) {
                        Log.d("Received From " + ((Message) object).getUserId(), ((Message) object).getContent() == null ? ((Message) object).getUrl() : ((Message) object).getContent());
                        if (messageReceived != null) {
                            Log.d("posted", "message");
                            messageReceived.postValue((Message) object);
                        }
                        int a = 3;
                    } else if (object instanceof List) {

                        if (!((List<?>) object).isEmpty()) {
                            Object obj = ((List<?>) object).get(0);
                            if (obj instanceof User) {
                                if (userList == null) userList = new MutableLiveData<>();
                                userList.postValue((List<User>) object);
                            }

                        }
                    } else if (object instanceof com.server.chat.model.Conversation) {
                        int a = 3;
                    } else {
                        int a = 8;
                    }
                }
            }
        });
        listen.start();
    }

    public void updateUserConversation() {
    }
}
