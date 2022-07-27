package com.hzhang.sweethome.service;
import com.hzhang.sweethome.exception.PersonalInvoiceNotExistException;
import com.hzhang.sweethome.model.PersonalInvoice;
import com.hzhang.sweethome.repository.PersonalInvoiceReository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonalInvoiceService {
    private PersonalInvoiceReository personalInvoiceReository;
    @Autowired
    public PersonalInvoiceService(PersonalInvoiceReository personalInvoiceReository) {
        this.personalInvoiceReository = personalInvoiceReository;
    }
    public List<String> listByUserandType(String email,String type) throws PersonalInvoiceNotExistException {
        List<String> textList =  personalInvoiceReository.findByEmailandType(email,type);
        if(textList==null){
            throw new PersonalInvoiceNotExistException("Invoice does not exist");
        }
        return textList;
    }

    public void add(PersonalInvoice personalInvoice){
            personalInvoiceReository.save(personalInvoice);
    }



}
