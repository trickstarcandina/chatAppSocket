package com.example.chattogether.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chattogether.R;
import com.example.chattogether.data.auth.UserT;
import com.example.chattogether.ui.message.MessageActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserAdapterDialog extends RecyclerView.Adapter<UserAdapterDialog.ViewHolder> {

    private final Context context;
    private final List<UserT> userList;

    public UserAdapterDialog(Context context, List<UserT> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_add_group,parent,false);
        return new UserAdapterDialog.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        UserT user = userList.get(position);
        holder.username.setText(user.getUsername());
        holder.email.setText(user.getEmail());
//        if(user.getImageUrl().equals("default")){
//            holder.avatar.setImageResource(R.drawable.ic_launcher_background);
//        } else {
//            Glide.with(context)
//                    .load(user.getImageUrl())
//                    .into(holder.avatar);
//        }

//        if(user.getOnline() == null || !user.getOnline().equals("on"))
//            holder.online.setVisibility(View.INVISIBLE);
//        else
//            holder.online.setVisibility(View.VISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MessageActivity.class);
//                i.putExtra("userID",user.user());
//                i.putExtra("imgUrl",user.getImageUrl());
                context.startActivity(i);
            }
        });
    }

//    @Override
//    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
//        User user = userList.get(position);
//        holder.
//    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public ImageView avatar, online;
        public TextView username;
        public TextView email;
        public CheckBox checkBox;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.im_avatar_add_group);
            username = itemView.findViewById(R.id.tv_username_add_group);
            email = itemView.findViewById(R.id.tv_email_add_group);
            online = itemView.findViewById(R.id.iv_online_add_group);
            checkBox = itemView.findViewById(R.id.cb_add_fr_to_group);
        }
    }
}



