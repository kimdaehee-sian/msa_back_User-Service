package com.guidely.userservice.service;

import com.guidely.userservice.dto.UserCreateRequest;
import com.guidely.userservice.dto.UserResponse;
import com.guidely.userservice.dto.UserUpdateRequest;
import com.guidely.userservice.entity.User;
import com.guidely.userservice.exception.UserNotFoundException;
import com.guidely.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    
    private final UserRepository userRepository;
    
    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        log.info("사용자 생성 요청: {}", request.getNickname());
        
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다: " + request.getNickname());
        }
        
        User user = User.builder()
                .nickname(request.getNickname())
                .language(request.getLanguage())
                .build();
        
        User savedUser = userRepository.save(user);
        log.info("사용자 생성 완료: ID={}, 닉네임={}", savedUser.getId(), savedUser.getNickname());
        
        return mapToUserResponse(savedUser);
    }
    
    public UserResponse getUserById(Long id) {
        log.info("사용자 조회 요청: ID={}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + id));
        
        return mapToUserResponse(user);
    }
    
    @Transactional
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        log.info("사용자 수정 요청: ID={}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + id));
        
        if (request.getNickname() != null && !request.getNickname().equals(user.getNickname())) {
            if (userRepository.existsByNickname(request.getNickname())) {
                throw new IllegalArgumentException("이미 존재하는 닉네임입니다: " + request.getNickname());
            }
            user.setNickname(request.getNickname());
        }
        
        if (request.getLanguage() != null) {
            user.setLanguage(request.getLanguage());
        }
        
        User updatedUser = userRepository.save(user);
        log.info("사용자 수정 완료: ID={}, 닉네임={}", updatedUser.getId(), updatedUser.getNickname());
        
        return mapToUserResponse(updatedUser);
    }
    
    public List<UserResponse> searchUsersByNickname(String nickname) {
        log.info("닉네임으로 사용자 검색 요청: {}", nickname);
        
        List<User> users = userRepository.findByNicknameContainingIgnoreCase(nickname);
        log.info("검색 결과: {}명의 사용자 발견", users.size());
        
        return users.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }
    
    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .language(user.getLanguage())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
} 