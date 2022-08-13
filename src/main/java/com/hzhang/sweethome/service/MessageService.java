package com.hzhang.sweethome.service;

import com.hzhang.sweethome.model.DeferredRequestList;
import com.hzhang.sweethome.model.MaintenanceReservation;
import com.hzhang.sweethome.model.Message;
import com.hzhang.sweethome.repository.MessageRepository;
import com.hzhang.sweethome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private DeferredRequestList deferredRequestList;
    private UserRepository userRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository,
                          DeferredRequestList deferredRequestList,
                          UserRepository userRepository){
        this.messageRepository=messageRepository;
        this.deferredRequestList = deferredRequestList;
        this.userRepository = userRepository;
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(String text,String nameAndRoom){
        Message message = new Message
                .Builder()
                .setTime(LocalTime.now())
                .setDate(LocalDate.now())
                .setText(text)
                .setNameAndRoom(nameAndRoom)
                .build();
        messageRepository.save(message);
        deferredRequestList.publishMsg(userRepository.findAll(), message);
    }

    public List<Message> findFirstTen(){
       List<Message> messageList  = messageRepository.findFirstTen();
        Collections.reverse(messageList);
       return messageList;
    }

    public List<Message> findAll(){
        List<Message> messageList  = messageRepository.findAll();
        return messageList;
    }

    public List<Message> loadMessages(long id) {
        List<Message> messages = messageRepository.loadMessage(id);
        Collections.reverse(messages);
        return messages;
    }

}
