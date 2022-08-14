package com.hzhang.sweethome.controller;

import com.hzhang.sweethome.model.UnreadNum;
import com.hzhang.sweethome.service.UnreadNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UnreadNumController {
    private UnreadNumService unreadNumService;

    @Autowired
    public UnreadNumController(UnreadNumService unreadNumService) {
        this.unreadNumService = unreadNumService;
    }

    @GetMapping(value = "/unread_nums")
    public UnreadNum getUnreadNums(@RequestParam(name = "type") String type, Principal principal) {
        return unreadNumService.getUnreadNum(principal.getName(),type);
    }

    @PutMapping(value = "/unread_nums/clear_personal")
    public void clearUnreadNumsByType(@RequestParam(name = "type") String type, Principal principal) {
        unreadNumService.clearUnreadNum(principal.getName(), type);
    }

    @PutMapping(value = "/unread_nums/clear_public")
    public void clearUnreadNumsByType(Principal principal) {
        unreadNumService.clearAllPublicUnreadNum(principal.getName());
    }

}
