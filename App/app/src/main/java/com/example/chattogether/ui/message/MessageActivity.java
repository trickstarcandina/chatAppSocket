package com.example.chattogether.ui.message;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chattogether.R;
import com.example.chattogether.data.model.MessageResponseItem;
import com.example.chattogether.databinding.ActivityMessageBinding;
import com.example.chattogether.ui.dialog.FragmentLoadingDialog;
import com.example.chattogether.ui.home.HomeActivity;
import com.example.chattogether.ui.home.HomeViewModel;
import com.example.chattogether.ui.service.OnConnected;
import com.example.chattogether.ui.service.connection.TCPClient;
import com.server.chat.model.Message;
import com.server.chat.model.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements OnConnected {

    final int IMAGE_REQUEST = 1;

    TextView textUsernameMessage;
    ImageView avatar;
    EditText messageEt;
    RecyclerView recyclerView;

    List<Message> messagePending = new ArrayList<>();
    List<Message> messageList = new ArrayList<>();
    List<User> userInConversationList = new ArrayList<>();

    ActivityMessageBinding binding;
    MessageAdapter adapter;
    MessageViewModel messageViewModel;
    HomeViewModel homeViewModel;
    int conversationId;
    String conversationName;
    String avatarUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_message);
        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        homeViewModel = HomeActivity.homeViewModel;

        Intent i = getIntent();
        conversationId = i.getIntExtra("conversationId", 1);
        conversationName = i.getStringExtra("conversationName");
        avatarUrl = i.getStringExtra("avatarUrl");
        initView();
        initListener();
        getMessageByConversationId();
    }


    private void initView() {
        // view user information
        textUsernameMessage = binding.textUsernameMessage;
        avatar = binding.avatarMessage;
        initRecyclerView();
        messageEt = binding.etInputMessage;
        textUsernameMessage.setText(conversationName);
        if(avatarUrl != null) Glide.with(getBaseContext())
                .load(avatarUrl)
                .into(avatar);
    }

    private void initRecyclerView() {
        //recycle view
        recyclerView = binding.messageLayout;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new MessageAdapter(this, messageList, userInConversationList);
        recyclerView.setAdapter(adapter);
    }

    private void initListener() {
        listenMessage();
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                String msg = messageEt.getText().toString().trim();
                if (msg.isEmpty()) {
                    Toast.makeText(MessageActivity.this, "No message to send!", Toast.LENGTH_SHORT).show();
                } else {
                    Message m = new Message(msg, TCPClient.getUser().getId(), conversationId);
                    messagePending.add(m);
                    sendMessage();
                    messageEt.setText("");
                    if(binding.tvNoMessage.getVisibility() == View.VISIBLE) binding.tvNoMessage.setVisibility(View.GONE);
                }
            }
        });
        binding.btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, IMAGE_REQUEST);
            }
        });
        //back_btn clicked
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageActivity.this.finish();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void listenMessage() {
        homeViewModel.getMessage().observe(this, messageReceived -> {
            if (messageReceived.getConversationId() == conversationId)
                homeViewModel.getUserList().observe(MessageActivity.this, users -> {            // get username in conversation
                    for (User u : users) {
                        if (u.getId().equals(messageReceived.getUserId()))
                            userInConversationList.add(u);
                    }
                    messageList.add(messageReceived);
                    Log.d("ReceivedMessage", messageReceived.getContent() != null ? messageReceived.getContent() : messageReceived.getUrl());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(binding.tvNoMessage.getVisibility() == View.VISIBLE) binding.tvNoMessage.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();  // must be run on UI thread
                            recyclerView.smoothScrollToPosition(adapter.getItemCount());
                        }
                    });
                });
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private void getMessageByConversationId() {
        messageViewModel.getMessageListByConversationId(conversationId).observe(this, messages -> {
            if(messages.isEmpty())
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.tvNoMessage.setVisibility(View.VISIBLE);
                    }
                });
            homeViewModel.getUserList().observe(MessageActivity.this, users -> {            // get username in conversation
                for (MessageResponseItem messageResponseItem : messages) {
                    messageList.add(new Message(
                            messageResponseItem.getContent(),
                            messageResponseItem.getUserId(),
                            messageResponseItem.getConversationId(),
                            messageResponseItem.getUrl(),
                            messageResponseItem.getContentType()
                    ));
                    for (User u : users) {
                        if (u.getId().equals(messageResponseItem.getUserId()))
                            userInConversationList.add(u);
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();  // must be run on UI thread
                        recyclerView.smoothScrollToPosition(adapter.getItemCount());
                    }

                });

            });
            adapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(adapter.getItemCount());
        });

    }

    void sendMessage() {
        homeViewModel.getUserList().observe(MessageActivity.this, users -> {            // get username in conversation
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (Message msg : messagePending) {
                        TCPClient.sender.sendObject(msg);
                        for (User u : users) {
                            if (u.getId().equals(msg.getUserId()))
                                userInConversationList.add(u);
                        }
                        messageList.add(msg);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(binding.tvNoMessage.getVisibility() == View.VISIBLE) binding.tvNoMessage.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                            recyclerView.smoothScrollToPosition(adapter.getItemCount());
                        }
                    });
                    messagePending.clear();
                }
            }).start();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                FragmentLoadingDialog fragmentLoadingDialog = new FragmentLoadingDialog();
                fragmentLoadingDialog.show(getSupportFragmentManager(), "");
                try {
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] bytes = stream.toByteArray();
                        Message message = new Message(TCPClient.getUser().getId(), conversationId, bytes, "jpg");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                TCPClient.sender.sendObject(message);
                                Log.d("send image successful", "true");
                                fragmentLoadingDialog.dismiss();
                            }
                        }).start();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onConnectedSuccessfully() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        messageViewModel.closeListenerThread();
    }
}