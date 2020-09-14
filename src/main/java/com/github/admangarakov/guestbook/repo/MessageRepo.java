package com.github.admangarakov.guestbook.repo;

import com.github.admangarakov.guestbook.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findAll();

    Message save(Message msg);
}
