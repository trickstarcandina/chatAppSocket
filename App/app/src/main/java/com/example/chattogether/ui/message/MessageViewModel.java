package com.example.chattogether.ui.message;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chattogether.data.api.ApiService;
import com.example.chattogether.data.auth.TempClient;
import com.example.chattogether.ui.service.connection.TCPClient;
import com.example.chattogether.data.model.MessageResponseItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageViewModel extends ViewModel {
    MutableLiveData<List<MessageResponseItem>> messageList;
    TCPClient tcpClient;


    public MutableLiveData<List<MessageResponseItem>> getMessageListByConversationId(int id) {
        if (messageList == null) {
            messageList = new MutableLiveData<>();
            getMessageListApi(id);
        }
        return messageList;
    }

    private void getMessageListApi(int id) {
        ApiService apiService = TempClient.getInstance();
        apiService.getMessageByConversationId(id).enqueue(new Callback<List<MessageResponseItem>>() {
            @Override
            public void onResponse(Call<List<MessageResponseItem>> call, Response<List<MessageResponseItem>> response) {
                Log.d("response", response.body().toString());
                if (response.code() == 200) {
                    messageList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<MessageResponseItem>> call, Throwable t) {

            }
        });
    }



    public void closeListenerThread() {
        Log.d("Listener Thread", "closed");
    }
}
