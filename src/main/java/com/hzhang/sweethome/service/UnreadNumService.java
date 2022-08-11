package com.hzhang.sweethome.service;

import com.hzhang.sweethome.exception.UnreadNumNotExistException;
import com.hzhang.sweethome.model.DeferredRequestList;
import com.hzhang.sweethome.model.InvoiceType;
import com.hzhang.sweethome.model.UnreadNum;
import com.hzhang.sweethome.model.UnreadNumKey;
import com.hzhang.sweethome.repository.UnreadNumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UnreadNumService {

    private UnreadNumRepository unreadNumRepository;
    private DeferredRequestList deferredRequestList;

    @Autowired
    public UnreadNumService(UnreadNumRepository unreadNumRepository,
                            DeferredRequestList list) {
        this.unreadNumRepository = unreadNumRepository;
        this.deferredRequestList = list;
    }

    public UnreadNum getUnreadNum(String email, String type) {
        UnreadNumKey key = new UnreadNumKey()
                .setEmail(email)
                .setType(type);
        Optional<UnreadNum> unreadNum = unreadNumRepository.findById(key);
        if (!unreadNum.isPresent()) {
            throw new UnreadNumNotExistException("cannot find unread nums");
        }
        return unreadNum.get();
    }

    public void increaseUnreadNumByOne(String email, String type) {
        unreadNumRepository.increaseUnreadNumByOneById(email, type);
        deferredRequestList.publish(email, "PERSONAL");
    }

    public void clearUnreadNum(String email, String type) {
        unreadNumRepository.clearUnreadNumById(email, type);
    }

    public void clearAllPublicUnreadNum() {
        unreadNumRepository.clearAllPublicUnreadNum();
    }

    public void incrementPublicUnreadNum() {
        unreadNumRepository.updateAllPublicUnreadNum(1);
        deferredRequestList.publish(InvoiceType.PUBLIC.name());
    }
}
