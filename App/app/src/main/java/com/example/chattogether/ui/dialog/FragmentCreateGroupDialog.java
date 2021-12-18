package com.example.chattogether.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.chattogether.R;
import com.example.chattogether.databinding.FragmentCreateGroupDialogBinding;
import com.example.chattogether.ui.home.HomeActivity;
import com.example.chattogether.ui.home.HomeViewModel;
import com.example.chattogether.ui.service.OnConnected;
import com.example.chattogether.ui.service.OnCreateGroup;
import com.example.chattogether.ui.service.connection.TCPClient;
import com.server.chat.model.CreateGroupRequest;
import com.server.chat.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import utils.Sender;

public class FragmentCreateGroupDialog extends DialogFragment implements OnConnected {

    static HomeViewModel homeViewModel;
    Context mContext;
    CreateGroupAdapterDialog userAdapter;
    FragmentCreateGroupDialogBinding binding;
    private List<User> userResponseList;
    OnCreateGroup onCreateGroup;

    TCPClient tcpClient;

    private List<Boolean> selectedUserListCheckBox;


    public FragmentCreateGroupDialog(OnCreateGroup onCreateGroup) {
        this.onCreateGroup = onCreateGroup;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_group_dialog, container, false);
        userResponseList = new ArrayList<>();
        initAdapter();
        loadUser();
        initViewListener();
        return binding.getRoot();
    }

    private void initViewListener() {
        binding.btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Integer> userIds = new ArrayList<>();

                for(int i = 0 ; i < userAdapter.getSelectedUserList().size();i++){
                    Boolean b = userAdapter.getSelectedUserList().get(i);
                    if(b) userIds.add(userResponseList.get(i).getId());
                }

                if(!userIds.isEmpty() && !binding.etGroupName.getText().toString().equals(""))
                    createGroupRequest(userIds);
                else
                    Toast.makeText(mContext, "Nhập tên nhóm và chọn thành viên", Toast.LENGTH_SHORT).show();
            }



        });
    }

    private void createGroupRequest(List<Integer> userIds) {

        tcpClient = TCPClient.getInstance();


        new Thread(new Runnable() {
            @Override
            public void run() {
                CreateGroupRequest request = new CreateGroupRequest(binding.etGroupName.getText().toString(), userIds);
                TCPClient.sender.sendObject(request);

                showAlertMessage("Tạo nhóm thành công");
                onCreateGroup.onCreateGroupSuccessfully();
            }
        }).start();

    }

    private void showAlertMessage(String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(mContext)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    public void initAdapter() {
        userAdapter = new CreateGroupAdapterDialog(getContext(), userResponseList);
        binding.rvAddFriend.setAdapter(userAdapter);
        binding.rvAddFriend.setHasFixedSize(true);
        binding.rvAddFriend.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadUser() {
        homeViewModel = HomeActivity.homeViewModel;
        homeViewModel.getUserList().observe((AppCompatActivity) mContext, user -> {
            userResponseList.clear();
            userResponseList.addAll(user);
            selectedUserListCheckBox = new ArrayList<>(Arrays.asList(new Boolean[10]));
            Collections.fill(selectedUserListCheckBox, false);
            userAdapter.setSelectedUserList(selectedUserListCheckBox);
            userAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onConnectedSuccessfully() {

    }
}
