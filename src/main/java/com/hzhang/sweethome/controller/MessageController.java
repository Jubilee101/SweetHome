package com.hzhang.sweethome.controller;


import com.hzhang.sweethome.model.Message;
import com.hzhang.sweethome.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@RestController
public class MessageController {
    private MessageService messageService;
    @Autowired
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }
    @GetMapping("/messages/init")
    public List<Message> findFirstTen(){
        List<Message> messages = messageService.findFirstTen();
        return messages;
    }

    @GetMapping("/messages")
    public List<Message> findall(){
        List<Message> messages = messageService.findAll();
        return messages;
    }

    @PostMapping("/messages")
    public void add(@RequestParam("text") String text,
                    @RequestParam("name_and_room") String name_and_room){
        messageService.add(text, name_and_room);
    }

    @GetMapping("/messages/{id}")
    public List<Message> loadMessage(@PathVariable("id") long id) {
        return messageService.loadMessages(id);
    }
}
