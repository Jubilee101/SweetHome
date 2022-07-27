package com.hzhang.sweethome.controller;

import com.hzhang.sweethome.model.User;
import com.hzhang.sweethome.model.UserRole;
import com.hzhang.sweethome.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register/resident")
    public void addResident(@RequestBody User user) {
        registerService.add(user, UserRole.ROLE_RESIDENT);
    }

    @PostMapping("/register/manager")
    public void addManager(@RequestBody User user) {
        registerService.add(user, UserRole.ROLE_MANAGER);
    }

}
