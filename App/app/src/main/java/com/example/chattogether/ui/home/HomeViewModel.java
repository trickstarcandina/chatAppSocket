package com.example.chattogether.ui.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chattogether.data.api.ApiService;
import com.example.chattogether.data.auth.TempClient;
import com.example.chattogether.data.auth.UserT;
import com.example.chattogether.ui.service.connection.TCPClient;
import com.example.chattogether.data.model.Conversation;
import com.example.chattogether.data.model.User;
import com.example.chattogether.ui.auth.LoginActivity;
import com.server.chat.model.Message;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    MutableLiveData<List<UserT>> userList;
    MutableLiveData<User> user;
    MutableLiveData<List<Conversation>> conversation = new MutableLiveData<>();
    TCPClient tcpClient;
    static Thread listen;
    MutableLiveData<Message> messageReceived;

    public MutableLiveData<List<UserT>> getUserList() {
        if (userList == null) {
            userList = new MutableLiveData<>();
            List<UserT> userTS = new ArrayList<>();
            userTS.add(new UserT("username", "email@gmail.com"));
            userTS.add(new UserT("username", "email@gmail.com"));
            userTS.add(new UserT("username", "email@gmail.com"));
            userTS.add(new UserT("username", "email@gmail.com"));
            userTS.add(new UserT("username", "email@gmail.com"));

            userList.setValue(userTS);
        }
        return userList;
    }

    private void getUser() {
        tcpClient = TCPClient.getInstance();
    }

    public MutableLiveData<User> getUserInfo() {
        if (user == null) {
            user = new MutableLiveData<>();
            getUserInfoApi();
        }
        return user;
    }

    private void getUserInfoApi() {
        ApiService apiService = TempClient.getInstance();
        apiService.getUserInfo(LoginActivity.token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String s = response.toString();
                if (response.body() != null) {
                    conversation.setValue(response.body().conversations);
                    user.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
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
                    if (object instanceof Message && ((Message) object).getUserId() != TCPClient.getUser().getId()) {
                        Log.d("Received From " + ((Message) object).getUserId() , ((Message) object).getContent());
                        if(messageReceived != null) {
                            Log.d("posted","message");
                            messageReceived.postValue((Message) object);
                        }
                        int a = 3;
                    }
                }
            }
        });
        listen.start();
    }

}
