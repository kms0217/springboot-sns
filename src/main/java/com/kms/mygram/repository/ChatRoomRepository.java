package com.kms.mygram.repository;

import com.kms.mygram.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query(value = "select * from chat_room where user_one_id=:user_id or user_tow_id=:user_id", nativeQuery = true)
    List<ChatRoom> findAllByUserId(@Param("user_id") Long userId);
}
