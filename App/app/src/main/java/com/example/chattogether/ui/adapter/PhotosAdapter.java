package com.example.chattogether.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chattogether.R;
import com.example.chattogether.ui.profile.ViewImage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    Context context;
    ArrayList<String> photoList;

    public PhotosAdapter(Context context, ArrayList<String> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @NonNull
    @NotNull
    @Override
    public PhotosAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PhotosAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(photoList.get(position))
                .into(holder.photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewImage.class);
                intent.putExtra("src",photoList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView photo;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
        }
    }
}
