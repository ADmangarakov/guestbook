package com.github.admangarakov.guestbook.services;

import com.github.admangarakov.guestbook.domain.Message;
import com.github.admangarakov.guestbook.domain.MessageUI;
import com.github.admangarakov.guestbook.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepo messageRepo;

    @Autowired
    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public void save(MessageUI msgUI, String browserApplication, String ip) {
        Message msg = new Message(msgUI);
        msg.setBrowserInfo(browserApplication);
        msg.setIp(ip);
        messageRepo.save(msg);
    }

    public List<Message> findAll() {
        return messageRepo.findAll();
    }
}
