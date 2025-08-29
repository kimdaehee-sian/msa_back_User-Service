package com.guidely.userservice.repository;

import com.guidely.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByNickname(String nickname);
    
    boolean existsByNickname(String nickname);
    
    List<User> findByNicknameContainingIgnoreCase(String nickname);
} 