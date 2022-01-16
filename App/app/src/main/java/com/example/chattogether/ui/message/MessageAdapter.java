package com.example.chattogether.ui.message;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chattogether.R;
import com.example.chattogether.ui.service.connection.TCPClient;
import com.server.chat.model.Message;
import com.server.chat.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private static final int MSG_RIGHT = 0;
    private static final int MSG_LEFT = 1;
    private static final int IMAGE_RIGHT = 2;
    private static final int IMAGE_LEFT = 3;
    Context context;
    List<Message> messageList;
    List<User> userList;
    String imgUrl;
    int imgSize;

    public MessageAdapter(Context context, List<Message> messageList, List<User> userList) {
        this.context = context;
        this.messageList = messageList;
        this.userList = userList;
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.size() > 0 && messageList.get(position).getUserId() == TCPClient.getUser().getId() && messageList.get(position).getUrl() != null)
            return IMAGE_RIGHT;
        if (messageList.size() > 0 && messageList.get(position).getUserId() == TCPClient.getUser().getId())
            return MSG_RIGHT;
        if (messageList.size() > 0 && messageList.get(position).getUrl() != null)
            return IMAGE_LEFT;
        return MSG_LEFT;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = null;

        switch (viewType) {
            case MSG_RIGHT:
                v = LayoutInflater.from(context).inflate(R.layout.item_msg_right, parent, false);
                break;
            case MSG_LEFT:
                v = LayoutInflater.from(context).inflate(R.layout.item_msg_left, parent, false);
                break;
            case IMAGE_LEFT:
                v = LayoutInflater.from(context).inflate(R.layout.item_image_left, parent, false);
                break;
            case IMAGE_RIGHT:
                v = LayoutInflater.from(context).inflate(R.layout.item_image_right, parent, false);
                break;

        }

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Message message = messageList.get(position);
        User user = userList.get(position);
        if (holder.msg != null)
            holder.msg.setText(message.getContent());
        if (holder.sender != null)
            holder.sender.setText(userList.get(position).getUsername());
        if (holder.ivMessageImage != null) {
            Log.d("message has image", (userList.get(position).getUsername() + " http://34.122.94.78:9000/chat" + message.getUrl()));
            Glide.with(context)
                    .load("http://34.122.94.78:9000/chat" + message.getUrl())
                    .centerInside()
                    .into(holder.ivMessageImage);
        }

        if (holder.avatar != null)
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
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView msg;
        public TextView sender;
        public ImageView avatar;
        public ImageView ivMessageImage;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.messageLayout);
            avatar = itemView.findViewById(R.id.avatar_message);
            sender = itemView.findViewById(R.id.tv_sender_username);
            ivMessageImage = itemView.findViewById(R.id.iv_message_image);
        }
    }
}
