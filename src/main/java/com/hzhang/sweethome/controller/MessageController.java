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
    @GetMapping("/messages")
    public List<Message> findall(){
        System.out.println("find all start");
        List<Message> messages = messageService.findall();
        System.out.println("found all");
        return messages;
    }

    @PostMapping("/messages")
    public void add(@RequestParam("text") String text,
                    @RequestParam("name_and_room") String name_and_room,
                    Principal principal){
        messageService.add(principal.getName(), text, name_and_room);
    }
}
