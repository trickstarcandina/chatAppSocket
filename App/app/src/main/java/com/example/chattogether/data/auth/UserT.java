package com.example.chattogether.data.auth;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class UserT extends BaseObservable {

    String username;
    String email;

    public UserT(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
