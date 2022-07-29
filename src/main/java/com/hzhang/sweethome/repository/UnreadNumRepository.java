package com.hzhang.sweethome.repository;

import com.hzhang.sweethome.model.UnreadNum;
import com.hzhang.sweethome.model.UnreadNumKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UnreadNumRepository extends JpaRepository<UnreadNum, UnreadNumKey> {
    @Transactional
    @Modifying
    @Query("update UnreadNum unread set unread.num = unread.num + 1 where unread.id.email = ?1 and unread.id.type = ?2")
    int increaseUnreadNumByOneById(String email, String type);

    @Transactional
    @Modifying
    @Query("update UnreadNum unread set unread.num = 0 where unread.id.email = ?1 and unread.id.type = ?2")
    int clearUnreadNumById(String email, String type);

    @Transactional
    @Modifying
    @Query("update UnreadNum unread set unread.num = 0 where unread.id.type = 'PUBLIC'")
    int clearAllPublicUnreadNum();

    @Transactional
    @Modifying
    @Query("update UnreadNum unread set unread.num = unread.num + ?1 where unread.id.type = 'PUBLIC'")
    int updateAllPublicUnreadNum(int num);
}
