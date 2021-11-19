package com.server.chat.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {
    private String username;
    private String password;
    private String nickName;
    private String address;
    private boolean status;

    @ManyToMany(mappedBy = "users")
    private List<Conversation> conversations;
}
