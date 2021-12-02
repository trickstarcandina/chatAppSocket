package com.example.chattogether.ui.message;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chattogether.R;
import com.example.chattogether.data.model.MessageResponseItem;
import com.example.chattogether.databinding.ActivityMessageBinding;
import com.example.chattogether.ui.home.HomeActivity;
import com.example.chattogether.ui.home.HomeViewModel;
import com.example.chattogether.ui.service.OnConnected;
import com.example.chattogether.ui.service.connection.TCPClient;
import com.server.chat.model.Message;

import java.util.ArrayList;
import java.util.List;

import utils.Sender;

public class MessageActivity extends AppCompatActivity implements OnConnected {

    TextView textUsernameMessage;
    ImageView avatar;
    EditText messageEt;
    RecyclerView recyclerView;

    List<Message> messagePending = new ArrayList<>();
    List<Message> messageList = new ArrayList<>();
    Sender sender;

    ActivityMessageBinding binding;
    MessageAdapter adapter;
    MessageViewModel messageViewModel;
    HomeViewModel homeViewModel;
    int conversationId;
    String conversationName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_message);
        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        homeViewModel = HomeActivity.homeViewModel;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        Intent i = getIntent();
        conversationId = i.getIntExtra("conversationId", 1);
        conversationName = i.getStringExtra("conversationName");
        initView();
        getMessageByConversationId();
        listenMessage();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getMessageByConversationId() {
        messageViewModel.getMessageListByConversationId(conversationId).observe(this, messages -> {
            for (MessageResponseItem messageResponseItem : messages) {
                messageList.add(new Message(
                        messageResponseItem.getContent(),
                        messageResponseItem.getUserId(),
                        messageResponseItem.getConversationId()
                ));
            }
            adapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(adapter.getItemCount());
        });

    }

    private void initView() {
        //back_btn clicked
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageActivity.this.finish();
            }
        });

        // view user information
        textUsernameMessage = binding.textUsernameMessage;
        avatar = binding.avatarMessage;
        initRecyclerView();
        // send message
        messageEt = binding.etInputMessage;
        textUsernameMessage.setText(conversationName);
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
//
//                    TCPClient client = TCPClient.getInstance();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            TCPClient.sender.sendObject(m);
                        }
                    }).start();
                    messageList.add(m);
                    adapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(adapter.getItemCount());
                    messageEt.setText("");
                }

            }

        });
    }

    private void initRecyclerView() {
        //recycle view
        recyclerView = binding.messageLayout;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new MessageAdapter(this, messageList);
        recyclerView.setAdapter(adapter);
    }


    @SuppressLint("NotifyDataSetChanged")
    private void listenMessage() {
//        if(MessageViewModel.listen != null){
//            Log.d("Thread is initialized", "true");
//            return;
//        }
        homeViewModel.getMessage().observe(this, messageReceived -> {

            if (messageReceived.getConversationId() == conversationId)
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("ReceivedMessage", messageReceived.getContent());
                        messageList.add(messageReceived);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();  // must be run on UI thread
                                recyclerView.smoothScrollToPosition(adapter.getItemCount());
                            }
                        });
                    }
                }).start();
        });

    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onConnectedSuccessfully() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Message msg : messagePending) {
                    sender.sendObject(msg);
                    messageList.add(msg);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(adapter.getItemCount());
                    }
                });
                messagePending.clear();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        messageViewModel.closeListenerThread();
    }
}