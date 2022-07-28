package com.hzhang.sweethome.service;

import com.hzhang.sweethome.model.Public_invoices;
import com.hzhang.sweethome.repository.Public_invoicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class Public_invoicesService {
    private Public_invoicesRepository public_invoicesRepository;
    @Autowired
    public Public_invoicesService(Public_invoicesRepository public_invoicesRepository){
        this.public_invoicesRepository = public_invoicesRepository;
    }

    public List<Public_invoices> list_public_invoices(){

        List<Public_invoices> invoices = public_invoicesRepository.findAll();
        invoices.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        return invoices;
    }

    public void add(Public_invoices public_invoices){
        public_invoicesRepository.save(public_invoices);
    }
}
