package com.example.chattogether.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.chattogether.R;
import com.example.chattogether.databinding.ActivityHomeBinding;
import com.example.chattogether.ui.dialog.FragmentCreateGroupDialog;
import com.example.chattogether.ui.profile.ActivityProfile;
import com.example.chattogether.ui.service.OnConnected;
import com.example.chattogether.ui.service.OnCreateGroup;
import com.example.chattogether.ui.service.connection.TCPClient;
import com.google.android.material.tabs.TabLayout;
import com.server.chat.model.GetAllUserRequest;
import com.server.chat.model.LoginRequest;

import java.util.Date;
import java.util.Objects;

import utils.Receiver;
import utils.Sender;

public class HomeActivity extends AppCompatActivity implements OnConnected, OnCreateGroup {
    public static HomeViewModel homeViewModel;
    public static Sender sender;
    public static Receiver receiver;
    ImageView avatar;
    TCPClient client;
    ActivityHomeBinding binding;
    FragmentChatList fragmentChatList = FragmentChatList.newInstance();
    FragmentUserList fragmentUserList = FragmentUserList.newInstance();
    FragmentCreateGroupDialog fragmentCreateGroupDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        binding.ivAvatar.requestFocus();
        initView();
        initListener();
    }

    private void initListener() {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getUserInfo().observe(this, user -> {
            fragmentChatList.getConversation(user.getConversations());
            TCPClient.userResponse = user;
            if(user.getAvatarUrl() != null){
                Glide.with(this)
                        .load("http://34.122.94.78:9000/chat"+user.getAvatarUrl())
                        .centerInside()
                        .into(binding.ivAvatar);
            }
            else
                Glide.with(getApplicationContext())
                    .load("https://j03qukjhr2obj.vcdn.cloud/image-upload/StockDesignOnTV/Logo giải đấu/Premier League.png")
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(binding.ivAvatar);

            client = TCPClient.getInstance();
            client.connection(this);
        });

        // add group
        binding.ibAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCreateGroupDialog = new FragmentCreateGroupDialog(HomeActivity.this);
                fragmentCreateGroupDialog.show(getSupportFragmentManager(), "");
            }
        });

        binding.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ActivityProfile.class));
            }
        });
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        // TabLayout and ViewPager
        TabLayout tabLayout = binding.tabLayout;
        ViewPager viewPager = binding.viewPager;
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(fragmentChatList, "Chats");
        viewPagerAdapter.addFragment(fragmentUserList, "Users");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        int[] icon = {R.drawable.ic_chats, R.drawable.ic_contact};
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            Objects.requireNonNull(tabLayout.getTabAt(i)).setIcon(icon[i]);
        }
    }

    @Override
    public void onConnectedSuccessfully() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sender = new Sender(client.getMySocket());
                sender.sendObject(new LoginRequest(TCPClient.userResponse.getId()));
                receiver = new Receiver(client.getMySocket());
                TCPClient.sender = sender;
                TCPClient.receiver = receiver;
                homeViewModel.listenMessage();
                loadUser();
                int a = 3;
            }
        }).start();
    }

    private void loadUser() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                homeViewModel.getUserList().observe(HomeActivity.this, userList -> {
                    fragmentUserList.loadUser(userList);
                    Log.d("receive user", "true");
                });
            }
        });
        GetAllUserRequest request = new GetAllUserRequest(TCPClient.getUser().getId());
        TCPClient.sender.sendObject(request);
    }

    @Override
    public void onCreateGroupSuccessfully() {
        fragmentCreateGroupDialog.dismiss();
        Log.d("create group successful", "true");
        homeViewModel.getUserInfoApi();
    }

    public int getId() {
        return (int) new Date().getTime();
    }

    // Adding Logout Funtionality
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (client != null) client.close();
    }

}

