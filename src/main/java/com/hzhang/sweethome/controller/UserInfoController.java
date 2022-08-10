package com.hzhang.sweethome.controller;

import com.hzhang.sweethome.model.User;
import com.hzhang.sweethome.service.UserInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoController {
    UserInfoService userInfoService;
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/user")
    public User getUser(Principal principal) {
        return userInfoService.getUser(principal.getName());
    }
}
