package com.example.chattogether.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chattogether.R;
import com.example.chattogether.data.old.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity {

    ImageView avatar,btn_edit, btn_back, background;
    TextView fullname, username
            , email, location, live_in
            , education, relationship;
    Button message, photos;

    FirebaseUser firebaseUser;
    DatabaseReference mRef, mRef2;

    StorageReference storageReference;
    Uri imageUri, backgroundUri;
    private static final int IMAGE_REQUEST = 1, BACKGROUND_REQUEST = 2;
    String avatarUrl;

    int avatarId, backgroundId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
//        }

        avatar = findViewById(R.id.avatar);
        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        location = findViewById(R.id.location);
        btn_edit = findViewById(R.id.btn_edit);
        btn_back = findViewById(R.id.back);
        live_in = findViewById(R.id.live);
        education = findViewById(R.id.education);
        relationship = findViewById(R.id.relationship);
        background = findViewById(R.id.background);
        message = findViewById(R.id.message);
        photos = findViewById(R.id.photos);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference("MyUser").child(firebaseUser.getUid());
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user.getImageUrl().equals("default")) {
                    avatar.setImageResource(R.drawable.send);
                    avatarUrl = "default";
                }
                else {
                    avatarUrl = user.getImageUrl();
                    Glide.with(getApplicationContext()).load(user.getImageUrl()).into(avatar);
                }
                username.setText("@"+user.getUsername());
                email.setText(user.getEmail());

                if(user.getFullname() == null)
                    fullname.setVisibility(View.GONE);
                else{
                    fullname.setVisibility(View.VISIBLE);
                    fullname.setText(user.getFullname());
                }
                if(user.getLocation() == null){
                    location.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    location.setVisibility(View.GONE);
                }
                else{
                    location.setVisibility(View.VISIBLE);
                    location.setText(user.getLocation());
                }

                live_in.setText(user.getLive_in());
                education.setText(user.getEducation());
                relationship.setText(user.getRelationship());
                if(user.getBackground().equals("default"))
                    background.setImageResource(R.drawable.bg);
                else
                    Glide.with(getApplicationContext()).load(user.getBackground()).into(background);

                // get avatarId
                avatarId = user.getAvatarId();
                backgroundId = user.getBackgroundId();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        // Navigate to Edit page
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
            }
        });

        //Back to previous page
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // change avatar
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view, Gravity.BOTTOM);
                popupMenu.inflate(R.menu.avatar_menu);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.change){
                            storageReference = FirebaseStorage.getInstance().getReference("Profile");
                            openGalleryToChooseImage();
                        }
                        else if(menuItem.getItemId() == R.id.view_avatar){
                            Intent intent = new Intent(ProfileActivity.this, ViewImage.class);
                            intent.putExtra("src",avatarUrl);
                            startActivity(intent);
                        }
                        return false;
                    }
                });




            }
        });

        // change background
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storageReference = FirebaseStorage.getInstance().getReference("Background");
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery,BACKGROUND_REQUEST);
            }
        });

        // view all photos
        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProfileActivity.this, PhotosActivity.class);
                intent.putExtra("avatarId",avatarId);
                intent.putExtra("backgroundId", backgroundId);
                intent.putExtra("userId",firebaseUser.getUid());
                startActivity(intent);
            }
        });





    }

    private void openGalleryToChooseImage() {
        // open gallery
        Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        startActivityForResult(openGallery, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                imageUri = data.getData();
                pushImageToFirebase(imageUri);
            }
        }
        if(requestCode == BACKGROUND_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                backgroundUri = data.getData();
                StorageReference bgRef = FirebaseStorage.getInstance().getReference("Background/" + firebaseUser.getUid() + "/" + (backgroundId+1));
                bgRef.putFile(backgroundUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        backgroundId ++;
                        // set bgUrl of User
                        mRef2 = FirebaseDatabase.getInstance().getReference("MyUser").child(firebaseUser.getUid());
                        mRef2.child("backgroundId").setValue(backgroundId);
                        bgRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Uri> task) {
                                mRef2.child("background").setValue(task.getResult().toString());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {

                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

                    }
                });
            }
        }
    }

    private void pushImageToFirebase(Uri imageUri) {
        mRef = FirebaseDatabase.getInstance().getReference("MyUser").child(firebaseUser.getUid());

        StorageReference fileRef = storageReference.child(firebaseUser.getUid() + "/" + (avatarId+1));
        Log.d("Name: ",fileRef.getName());
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(ProfileActivity.this, "Upload Successfull!", Toast.LENGTH_SHORT).show();
                avatarId++;
                mRef.child("avatarId").setValue(avatarId);

                // set imageUrl of this user
                fileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Uri> task) {
                        mRef.child("imageUrl").setValue(task.getResult().toString());
                    }
                }). addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(ProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}