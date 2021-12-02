package com.example.chattogether.ui.profile;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chattogether.ui.adapter.PhotosAdapter;
import com.example.chattogether.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAvatar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAvatar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<String> photoList;
    RecyclerView recyclerView;
    StorageReference storageReference;
    String userId;

    public FragmentAvatar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Avatars.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAvatar newInstance(String param1, String param2) {
        FragmentAvatar fragment = new FragmentAvatar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle bundle = this.getArguments();
        photoList = new ArrayList<>();

        int avatarId = bundle.getInt("avatarId");
        userId = bundle.getString("userId");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_avatars, container, false);
        recyclerView = view.findViewById(R.id.recycle_photos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // set item recycleView

        for(int i=1;i<=avatarId;i++){
            storageReference = FirebaseStorage.getInstance().getReference("Profile/"+userId+"/"+i);
            storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Uri> task) {
                    photoList.add(task.getResult().toString());
                    Log.d("avatarId",task.getResult().toString());
                    PhotosAdapter photosAdapter = new PhotosAdapter(getContext(),photoList);
                    recyclerView.setAdapter(photosAdapter);
                }
            });
        }
//        Log.d("photoList",photoList.toString());


        return view;
    }
}