package com.hzhang.sweethome.service;

import com.hzhang.sweethome.model.MaintenanceReservation;
import com.hzhang.sweethome.model.Message;
import com.hzhang.sweethome.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository=messageRepository;
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(String text,String nameandroom){
        Message message = new Message
                .Builder()
                .setTime(LocalTime.now())
                .setDate(LocalDate.now())
                .setText(text)
                .setNameAndRoom(nameandroom)
                .build();
        messageRepository.save(message);
    }

    public List<Message> findall(){
       List<Message> messageList  = messageRepository.findAll();
        if (!messageList.isEmpty()) {
            messageList.sort((Message o1, Message o2) -> {
                if (o1.getDate() == null && o2.getDate() == null) {
                    return 0;
                } else if (o1.getDate() == null) {
                    return -1;
                } else if (o2.getDate() == null) {
                    return 1;
                } else {
                    if (o1.getDate().equals(o2.getDate())) {
                        return -1 * o1.getTime().compareTo(o2.getTime());
                    } else {
                        return -1 * o1.getDate().compareTo(o2.getDate());
                    }
                }
            });
        }
       return messageList;
    }

}
