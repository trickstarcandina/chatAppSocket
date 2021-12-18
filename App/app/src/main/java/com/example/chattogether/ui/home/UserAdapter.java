package com.example.chattogether.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chattogether.R;
import com.example.chattogether.data.api.ApiService;
import com.example.chattogether.data.auth.TempClient;
import com.example.chattogether.data.model.ConversationInfo;
import com.example.chattogether.ui.message.MessageActivity;
import com.example.chattogether.ui.service.connection.TCPClient;
import com.server.chat.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final Context context;
    private final List<User> userResponseList;
    LifecycleOwner lifecycleOwner;

    public UserAdapter(Context context, List<User> userResponseList, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.userResponseList = userResponseList;
        this.lifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        User user = userResponseList.get(position);
        holder.username.setText(user.getUsername());

        if (user.getAvatarUrl() != null) {
            Glide.with(context)
                    .load("http://34.122.94.78:9000/chat" + user.getAvatarUrl())
                    .centerInside()
                    .into(holder.avatar);
        } else
            Glide.with(context)
                    .load("https://j03qukjhr2obj.vcdn.cloud/image-upload/StockDesignOnTV/Logo giải đấu/Premier League.png")
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.avatar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                HomeActivity.homeViewModel.getUserInfo().observe(lifecycleOwner, userResponse -> {

                    userResponse.getConversations().forEach((conversation -> {

                        ApiService apiService = TempClient.getInstance();
                        apiService.getConversationInfo(String.valueOf(conversation.getId())).enqueue(new Callback<List<ConversationInfo>>() {
                            @Override
                            public void onResponse(Call<List<ConversationInfo>> call, Response<List<ConversationInfo>> response) {
                                if (response.code() == 200) {
                                    assert response.body() != null;
                                    List<ConversationInfo> conversationInfoList = response.body();
                                    if (conversationInfoList.size() == 2) {
                                        if ((conversationInfoList.get(0).id == TCPClient.getUser().getId() && conversationInfoList.get(1).id == user.getId())
                                                ||
                                                (conversationInfoList.get(1).id == TCPClient.getUser().getId() && conversationInfoList.get(0).id == user.getId())) {
                                            Intent i = new Intent(context, MessageActivity.class);
                                            i.putExtra("conversationId", conversation.getId());
                                            i.putExtra("conversationName", user.getUsername());
                                            i.putExtra("avatarUrl","http://34.122.94.78:9000/chat" + user.getAvatarUrl());
                                            context.startActivity(i);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<ConversationInfo>> call, Throwable t) {
                                Log.d("failed", t.toString());
                            }
                        });

                    }));
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return userResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView avatar, online;
        public TextView username;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.iv_avatar);
            username = itemView.findViewById(R.id.textUser);
            online = itemView.findViewById(R.id.online);
        }
    }
}



