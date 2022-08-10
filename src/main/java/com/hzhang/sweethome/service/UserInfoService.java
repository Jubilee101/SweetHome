package com.hzhang.sweethome.service;

import com.hzhang.sweethome.exception.UserNotExistException;
import com.hzhang.sweethome.model.User;
import com.hzhang.sweethome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService {
    UserRepository userRepository;

    @Autowired
    public UserInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String email) {
        Optional<User> user = userRepository.findById(email);
        if (!user.isPresent()) {
            throw new UserNotExistException("User does not exist");
        }
        return user.get();
    }
}
