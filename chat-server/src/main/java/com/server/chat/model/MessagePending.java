package com.server.chat.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "message_pending")
@Getter
@Setter
public class MessagePending extends BaseEntity {

    @ManyToOne
    private Message message;

    @ManyToOne
    private User user;

}
