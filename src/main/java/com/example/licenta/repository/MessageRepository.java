package com.example.licenta.repository;

import com.example.licenta.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    public List<Message> findMessagesByFromIdAndToIdOrToIdAndFromId(Long fromId, Long toId);
}
