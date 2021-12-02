package com.example.chattogether.ui.profile;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chattogether.ui.adapter.PhotosAdapter;
import com.example.chattogether.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FragmentBackground extends Fragment {

    StorageReference storageReference;
    ArrayList<String> photoList;

    public FragmentBackground() {
        // Required empty public constructor
    }

    public static FragmentBackground newInstance(String param1, String param2) {
        FragmentBackground fragment = new FragmentBackground();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        String userId = bundle.getString("userId");
        int backgroundId = bundle.getInt("backgroundId");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_background, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_photos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // refer to database - Image Url is added to photoList
        photoList = new ArrayList<>();
        for (int i = 1; i<=backgroundId;i++){
            storageReference = FirebaseStorage.getInstance().getReference("Background/"+userId+"/"+i);
            storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Uri> task) {
                    photoList.add(task.getResult().toString());
                    PhotosAdapter photosAdapter = new PhotosAdapter(getContext(),photoList);
                    recyclerView.setAdapter(photosAdapter);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {

                }
            });
        }

        return view;
    }
}