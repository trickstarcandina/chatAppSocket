package com.example.chattogether.data.old;

public class DataResponse<T> {
    private String access_token;
    private String refresh_token;
    private T user;

    public DataResponse(String access_token, String refresh_token, T user) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.user = user;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public T getUser() {
        return user;
    }

    public void setUser(T user) {
        this.user = user;
    }
}
