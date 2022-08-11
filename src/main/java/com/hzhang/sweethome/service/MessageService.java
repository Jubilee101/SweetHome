package com.hzhang.sweethome.service;

import com.hzhang.sweethome.model.DeferredRequestList;
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
    private DeferredRequestList deferredRequestList;
    @Autowired
    public MessageService(MessageRepository messageRepository,
                          DeferredRequestList deferredRequestList){
        this.messageRepository=messageRepository;
        this.deferredRequestList = deferredRequestList;
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
        deferredRequestList.publish("MESSAGE");
    }

    public List<Message> findall(){
       List<Message> messageList  = messageRepository.findAll();
//        if (!messageList.isEmpty()) {
//            messageList.sort((Message o1, Message o2) -> {
//                if (o1.getDate().equals(o2.getDate())) {
//                    return o1.getTime().compareTo(o2.getTime());
//                } else {
//                    return o1.getDate().compareTo(o2.getDate());
//                }
//            });
//        }
       return messageList;
    }

}
