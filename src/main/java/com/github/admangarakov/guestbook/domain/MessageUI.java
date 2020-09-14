package com.github.admangarakov.guestbook.domain;

import lombok.Data;

import java.net.URL;
import java.util.Date;

@Data
public class MessageUI {
    private Long id;
    private String username;
    private String emailAddress;
    private URL homepage;
    private String text;
    private Date creationDate;

    public MessageUI() {

    }

    public MessageUI(Message msg) {
        setId(msg.getId());
        setUsername(msg.getUsername());
        setEmailAddress(msg.getEmailAddress());
        setHomepage(msg.getHomepage());
        setText(msg.getText());
        setCreationDate(msg.getCreationDate());
    }
}
