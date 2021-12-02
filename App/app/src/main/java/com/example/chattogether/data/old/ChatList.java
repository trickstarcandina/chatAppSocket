package com.example.chattogether.data.old;

public class ChatList {

    private String id;
    private String last_msg;
    private boolean you_are_sender;

    public ChatList(String id, String last_msg, boolean you_are_sender) {
        this.id = id;
        this.last_msg = last_msg;
        this.you_are_sender = you_are_sender;
    }

    public boolean isYou_are_sender() {
        return you_are_sender;
    }

    public void setYou_are_sender(boolean you_are_sender) {
        this.you_are_sender = you_are_sender;
    }

    public ChatList(){
    }

    public String getLast_msg() {
        return last_msg;
    }

    public void setLast_msg(String last_msg) {
        this.last_msg = last_msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ChatList(String id) {
        this.id = id;
    }
}
