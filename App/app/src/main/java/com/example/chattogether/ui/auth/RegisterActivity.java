package com.example.chattogether.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chattogether.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText userEt, passEt, emailEt;
    Button register;
    ImageView back;

    FirebaseAuth auth;
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
//        }

//        FirebaseApp.initializeApp();
        // Initializing Widgets:
        userEt = findViewById(R.id.editTextUsername);
        passEt = findViewById(R.id.editTextPassword);
        emailEt = findViewById(R.id.editTextEmailAddress);
        register = findViewById(R.id.btnRegister);
        back = findViewById(R.id.back);
        // FirebaseAuth:
        auth = FirebaseAuth.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //btn Register action performed
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userEt.getText().toString();
                String pass = passEt.getText().toString();
                String email = emailEt.getText().toString();
                if(username.isEmpty() || pass.isEmpty() || email.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                }
                else{
                    RegisterNow(username,pass,email);
                }
            }
        });
    }
    public void RegisterNow(String username, String password, String email){
//        Toast.makeText(RegisterActivity.this, (username + "" + password + "" +email), Toast.LENGTH_SHORT).show();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userId = firebaseUser.getUid();
                    mRef = FirebaseDatabase.getInstance()
                            .getReference("MyUser").child(userId);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userId);
                    hashMap.put("username", username);
                    hashMap.put("email", email);
                    hashMap.put("password", password);
                    hashMap.put("imageUrl", "default");
                    hashMap.put("background", "default");

                    HashMap<String, Integer> hashMap1 = new HashMap<String, Integer>();
                    hashMap1.put("avatarId",new Integer(0));
                    hashMap1.put("backgroundId",new Integer(0));
                    mRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull  Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}