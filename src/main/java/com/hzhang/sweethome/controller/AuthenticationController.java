package com.hzhang.sweethome.controller;

import com.hzhang.sweethome.model.Token;
import com.hzhang.sweethome.model.User;
import com.hzhang.sweethome.model.UserRole;
import com.hzhang.sweethome.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate/resident")
    public Token authenticateResident(@RequestBody User user) {
        return authenticationService.authenticate(user, UserRole.ROLE_RESIDENT);
    }

    @PostMapping("/authenticate/manager")
    public Token authenticateManager(@RequestBody User user) {
        return authenticationService.authenticate(user, UserRole.ROLE_MANAGER);
    }
}
