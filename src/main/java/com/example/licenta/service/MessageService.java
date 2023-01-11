package com.example.licenta.service;
import com.example.licenta.model.Message;
import com.example.licenta.model.User;
import com.example.licenta.model.dto.MessageDTO;
import com.example.licenta.repository.MessageRepository;
import com.example.licenta.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class MessageService{
    @Resource
    private MessageRepository messageRepository;
    private UserRepository userRepository;

    public List<MessageDTO> findAllMessagesBetweenTwoIds(Long fromId, Long toId) {
        List<Message> mesaje =  messageRepository.findMessagesByFromIdAndToIdOrToIdAndFromId(fromId, toId);
        List<MessageDTO> mesajedto = new ArrayList<MessageDTO>();
        for (Message m : mesaje) {
            String lastnameFromId = userRepository.findUserById(fromId).getLastName();
            String firstnameFromId = userRepository.findUserById(fromId).getFirstName();
            String lastnameToId = userRepository.findUserById(toId).getLastName();
            String firstnameToId = userRepository.findUserById(toId).getFirstName();
            mesajedto.add(new MessageDTO(m.getId(), m.getFromId(), m.getToId(), m.getText(), m.getTime(), lastnameFromId, lastnameToId, firstnameFromId, firstnameToId));
        }
        return mesajedto;
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }

}
