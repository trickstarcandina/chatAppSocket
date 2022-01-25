package com.example.chattogether.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.chattogether.R;
import com.example.chattogether.databinding.FragmentUsersBinding;
import com.server.chat.model.User;

import java.util.ArrayList;
import java.util.List;

public class FragmentUserList extends Fragment {

    Context mContext;
    UserAdapter userAdapter;
    HomeViewModel homeViewModel;
    FragmentUsersBinding binding;
    private List<User> userList;

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
        return binding.getRoot();
    }

    public void initAdapter() {
        userAdapter = new UserAdapter(getContext(), userList,this );
        binding.rvUserList.setAdapter(userAdapter);
        binding.rvUserList.setHasFixedSize(true);
        binding.rvUserList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void loadUser(List<User> users) {
        userList.clear();
        userList.addAll(users);
        userAdapter.notifyDataSetChanged();
    }
}