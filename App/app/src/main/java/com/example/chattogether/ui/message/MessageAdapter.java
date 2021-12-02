package com.example.chattogether.ui.message;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chattogether.R;
import com.example.chattogether.ui.service.connection.TCPClient;
import com.server.chat.model.Message;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private static final int MSG_RIGHT = 1;
    private static final int MSG_LEFT = 0;
    Context context;
    List<Message> messageList;
    String imgUrl;
    int imgSize;

    public MessageAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.size() > 0 && messageList.get(position).getUserId() == TCPClient.getUser().getId())
            return MSG_RIGHT;
        return MSG_LEFT;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == MSG_RIGHT) {
            v = LayoutInflater.from(context).inflate(R.layout.item_msg_right, parent, false);
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.item_msg_left, parent, false);
        }

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.msg.setText(message.getContent());
        if(holder.sender != null)
            holder.sender.setText("User ID " + message.getUserId());

//        if(position >= 0 && position < chatList.size() - 1) {
//            Chat nextChat = chatList.get(position + 1);
//            if (!nextChat.getSender().equals(firebaseUser.getUid())){
//                holder.avatar.getLayoutParams().height = 0;
//                holder.avatar.setVisibility(View.INVISIBLE);
//                return;
//            }
//        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView msg;
        public TextView sender;
        public ImageView avatar;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.messageLayout);
            avatar = itemView.findViewById(R.id.avatar_message);
            sender = itemView.findViewById(R.id.tv_sender_username);
        }
    }
}
