package com.server.chat.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "message")
@Getter
@Setter
public class Message extends BaseEntity implements Serializable{
    static final long serialVersionUID = 1L;
    private String content;
    private Integer userId;
    private Integer groupId;
}
