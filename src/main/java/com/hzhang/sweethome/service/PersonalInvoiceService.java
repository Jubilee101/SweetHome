package com.hzhang.sweethome.service;
import com.hzhang.sweethome.exception.PersonalInvoiceNotExistException;
import com.hzhang.sweethome.model.PersonalInvoice;
import com.hzhang.sweethome.repository.PersonalInvoiceReository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PersonalInvoiceService {
    private PersonalInvoiceReository personalInvoiceReository;
    @Autowired
    public PersonalInvoiceService(PersonalInvoiceReository personalInvoiceReository) {
        this.personalInvoiceReository = personalInvoiceReository;
    }
    public List<PersonalInvoice> listByUserandType(String email, String type) throws PersonalInvoiceNotExistException {
        List<PersonalInvoice> invoiceList =  personalInvoiceReository.findByEmailandType(email,type);
        if(invoiceList==null){
            throw new PersonalInvoiceNotExistException("Invoice does not exist");
        }
        invoiceList.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        return invoiceList;
    }

    public void add(PersonalInvoice personalInvoice){
            personalInvoiceReository.save(personalInvoice);
    }



}
