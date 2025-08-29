package com.guidely.userservice.controller;

import com.guidely.userservice.dto.UserCreateRequest;
import com.guidely.userservice.dto.UserResponse;
import com.guidely.userservice.dto.UserUpdateRequest;
import com.guidely.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    
    private final UserService userService;
    
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request) {
        log.info("사용자 생성 API 호출: {}", request.getNickname());
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsersByNickname(
            @RequestParam String nickname) {
        log.info("닉네임 검색 API 호출: {}", nickname);
        List<UserResponse> responses = userService.searchUsersByNickname(nickname);
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        log.info("사용자 조회 API 호출: ID={}", id);
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id, 
            @Valid @RequestBody UserUpdateRequest request) {
        log.info("사용자 수정 API 호출: ID={}", id);
        UserResponse response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }
} 