package com.example.chattogether.data.old;

public class BaseResponse<T> {
    private boolean success;
    private String messages;
    private int statusCode;
    private T data;

    public BaseResponse(boolean success, String messages, int statusCode, T data) {
        this.success = success;
        this.messages = messages;
        this.statusCode = statusCode;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
