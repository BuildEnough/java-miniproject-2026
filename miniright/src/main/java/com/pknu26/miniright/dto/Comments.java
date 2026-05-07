package com.pknu26.miniright.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
    private Long commentId;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long postId;
    private Long categoryId;
    private Long userId;
    
    // 작성자 이름을 화면에 표시하기 위한 필드 (USERS 테이블 JOIN용)
    private String nickname;
}