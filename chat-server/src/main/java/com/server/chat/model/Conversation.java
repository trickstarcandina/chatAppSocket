package com.server.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "conversations")
@Setter
@Getter
public class Conversation extends BaseEntity {

    private String name;

    @JsonProperty("isGroup")
    private boolean isGroup;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_conversation",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
}
