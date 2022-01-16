package com.example.chattogether.data.old;

public class LoginRequest {
    private String username;
    private String password;
    private String client_id;
    private String client_secret;
    private String platformOS;

    public LoginRequest(String username, String password, String client_id, String client_secret, String platformOS) {
        this.username = username;
        this.password = password;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.platformOS = platformOS;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getPlatformOS() {
        return platformOS;
    }

    public void setPlatformOS(String platformOS) {
        this.platformOS = platformOS;
    }
}
