package com.example.chattogether.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.example.chattogether.R;
import com.example.chattogether.data.old.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import android.widget.Button;

public class EditProfileActivity extends AppCompatActivity {

    ImageView avatar, back;
    EditText username, fullname, email
            , education, live_in, location;
    RadioGroup radioGroup;
    RadioButton single, in_relation;
    Button save;

    FirebaseUser firebaseUser;
    DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
//        }
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//
//        val attrib = window.attributes
//        attrib.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES

        avatar = findViewById(R.id.avatar);
        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        education = findViewById(R.id.education);
        live_in = findViewById(R.id.live_in);
        radioGroup =  findViewById(R.id.radio_group);
        single = findViewById(R.id.single);
        in_relation = findViewById(R.id.relationship);
        location = findViewById(R.id.location);
        save = findViewById(R.id.btn_save);
        back = findViewById(R.id.back);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference("MyUser/" + firebaseUser.getUid());
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if(user.getImageUrl().equals("default")){
                    avatar.setImageResource(R.drawable.send);
                } else
                    Glide.with(getApplicationContext()).load(user.getImageUrl()).into(avatar);

                username.setText(user.getUsername());
                fullname.setText(user.getFullname());
                email.setText(user.getEmail());
                education.setText(user.getEducation());
                live_in.setText(user.getLive_in());
                if(user.getRelationship()==null || user.getRelationship().equals("Single")){
                    single.setChecked(true);
                } else {
                    in_relation.setChecked(true);
                }
                location.setText(user.getLocation());


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        // save database if has modify
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        System.out.println("clicked");
                        if(!user.getUsername().equals(username.getText().toString()))
                            mRef.child("username").setValue(username.getText().toString());
                        if(!user.getEmail().equals(email.getText().toString()))
                            mRef.child("email").setValue(email.getText().toString());
                        if(user.getFullname() == null || !user.getFullname().equals(fullname.getText().toString()))
                            mRef.child("fullname").setValue(fullname.getText().toString());
                        if(user.getEducation() == null || !user.getEducation().equals(education.getText().toString()))
                            mRef.child("education").setValue(education.getText().toString());
                        if(user.getLive_in() == null || !user.getLive_in().equals(live_in.getText().toString()))
                            mRef.child("live_in").setValue(live_in.getText().toString());
                        if(user.getLocation() == null || !user.getLocation().equals(location.getText().toString()))
                            mRef.child("location").setValue(location.getText().toString());

                        int selectedID = radioGroup.getCheckedRadioButtonId();
                        if(selectedID == R.id.single && (user.getRelationship()==null || !user.getRelationship().equals(single.getText().toString())))
                            mRef.child("relationship").setValue(single.getText().toString());
                        else if(selectedID == R.id.relationship &&(user.getRelationship()==null || !user.getRelationship().equals(in_relation.getText().toString())))
                            mRef.child("relationship").setValue(in_relation.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                finish();
            }

        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}