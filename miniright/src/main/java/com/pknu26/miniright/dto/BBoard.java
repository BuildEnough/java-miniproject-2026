package com.pknu26.miniright.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BBoard {

    private Long postId;
    private Long categoryId;
    private String title;
    private String contents; 
    private Long userId;

    private Integer viewCount; // 조회수
    
    private String imagePath;
    
    private LocalDateTime createdAt;  // 생성일
    private LocalDateTime updatedAt;  // 수정일
}
