package com.example.chattogether.ui.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.example.chattogether.R;
import com.example.chattogether.databinding.ActivityEditProfileBinding;
import com.example.chattogether.databinding.ActivityProfileBinding;
import com.example.chattogether.ui.dialog.FragmentLoadingDialog;
import com.example.chattogether.ui.home.HomeActivity;
import com.example.chattogether.ui.service.connection.TCPClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.server.chat.model.Message;
import com.server.chat.model.UpdateUserRequest;

import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class EditProfileActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST = 1;
    ActivityEditProfileBinding binding;
    String avatarUrl = "";
    byte[] bytesArray;
    String mimeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        avatarUrl = getIntent().getStringExtra("avatarUrl");

        initView();
        initListener();
    }

    private void initView() {

        HomeActivity.homeViewModel.getUserInfo().observe(this, userResponse -> {
            binding.etUsername.setText(userResponse.getUsername());
            binding.etAddress.setText(userResponse.getAddress());
            binding.etFullName.setText(userResponse.getNickName());
            if(userResponse.getAvatarUrl() != null){
                Glide.with(this)
                        .load("http://34.122.94.78:9000/chat"+userResponse.getAvatarUrl())
                        .centerInside()
                        .into(binding.ivAvatar);
            }
        });
    }

    private void initListener() {
        // change avatar
        binding.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view, Gravity.BOTTOM);
                popupMenu.inflate(R.menu.avatar_menu);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.change) {
                            Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(openGallery, IMAGE_REQUEST);
                        } else if (menuItem.getItemId() == R.id.view_avatar) {
                            Intent intent = new Intent(EditProfileActivity.this, ViewImage.class);
                            intent.putExtra("src", avatarUrl);
                            startActivity(intent);
                        }
                        return false;
                    }
                });
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUpdateUserRequest();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                try {
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        bytesArray = stream.toByteArray();
                        Log.d("bytesArray", Arrays.toString(bytesArray));

                        ContentResolver cr = getContentResolver();
                        mimeType = cr.getType(uri);

                        // preview avatar
                        binding.ivAvatar.setImageBitmap(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    void sendUpdateUserRequest(){

        FragmentLoadingDialog fragmentLoadingDialog = new FragmentLoadingDialog();
        fragmentLoadingDialog.show(getSupportFragmentManager(), "");
        int userId = TCPClient.getUser().getId();
        String address = binding.etAddress.getText().toString() ;
        String username = binding.etUsername.getText().toString();
        String nickName = binding.etFullName.getText().toString();
        String password = binding.etPassword.getText().toString();
        String contentType = "jpg";

        UpdateUserRequest updateUserRequest = new UpdateUserRequest(userId, address, username, nickName, password, bytesArray, contentType);
        new Thread(new Runnable() {
            @Override
            public void run() {
                TCPClient.sender.sendObject(updateUserRequest);
                Log.d("update successful", "true");
                fragmentLoadingDialog.dismiss();
//                                runOnUiThread(new Runnable() {
//                                    @SuppressLint("NotifyDataSetChanged")
//                                    @Override
//                                    public void run() {
//                                        recyclerView.smoothScrollToPosition(adapter.getItemCount());
//                                        adapter.notifyDataSetChanged();
//                                    }
//                                });
                HomeActivity.homeViewModel.updateUserInfo();
                bytesArray = null;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(EditProfileActivity.this, "Update successful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();

    }


}