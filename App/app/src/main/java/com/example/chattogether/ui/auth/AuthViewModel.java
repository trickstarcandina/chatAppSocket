package com.example.chattogether.ui.auth;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chattogether.data.api.ApiService;
import com.example.chattogether.data.auth.LoginRequest;
import com.example.chattogether.data.auth.TempClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {

    private MutableLiveData<String> accessToken;

    public void login(LoginRequest loginRequest) {
        ApiService apiService = TempClient.getInstance();
        apiService.loginRequest(loginRequest).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Log.d("response", response.toString());
                if (response.code() == 200) {
                    accessToken.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("failed",t.toString());
            }
        });
    }

    public MutableLiveData<String> getAccessToken(LoginRequest loginRequest) {
        if (accessToken == null) {
            accessToken = new MutableLiveData<>();
            login(loginRequest);
        }
        return accessToken;
    }


}
