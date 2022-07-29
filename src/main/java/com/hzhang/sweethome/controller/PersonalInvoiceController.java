package com.hzhang.sweethome.controller;

import com.hzhang.sweethome.model.PersonalInvoice;
import com.hzhang.sweethome.model.User;
import com.hzhang.sweethome.service.PersonalInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public void add(@RequestParam("text") String type,
                    @RequestParam("type") String text,
                    @RequestParam("room") String room,
                    @RequestParam("name") String name){
        personalInvoiceService.add(type, text, name, room);
    }


}
