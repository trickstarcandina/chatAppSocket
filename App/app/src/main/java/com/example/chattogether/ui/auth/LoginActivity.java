package com.example.chattogether.ui.auth;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.chattogether.R;
import com.example.chattogether.ui.service.connection.TCPClient;
import com.example.chattogether.data.auth.LoginRequest;
import com.example.chattogether.databinding.ActivityLogin2BindingImpl;
import com.example.chattogether.ui.dialog.FragmentLoadingDialog;
import com.example.chattogether.ui.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {
    ActivityLogin2BindingImpl binding;

    AuthViewModel authViewModel;
    TCPClient client;
    public static String token = "token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentLoadingDialog fragmentLoadingDialog = new FragmentLoadingDialog();
                fragmentLoadingDialog.show(getSupportFragmentManager(),"TAG");

                String username = binding.username.getText().toString().trim();
                String password = binding.password.getText().toString().trim();
                LoginRequest l = new LoginRequest(username,password);

                authViewModel.getAccessToken(l).observe(LoginActivity.this, tokenResponse -> {
                    if (!tokenResponse.isEmpty()) {
                        fragmentLoadingDialog.dismiss();
                        token = tokenResponse;
                        // go to home page
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    }
                });

            }
        });
    }
}