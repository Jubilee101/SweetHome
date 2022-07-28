package com.hzhang.sweethome.service;

import com.hzhang.sweethome.exception.UserAlreadyExistException;
import com.hzhang.sweethome.model.Authority;
import com.hzhang.sweethome.model.InvoiceType;
import com.hzhang.sweethome.model.UnreadNum;
import com.hzhang.sweethome.model.UnreadNumKey;
import com.hzhang.sweethome.model.User;
import com.hzhang.sweethome.model.UserRole;
import com.hzhang.sweethome.repository.AuthorityRepository;
import com.hzhang.sweethome.repository.UnreadNumRepository;
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
    private UnreadNumRepository unreadNumRepository;
    @Autowired
    public RegisterService(UserRepository userRepository,
                           AuthorityRepository authorityRepository,
                           PasswordEncoder passwordEncoder,
                           UnreadNumRepository unreadNumRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.unreadNumRepository = unreadNumRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(User user, UserRole role) throws UserAlreadyExistException {
        if(userRepository.existsById(user.getEmail())) {
            throw new UserAlreadyExistException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        authorityRepository.save(new Authority(user.getEmail(), role.name()));
        for (InvoiceType type : InvoiceType.values()){
            UnreadNumKey key = new UnreadNumKey();
            key.setEmail(user.getEmail())
                            .setType(type.name());
            unreadNumRepository.save(new UnreadNum().setId(key));
        }
    }
}
