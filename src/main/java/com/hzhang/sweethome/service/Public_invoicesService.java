package com.hzhang.sweethome.service;

import com.hzhang.sweethome.model.Public_invoices;
import com.hzhang.sweethome.repository.Public_invoicesRepository;
import com.hzhang.sweethome.repository.UnreadNumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class Public_invoicesService {
    private Public_invoicesRepository public_invoicesRepository;
    private UnreadNumService unreadNumService;
    @Autowired
    public Public_invoicesService(Public_invoicesRepository public_invoicesRepository,
                                  UnreadNumService unreadNumService){
        this.public_invoicesRepository = public_invoicesRepository;
        this.unreadNumService = unreadNumService;
    }

    public List<Public_invoices> list_public_invoices(){

        List<Public_invoices> invoices = public_invoicesRepository.findAll();
        invoices.sort((o1, o2) -> {
            if (o1.getDate().equals(o2.getDate())) {
                return -1 * o1.getTime().compareTo(o2.getTime());
            }
            return -1 * o1.getDate().compareTo(o2.getDate());
        });
        return invoices;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(Public_invoices public_invoices){
        public_invoicesRepository.save(public_invoices);
        unreadNumService.incrementPublicUnreadNum();
    }
}
