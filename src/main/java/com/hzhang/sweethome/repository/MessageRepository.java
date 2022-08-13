package com.hzhang.sweethome.repository;

import com.hzhang.sweethome.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    @Modifying
    @Query(value = "SELECT * FROM message WHERE id < ?1 ORDER BY id DESC LIMIT 10", nativeQuery = true)
    List<Message> loadMessage(long id);

    @Modifying
    @Query(value = "SELECT * FROM message ORDER BY id DESC LIMIT 10", nativeQuery = true)
    List<Message> findFirstTen();
}
