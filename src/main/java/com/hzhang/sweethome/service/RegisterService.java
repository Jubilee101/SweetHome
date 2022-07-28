package com.hzhang.sweethome.service;

import com.hzhang.sweethome.exception.UserAlreadyExistException;
import com.hzhang.sweethome.model.Authority;
import com.hzhang.sweethome.model.User;
import com.hzhang.sweethome.model.UserRole;
import com.hzhang.sweethome.repository.AuthorityRepository;
import com.hzhang.sweethome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;
    private UnreadNumService unreadNumService;
    @Autowired
    public RegisterService(UserRepository userRepository,
                           AuthorityRepository authorityRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(User user, UserRole role) throws UserAlreadyExistException {
        if(userRepository.existsById(user.getEmail())) {
            throw new UserAlreadyExistException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        authorityRepository.save(new Authority(user.getEmail(), role.name()));

    }
}
