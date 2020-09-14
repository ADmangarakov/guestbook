package com.github.admangarakov.guestbook.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.URL;
import java.util.Date;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String emailAddress;
    private URL homepage;
    private String text;

    private String ip;
    private String browserInfo;
    private Date creationDate;

    public Message() {
    }

    public Message(MessageUI messageUI) {
        setUsername(messageUI.getUsername());
        setEmailAddress(messageUI.getEmailAddress());
        setHomepage(messageUI.getHomepage());
        setText(messageUI.getText());
        setCreationDate(messageUI.getCreationDate());
    }

}
