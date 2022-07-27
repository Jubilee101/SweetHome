package com.hzhang.sweethome.service;

import com.hzhang.sweethome.model.UnreadNum;
import com.hzhang.sweethome.model.UnreadNumKey;
import com.hzhang.sweethome.repository.UnreadNumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UnreadNumService {

    private UnreadNumRepository unreadNumRepository;

    @Autowired
    public UnreadNumService(UnreadNumRepository unreadNumRepository) {
        this.unreadNumRepository = unreadNumRepository;
    }

    public int getUnreadNumByEmailAndType(String email, String type) {
        UnreadNumKey key = new UnreadNumKey()
                .setEmail(email)
                .setType(type);
        Optional<UnreadNum> unreadNum = unreadNumRepository.findById(key);
        if (!unreadNum.isPresent()) {
            return 0;
        }
        return unreadNum.get().getNum();
    }
}
