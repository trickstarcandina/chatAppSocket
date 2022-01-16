package com.example.chattogether.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chattogether.R;
import com.example.chattogether.data.model.Conversation;
import com.example.chattogether.ui.message.MessageActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    Context context;
    List<Conversation> conversations; // list user chat recently by ID

    public ChatAdapter(Context context, List<Conversation> conversations) {
        this.context = context;
        this.conversations = conversations;
    }


    @NonNull
    @NotNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChatAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Conversation conversation = conversations.get(position);

        holder.username.setText(conversation.getName() == null ? "Conversation #" + conversation.getId() : conversation.getName());
//        holder.online.setVisibility();
        if (conversation.conversationAvatar != null) {
            Glide.with(context)
                    .load("http://34.122.94.78:9000/chat" + conversation.conversationAvatar)
                    .centerInside()
                    .into(holder.avatar);
        } else {
            Glide.with(context)
                    .load("https://j03qukjhr2obj.vcdn.cloud/image-upload/StockDesignOnTV/Logo giải đấu/Premier League.png")
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.avatar);
        }
        // listen on chatItem is clicked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to msgActivity
                Intent i = new Intent(context, MessageActivity.class);
                i.putExtra("conversationId", conversation.getId());
                if (conversation.conversationAvatar != null)
                    i.putExtra("avatarUrl", "http://34.122.94.78:9000/chat" + conversation.conversationAvatar);
                i.putExtra("conversationName", conversation.getName() == null ? "Conversation #" + conversation.getId() : conversation.getName());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView avatar, online;
        public TextView username;
        public TextView lastMsg;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.avatar = itemView.findViewById(R.id.avatar_chat_list);
            this.username = itemView.findViewById(R.id.textUser);
            this.online = itemView.findViewById(R.id.online);
        }
    }
}
