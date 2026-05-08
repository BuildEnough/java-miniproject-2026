package com.pknu26.miniright.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BBoardComment {

    private Long commentId;
    private String contents;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long postId;
    private Long categoryId;
    private Long userId;

    // USERS 테이블에서 가져오는 작성자 이름
    private String writerName;
}