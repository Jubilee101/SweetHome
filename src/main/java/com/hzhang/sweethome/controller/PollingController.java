package com.hzhang.sweethome.controller;

import com.hzhang.sweethome.model.DeferredRequestList;
import com.hzhang.sweethome.model.InvoiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.security.Principal;

@RestController
public class PollingController {
    private DeferredRequestList deferredRequestList;

    @Autowired
    public PollingController(DeferredRequestList list) {
        this.deferredRequestList = list;
    }

    @GetMapping(value = "/watch")
    public DeferredResult<String> watch(@RequestParam(name = "type") String type
            , Principal principal) {
        return deferredRequestList.watch(principal.getName(), type);
    }
    // each person take one path so that there's no jam
    @GetMapping(value = "/watch/{email}")
    public DeferredResult<String> watchMsg(@PathVariable("email") String email,
                                           Principal principal) {
        System.out.println(email + " send msg watch request");
        return deferredRequestList.watch(principal.getName(), "MESSAGE");
    }
}
