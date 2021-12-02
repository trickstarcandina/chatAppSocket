package com.example.chattogether.ui.home;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.chattogether.R;
import com.example.chattogether.data.auth.UserT;
import com.example.chattogether.databinding.FragmentUsersBinding;
import com.example.chattogether.ui.adapter.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentUserList extends Fragment {

    Context mContext;
    UserAdapter userAdapter;
    HomeViewModel homeViewModel;
    FragmentUsersBinding binding;
    private List<UserT> userList;

    public static FragmentUserList newInstance() {
        return new FragmentUserList();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_users, container, false);
        userList = new ArrayList<>();
        initAdapter();
        loadUser();
        return binding.getRoot();
    }

    public void initAdapter() {
        userAdapter = new UserAdapter(getContext(), userList);
        binding.rvUserList.setAdapter(userAdapter);
        binding.rvUserList.setHasFixedSize(true);
        binding.rvUserList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadUser() {
        homeViewModel.getUserList().observe((AppCompatActivity) mContext, userTS -> {
            userList.clear();
            userList.addAll(userTS);
            userAdapter.notifyDataSetChanged();
        });


    }
}