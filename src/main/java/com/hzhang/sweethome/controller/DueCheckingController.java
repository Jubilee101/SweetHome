package com.hzhang.sweethome.controller;

import com.hzhang.sweethome.service.DueCheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class DueCheckingController {
    private DueCheckingService dueCheckingService;

    @Autowired
    public DueCheckingController(DueCheckingService service){
        dueCheckingService = service;
    }

    @PostMapping(value = "/due")
    public void checkDue(Principal principal) {
        dueCheckingService.checkDue(principal.getName());
    }
}
