package com.hzhang.sweethome.controller;

import com.hzhang.sweethome.model.DeferredRequestList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
