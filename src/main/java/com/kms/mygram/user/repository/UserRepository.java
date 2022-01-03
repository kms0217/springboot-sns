package com.kms.mygram.user.repository;

import com.kms.mygram.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findWithAuthoritiesByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);

    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByUsernameOrEmailOrPhoneNumber(String username, String Email, String phoneNumber);

    @Query(value = "select * from user a " +
            "where a.user_id not in " +
            "(select b.followee_id from follow b where b.follower_id =:user_id) " +
            "and a.user_id !=:user_id limit 5", nativeQuery = true)
    List<User> findRecommendUser(@Param("user_id") Long userId);

    @Query(value = "select * from user where username like :filter% and user_id!=:user_id", nativeQuery = true)
    List<User> findWithFilter(@Param("user_id") Long userId, @Param("filter") String filter);
}
