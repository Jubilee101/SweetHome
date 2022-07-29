package com.hzhang.sweethome.controller;

import com.hzhang.sweethome.model.PersonalInvoice;
import com.hzhang.sweethome.service.PersonalInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

public class PersonalInvoiceController {
    private PersonalInvoiceService personalInvoiceService;
    @Autowired
    public PersonalInvoiceController(PersonalInvoiceService personalInvoiceService) {
        this.personalInvoiceService = personalInvoiceService;
    }

    @GetMapping(value = "/personal_invoice")
    public List<PersonalInvoice> listPersonalInvoice(
            Principal principal,
            @RequestParam(name="type") String type
    ){

        return personalInvoiceService.listByUserandType(principal.getName(), type);
    }

    @PostMapping(value = "/personal_invoice")
    public void add(@RequestParam("text") String text,@RequestParam("type") String type){
        PersonalInvoice personalInvoice = new PersonalInvoice.Builder().setTimestamp(LocalDate.now()).setText(text).setType(type).build();
        personalInvoiceService.add(personalInvoice);
    }


}
