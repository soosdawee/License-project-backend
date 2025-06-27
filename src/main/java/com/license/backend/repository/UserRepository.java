package com.license.backend.repository;

import com.license.backend.domain.model.User;
import com.license.backend.domain.model.Visualization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    UserDetails findUserDetailsByEmail(String email);

    User findByEmail(String email);

    @Query("""
    SELECT v FROM User u
    JOIN u.likedVisualizations v
    LEFT JOIN FETCH v.user 
    WHERE u.userId = :userId
    """)
    Set<Visualization> findLikedVisualizationsByUserId(@Param("userId") Integer userId);

    User findUserByEmail(String email);

    User findUserByResetPasswordToken(String email);

}