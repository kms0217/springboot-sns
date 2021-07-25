package com.kms.mygram.repository;

import com.kms.mygram.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByUsernameOrEmailOrPhoneNumber(String username, String Email, String phoneNumber);

    @Query(value = "select * from user a " +
            "where a.user_id not in " +
            "(select b.followee_id from follow b where b.follower_id =:user_id) " +
            "and a.user_id !=:user_id", nativeQuery = true)
    List<User> findRecommendUser(@Param("user_id") Long userId);
}
