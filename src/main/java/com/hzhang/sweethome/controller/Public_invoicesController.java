package com.hzhang.sweethome.controller;

import com.hzhang.sweethome.model.Public_invoices;
import com.hzhang.sweethome.service.Public_invoicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Public_invoicesController {
    private Public_invoicesService public_invoicesService;
    @Autowired
    public Public_invoicesController(Public_invoicesService public_invoicesService){
        this.public_invoicesService = public_invoicesService;
    }
    @GetMapping(value = "/public_invoice")
    public List<Public_invoices> list_public_invoices(){
        return public_invoicesService.list_public_invoices();
    }

    @PostMapping("/public_invoice")
    public void addPublic_invoices(@RequestBody Public_invoices public_invoices){
        public_invoicesService.add(public_invoices);

    }

}
