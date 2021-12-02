package com.example.chattogether.ui.home;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.chattogether.R;
import com.example.chattogether.ui.service.connection.TCPClient;
import com.example.chattogether.databinding.ActivityHomeBinding;
import com.example.chattogether.ui.adapter.ViewPagerAdapter;
import com.example.chattogether.ui.dialog.FragmentAddFriendDialog;
import com.example.chattogether.ui.service.OnConnected;
import com.google.android.material.tabs.TabLayout;
import com.server.chat.model.LoginRequest;

import java.util.Date;
import java.util.Objects;

import utils.Receiver;
import utils.Sender;

public class HomeActivity extends AppCompatActivity implements OnConnected {
    public static HomeViewModel homeViewModel;
    ImageView avatar;
    TCPClient client;
    ActivityHomeBinding binding;
    public static Sender sender;
    public static Receiver receiver;

    FragmentChatList fragmentChatList = FragmentChatList.newInstance();
    FragmentUserList fragmentUserList = FragmentUserList.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        initView();

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getUserInfo().observe(this, user -> {
            fragmentChatList.getConversation(user.conversations);
            TCPClient.user = user;
            client = TCPClient.getInstance();
            client.connection(this);
        });

        // add group
        binding.ibAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentAddFriendDialog fragmentAddFriendDialog = new FragmentAddFriendDialog();
                fragmentAddFriendDialog.show(getSupportFragmentManager(), "");
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
                sender  = new Sender(client.getMySocket());
                sender.sendObject(new LoginRequest(TCPClient.user.getId()));
                receiver = new Receiver(client.getMySocket());
                homeViewModel.listenMessage();
                TCPClient.sender = sender;
                TCPClient.receiver = receiver;
                int a = 3;
            }
        }).start();
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

