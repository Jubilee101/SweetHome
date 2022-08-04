package com.hzhang.sweethome.service;

import com.hzhang.sweethome.model.PublicUtils;
import com.hzhang.sweethome.repository.PublicUtilsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicUtilsService {
    PublicUtilsRepository publicUtilsRepository;

    @Autowired
    public PublicUtilsService(PublicUtilsRepository publicUtilsRepository) {
        this.publicUtilsRepository = publicUtilsRepository;
    }

    List<PublicUtils> getAllPublicUtils() {
        return publicUtilsRepository.findAll();
    }
}
