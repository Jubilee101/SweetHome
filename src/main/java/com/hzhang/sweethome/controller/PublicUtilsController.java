package com.hzhang.sweethome.controller;

import com.hzhang.sweethome.model.PublicUtils;
import com.hzhang.sweethome.service.PublicUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PublicUtilsController {
    private PublicUtilsService publicUtilsService;

    @Autowired
    public PublicUtilsController(PublicUtilsService publicUtilsService) {
        this.publicUtilsService = publicUtilsService;
    }

    @GetMapping("/public_utils")
    public List<PublicUtils> getAllPublicUtils(){
        return publicUtilsService.getAllPublicUtils();
    }

    @PostMapping("/public_utils")
    public void addPublicUtils(@RequestParam(name = "category") String category,
                               @RequestParam(name = "description") String description){
        publicUtilsService.add(category, description);
    }
}
