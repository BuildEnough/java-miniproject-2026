package com.pknu26.miniright.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {

    private Long userId;
    private String loginId;
    private String password;
    private String name;
    private String role;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
