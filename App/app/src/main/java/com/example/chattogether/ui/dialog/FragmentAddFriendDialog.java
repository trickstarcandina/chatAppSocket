package com.example.chattogether.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.chattogether.R;
import com.example.chattogether.data.auth.UserT;
import com.example.chattogether.databinding.FragmentAddFriendDialogBinding;
import com.example.chattogether.ui.adapter.UserAdapter;
import com.example.chattogether.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentAddFriendDialog extends DialogFragment {

    Context mContext;
    UserAdapterDialog userAdapter;
    static HomeViewModel homeViewModel;
    FragmentAddFriendDialogBinding binding;
    private List<UserT> userList;


    public  FragmentAddFriendDialog() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_friend_dialog, container, false);
        userList = new ArrayList<>();
        initAdapter();
        loadUser();
        return binding.getRoot();
    }

    public void initAdapter() {
        userAdapter = new UserAdapterDialog(getContext(), userList);
        binding.rvAddFriend.setAdapter(userAdapter);
        binding.rvAddFriend.setHasFixedSize(true);
        binding.rvAddFriend.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadUser() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getUserList().observe((AppCompatActivity) mContext, userTS -> {
            userList.clear();
            userList.addAll(userTS);
            userAdapter.notifyDataSetChanged();
        });
    }
}
