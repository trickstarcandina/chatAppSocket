package com.server.chat.model;

import java.io.Serializable;
import java.util.List;


public class CreateGroupRequest implements Serializable {
    static final long serialVersionUID = 4L;
    private String name;
    private List<Integer> userIds;

    public CreateGroupRequest() {
    }

    public CreateGroupRequest(String name, List<Integer> userIds) {
        this.name = name;
        this.userIds = userIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }
}
