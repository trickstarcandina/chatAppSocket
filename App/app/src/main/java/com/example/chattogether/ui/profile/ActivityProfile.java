package com.example.chattogether.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.chattogether.R;
import com.example.chattogether.databinding.ActivityProfileBinding;
import com.example.chattogether.ui.home.HomeActivity;
import com.example.chattogether.ui.home.HomeViewModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ActivityProfile extends AppCompatActivity {

    private static final int IMAGE_REQUEST = 1;
    StorageReference storageReference;
    Uri imageUri;
    String avatarUrl;
    ActivityProfileBinding binding;
    HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        homeViewModel = HomeActivity.homeViewModel;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        initListener();
    }

    private void initView() {
        homeViewModel.getUserInfo().observe(this, user -> {
            if (user.getAvatarUrl() != null) {
                avatarUrl = user.getAvatarUrl();
                if(user.getAvatarUrl() != null){
                    Glide.with(this)
                            .load("http://34.122.94.78:9000/chat"+user.getAvatarUrl())
                            .centerInside()
                            .into(binding.ivAvatar);
                }
                avatarUrl = "http://34.122.94.78:9000/chat" + user.getAvatarUrl();
            }
            else {
                Glide.with(getApplicationContext())
                        .load("https://j03qukjhr2obj.vcdn.cloud/image-upload/StockDesignOnTV/Logo giải đấu/Premier League.png")
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(binding.ivAvatar);
                avatarUrl = "https://j03qukjhr2obj.vcdn.cloud/image-upload/StockDesignOnTV/Logo giải đấu/Premier League.png";
            }
            binding.tvUsername.setText(user.getUsername());
            binding.tvFullName.setText(user.getNickName());
            binding.tvAddress.setText(user.getAddress());
        });
    }

    private void initListener() {

        // Navigate to Edit page
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityProfile.this, EditProfileActivity.class);
                intent.putExtra("avatarUrl",avatarUrl);
                startActivity(intent);
            }
        });

        //Back to previous page
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

                        } else if (menuItem.getItemId() == R.id.view_avatar) {
                            Intent intent = new Intent(ActivityProfile.this, ViewImage.class);
                            intent.putExtra("src", avatarUrl);
                            startActivity(intent);
                        }
                        return false;
                    }
                });
            }
        });
    }



    private void openGalleryToChooseImage() {
        // open gallery

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                imageUri = data.getData();
            }
        }
    }


}