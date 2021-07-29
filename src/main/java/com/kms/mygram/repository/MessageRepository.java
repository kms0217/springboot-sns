package com.kms.mygram.repository;

import com.kms.mygram.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "select * from message where chat_room_id=:chat_room_id order by created_at", nativeQuery = true)
    List<Message> findAllByChatRoomId(@Param("chat_room_id") Long chatRoomId);
}
