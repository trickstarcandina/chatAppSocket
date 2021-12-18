package com.example.chattogether.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chattogether.R;
import com.example.chattogether.ui.message.MessageActivity;
import com.server.chat.model.User;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateGroupAdapterDialog extends RecyclerView.Adapter<CreateGroupAdapterDialog.ViewHolder> {

    private final Context context;
    private final List<User> userList;
    private List<Boolean> selectedUserList;

    public CreateGroupAdapterDialog(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        int a = 3;
    }

    public void setSelectedUserList( List<Boolean> selectedUserList){
        this.selectedUserList = selectedUserList;
        int a = 3;
    }

    public List<Boolean> getSelectedUserList( ){
        return this.selectedUserList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_add_group, parent, false);
        return new CreateGroupAdapterDialog.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User userResponse = userList.get(position);
        holder.username.setText(userResponse.getUsername());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MessageActivity.class);
                context.startActivity(i);
            }
        });

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectedUserList.set(position, isChecked);
                Log.d("check", selectedUserList.get(position).toString());
            }
        });

        if(userResponse.getAvatarUrl() != null){
            Glide.with(context)
                    .load("http://34.122.94.78:9000/chat"+userResponse.getAvatarUrl())
                    .centerInside()
                    .into(holder.avatar);
        }
        else
            Glide.with(context)
                    .load("https://j03qukjhr2obj.vcdn.cloud/image-upload/StockDesignOnTV/Logo giải đấu/Premier League.png")
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView avatar, online;
        public TextView username;
        public TextView email;
        public CheckBox checkBox;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.im_avatar_add_group);
            username = itemView.findViewById(R.id.tv_username_add_group);
            online = itemView.findViewById(R.id.iv_online_add_group);
            checkBox = itemView.findViewById(R.id.cb_add_fr_to_group);
        }
    }
}



