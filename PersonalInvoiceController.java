package com.hzhang.sweethome.controller;

import com.hzhang.sweethome.model.PersonalInvoice;
import com.hzhang.sweethome.service.PersonalInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class PersonalInvoiceController {
    private PersonalInvoiceService personalInvoiceService;
    @Autowired
    public PersonalInvoiceController(PersonalInvoiceService personalInvoiceService) {
        this.personalInvoiceService = personalInvoiceService;
    }

    @GetMapping(value = "/personalInvoices")
    public List<String> listPersonalInvoice(
            @RequestParam(name = "user_email") String userEmail,
            @RequestParam(name="type") String type
    ){
        return personalInvoiceService.listByUserandType(userEmail,type);
    }

    @GetMapping(value = "/personalInvoices")
    public void add(@RequestBody PersonalInvoice personalInvoice){
        personalInvoiceService.add(personalInvoice);
    }


}
