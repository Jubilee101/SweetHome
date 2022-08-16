package com.hzhang.sweethome.repository;

import com.hzhang.sweethome.model.PublicUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PublicUtilsRepository extends JpaRepository<PublicUtils, String> {
    Optional<PublicUtils> findByCategory(String category);
}
