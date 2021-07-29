package com.kms.mygram.repository;

import com.kms.mygram.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query(value = "select * from chat_room where user_one_id=:user_id or user_tow_id=:user_id", nativeQuery = true)
    List<ChatRoom> findAllByUserId(@Param("user_id") Long userId);

    @Query(value = "select * from chat_room " +
            "where (user_one_id=:user_one_id and user_two_id=:user_two_id) " +
            "or (user_one_id=:user_two_id and user_two_id=:user_one_id) limit 1", nativeQuery = true)
    Optional<ChatRoom> findByUserOneIdAndUserTwoId(@Param("user_one_id") Long userOneId, @Param("user_two_id") Long userTwoId);
}
