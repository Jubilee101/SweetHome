package com.hzhang.sweethome.repository;

import com.hzhang.sweethome.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findAll();
}
