package com.hzhang.sweethome.repository;

import com.hzhang.sweethome.model.PublicUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicUtilsRepository extends JpaRepository<PublicUtils, String> {

}
