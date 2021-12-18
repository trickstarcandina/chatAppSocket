package com.example.chattogether.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chattogether.R;
import com.example.chattogether.data.api.ApiService;
import com.example.chattogether.data.auth.TempClient;
import com.example.chattogether.data.model.Conversation;
import com.example.chattogether.data.model.ConversationDetail;
import com.example.chattogether.data.model.ConversationInfo;
import com.example.chattogether.databinding.FragmentChatsBinding;
import com.example.chattogether.ui.service.connection.TCPClient;
import com.server.chat.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentChatList extends Fragment {

    RecyclerView recyclerView;
    List<Conversation> conversations;
    ChatAdapter adapter;
    Context mContext;

    HomeViewModel homeViewModel;
    FragmentChatsBinding binding;

    public FragmentChatList() {
    }

    public static FragmentChatList newInstance() {
        return new FragmentChatList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chats, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        binding.etSearch.clearFocus();
        conversations = new ArrayList<>();
        recyclerView = binding.rvChatList;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatAdapter(mContext, conversations);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getConversation(List<Conversation> conversationList) {
        conversations.clear();
        conversations.addAll(conversationList);
        Collections.reverse(conversations);

        for(Conversation conversation: conversations){

            if(!conversation.getIsGroup()){
                String id = String.valueOf(conversation.getId());

                ApiService apiService = TempClient.getInstance();
                apiService.getConversationInfo(id).enqueue(new Callback<List<ConversationInfo>>() {
                    @Override
                    public void onResponse(Call<List<ConversationInfo>> call, Response<List<ConversationInfo>> response) {
                        if(response.code() == 200){
                            assert response.body() != null;
                            for (ConversationInfo conversationInfo: response.body()){
                                if(conversationInfo.id != TCPClient.getUser().getId())
                                {
                                    conversation.setName(conversationInfo.username);
                                    conversation.conversationAvatar = conversationInfo.avatarUrl;
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ConversationInfo>> call, Throwable t) {
                        Log.d("failed", t.toString());
                    }
                });
            }

        }
        adapter.notifyDataSetChanged();
    }

    public void getConversationInfo(String id){

    }
}