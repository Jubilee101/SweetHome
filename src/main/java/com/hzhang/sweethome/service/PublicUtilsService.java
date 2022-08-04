package com.hzhang.sweethome.service;

import com.hzhang.sweethome.exception.PublicUtilsAlreadyExistsException;
import com.hzhang.sweethome.model.PublicUtils;
import com.hzhang.sweethome.repository.PublicUtilsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicUtilsService {
    private PublicUtilsRepository publicUtilsRepository;

    @Autowired
    public PublicUtilsService(PublicUtilsRepository publicUtilsRepository) {
        this.publicUtilsRepository = publicUtilsRepository;
    }

    List<PublicUtils> getAllPublicUtils() {
        return publicUtilsRepository.findAll();
    }

    void add(String category, String description) {
        Optional<PublicUtils> utils = publicUtilsRepository.findByCategory(category);
        if (utils.isPresent()) {
            throw new PublicUtilsAlreadyExistsException("This public utility already exists");
        }
        publicUtilsRepository.save(new PublicUtils(category, description));
    }
}
